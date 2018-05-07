package com.microwise.uma.processor;

import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.PackToPoContoller;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.pack.BasePack;
import com.microwise.uma.pack.CardReaderPack;
import com.microwise.uma.pack.LocationInfoPack;
import com.microwise.uma.util.ConfigureProperties;
import com.microwise.uma.util.NumberUtil;

/**
 * 基本包分类:读卡器信息包 、 位置信息包
 * 
 * @author houxiaocheng
 * @date 2013-4-25 下午1:25:04
 * 
 * @check 2013-05-06 xubaoji svn:3187
 * @check 2013-10-14 @gaohui #5935
 */
@Component
public class MessagePackParseProcessor implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(MessagePackParseProcessor.class);

	/** 用来获得基本数据包队列 */
	@Autowired
	private AllController allController;

	/** 入库队列控制器 */
	@Autowired
	private PackToPoContoller packToPoController;

	/**
	 * 解析过滤包线程运行
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("基本包分类线程启动");
		while (!SystemConfigure.exit) {
			try {
				if (allController.needCheckBasePack()) {

					List<BasePack> checkPacks = allController.checkBasePackList();
					for (BasePack basePack : checkPacks) {
						// 将基本数据包中 buffer 解析为数据包对象
						BasePack pack = parseBufferToPack(basePack);
						// 如果没有解析出数据包 跳过本次循环
						if (pack == null) {
							continue ;
						}
						// 将分类后的数据包添加到 对应的 队列中等待解析
						if (pack instanceof LocationInfoPack) {
							packToPoController.offerLocation((LocationInfoPack)pack);
						}
						if (pack instanceof CardReaderPack) {
							packToPoController.offerCardReader((CardReaderPack)pack);
						}
					}
				}
				Thread.sleep(SystemConfigure.getConfigure(
						ConfigureProperties.BUFFER_TO_BASEPACK_PROCESSOR_SLEEPTIME,
						SystemConfigure.BUFFER_TO_BASEPACK_PROCESSOR_SLEEPTIME));
			} catch (Exception e) {
				logger.error("基本包分类线程出错", e);
			}
		}
	}

	/**
	 * 将数据包根据指令分类，并格式化buffer为一个数据包对象
	 * 
	 * @param date
	 *            时间戳
	 * @param command
	 *            指令
	 * @param buffer
	 *            数据buffer
	 */
	private BasePack parseBufferToPack(BasePack basePack) {
		ByteBuffer buffer = basePack.getDatas();
		BasePack pack = null;
		switch (buffer.getShort(BasePack.PACK_COMMAND_INDEX)) {
		// 位置信息 数据包
		case LocationInfoPack.COMMAND:
			pack = parseToLocationInfoPack(basePack);
			break;
		// 以下为读卡器信息包
		case CardReaderPack.COMMAND_GETSN:
			pack = parseToCardReaderPackBySN(buffer);
			break;
		case CardReaderPack.COMMAND_GETID:
			pack = parseToCardReaderPackById(buffer);
			break;
		case CardReaderPack.COMMAND_GETVERSION:
			pack = parseToCardReaderPackByVersion(buffer);
			break;
		}
		if (pack != null) {
			pack.setDate(basePack.getDate());
			pack.setIpAndPort(basePack.getIpAndPort());
			pack.setSiteId(basePack.getSiteId());
		}
		return pack;
	}

	/**
	 * 把buffer封装成一个初级的LocationInfoPack（位置信息包）对象
	 * 
	 * @param buffer
	 * @return LocationInfoPack
	 */
	private LocationInfoPack parseToLocationInfoPack(BasePack basePack) {
		// 激发器 sn
		int exciterId = 0;
		// 电子卡 sn
		int cardIdL = 0;
		// 电量
		int voltage = 0;
		ByteBuffer buffer = basePack.getDatas();
		// 位置信息包
		LocationInfoPack lPack = null;
		if (buffer.limit() == 41) { // 版本 10
			exciterId = buffer.getShort(LocationInfoPack.EXCITER_INDEX) & 0xffff;
			cardIdL = buffer.getInt(LocationInfoPack.SN_INDEX) & 0xffffffff;
			voltage = buffer.get(LocationInfoPack.VOLTAGE_INDEX) & 0xff;
			lPack = new LocationInfoPack();
		} else if (buffer.limit() == 33) { // 版本11
			exciterId = buffer.getShort(LocationInfoPack.EXCITER_INDEX - 8) & 0xffff;
			cardIdL = buffer.getInt(LocationInfoPack.SN_INDEX - 8) & 0xffffffff;
			voltage = buffer.get(LocationInfoPack.VOLTAGE_INDEX - 8) & 0xff;
			lPack = new LocationInfoPack();
		}
		if (lPack != null) {
			String cardsn = String.format("%1$04x", cardIdL);
			lPack.setCardId(cardsn);
			lPack.setVoltage(voltage * 1.0f); // 电池电压入库方式调整
			if (exciterId == 0x0000 || exciterId == 0xFFFF) {
				// 判断 激发器是否为 0000 和 ffff 数据包 TODO 是否 可以考虑 过滤 掉 这些数据包
				lPack.setExciterId(null);
			} else {
				// 获得 激发器 sn 字符串 判断是否为 纯数字组成，如果包含 字母 或其他不是数字字符 丢弃
				String exciter = String.format("%1$04x", exciterId);
				if (NumberUtil.stringIsNumber(exciter)) {
					// 如果开启激发器过滤 并且激发器白名单不为 empty 并且当前激发器 不在白名单中将该包数据过滤
					if (SystemConfigure.usedExciterMap.get(basePack.getSiteId()).contains(exciter)) {
						lPack.setExciterId(exciter);
					} else {
						logger.info("激发器不在白名单中, 被过滤 exciterSn = " + exciter + "cardsn=" + cardsn);
						return null;
					}
				} else {
					logger.info("过滤错误激发器sn=" + exciter);
					return null;
				}
			}
		}
		return lPack;
	}

	/**
	 * 根据SN请求封装buffer为读卡器包
	 * 
	 * @param buffer
	 * @return CardReaderPack
	 */
	private CardReaderPack parseToCardReaderPackBySN(ByteBuffer buffer) {
		// TODO
		return null;
	}

	/**
	 * 根据id请求封装buffer为读卡器包
	 * 
	 * @param buffer
	 *            数据buffer
	 * @return CardReaderPack
	 */
	private CardReaderPack parseToCardReaderPackById(ByteBuffer buffer) {
		int sn = buffer.getInt(CardReaderPack.COMMAND_GETID_SNINDEX) & 0xffffffff;
		int id = buffer.getShort(CardReaderPack.COMMAND_GETID_IDINDEX) & 0xffff;
		CardReaderPack pack = new CardReaderPack(CardReaderPack.COMMAND_GETID);
		pack.setSn(String.format("%1$04x", sn));
		pack.setDeviceId(String.format("%1$04x", id));
		return pack;
	}

	/**
	 * 根据version请求封装buffer为读卡器包
	 * 
	 * @param buffer
	 *            数据buffer
	 * @return CardReaderPack
	 */
	private CardReaderPack parseToCardReaderPackByVersion(ByteBuffer buffer) {
		int version = buffer.getShort(CardReaderPack.COMMAND_GETVERSION_INDEX);
		CardReaderPack pack = new CardReaderPack(CardReaderPack.COMMAND_GETVERSION);
		pack.setVersion(String.format("%1$04x", version));
		return pack;
	}

}
