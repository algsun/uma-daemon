package com.microwise.uma.processor;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.bean.Request;
import com.microwise.uma.controller.PackToPoContoller;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.pack.CardReaderPack;
import com.microwise.uma.pack.LocationInfoPack;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.util.ConfigureProperties;

/**
 * 包解析线程 : 1、封装数据存储对象 2、去重、提取设备
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:27:56
 * 
 * @check 2013-05-07 xubaoji svn:3214
 * @check 2013-10-14 @gaohui #5888
 */
@Component
public class PackDisassemblyProcessor implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PackDisassemblyProcessor.class);

	/** 入库队列控制器 */
	@Autowired
	private PackToPoContoller pachToPo;

	@Autowired
	private SystemConfigure systemConfigure;

	/**
	 * 包解析线程启动
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("包解析线程启动");
		while (!SystemConfigure.exit) {
			try {
				// 判断 是否有需要解析的数据包，有 执行 程序，没有循环判断 会造成cpu 大量消耗
				if (pachToPo.needPackDisassembly()) {

                    // 解析 读卡器包
					Queue<CardReaderPack> crpackList = pachToPo.checkCardReadersPackList();
                    // 待添加设备列表
                    LinkedList<DevicePO> devices = new LinkedList<DevicePO>();
					for (CardReaderPack pack : crpackList) {
						// logger.info("待解析的位置信息包数量为：" + lipackList.size());
						DevicePO cardReader = toCardReaderDevicePO(pack);
						if (cardReader != null) {
							logger.info("解析到新的读卡器设备：sn=" + cardReader.getSn());
							devices.add(cardReader);
						}
					}

                    // 解析位置信息包
                    Queue<LocationInfoPack> lipackList = pachToPo.checkLocationsPackList();
                    Map<Long, DevicePO> updDevices = new HashMap<Long, DevicePO>();
                    // 创建 用来存储 待更新 实时位置，待更新设备，待添加实时位置集合
                    Map<Long, RealtimeLocationPO> updRealtimeLocations = new HashMap<Long, RealtimeLocationPO>();
                    Map<Long, RealtimeLocationPO> saveRealtimeLocations = new HashMap<Long, RealtimeLocationPO>();
                    // 待添加历史位置 列表
                    LinkedList<LocationPO> locations = new LinkedList<LocationPO>();
					for (LocationInfoPack pack : lipackList) {
						// 解析 电子卡
						disassemblyCard(pack, devices, updDevices);
						// 解析激发器
						disassemblyExciter(pack, devices, updDevices);
						// 解析位置信息
						disassemblyLocation(pack, updRealtimeLocations, saveRealtimeLocations, locations);
					}
                    logger.debug("---解析crpackList ---crpackList：" + crpackList.size() + " ; lipackList : " + lipackList.size());

					// 将 待添加设备添加到设备添加队列
					pachToPo.addDevicePO(devices);
					// 将待添加位置信息 添加到位置信息添加队列
					pachToPo.addLocationPO(locations);
					// 将待 更新设备添加到 设备更新队列
					pachToPo.addUpdateDevicePO(updDevices);
					// 将 待新增 实时位置 列表 添加到实时位置添加队列
					pachToPo.addRealtimeLocationPOForSave(saveRealtimeLocations);
					// 将待更新实时位置列表 添加到实时位置更新队列
					pachToPo.addRealtimeLocationPOForUpdate(updRealtimeLocations);

					logger.debug("---解析basePack完成 ---结果：devices ：" + devices.size() + " , locations :" + locations.size());
				}
				Thread.sleep(SystemConfigure.getConfigure(
						ConfigureProperties.DISASSEMBLY_PROCESSOR_SLEEPTIME,
						SystemConfigure.DISASSEMBLY_PROCESSOR_SLEEPTIME));
			} catch (Exception e) {
				logger.error("包解析线程出错:", e);
			}
		}
	}

	/***
	 * 解析电子卡
	 * 
	 * @author xu.baoji
	 * @date 2013-9-24
	 * 
	 * @param pack
	 *            位置信息包
	 * @param devices
	 *            添加设备列表
	 * @param updDevices
	 *            更新设备列表
	 */
	private void disassemblyCard(LocationInfoPack pack, List<DevicePO> devices,
			Map<Long, DevicePO> updDevices) {
		// 解析电子卡设备 ， 判断卡是否入库 从缓存中判断该sn 的电子卡是否存在
		if (!SystemConfigure.deviceSnSet.contains(pack.getCardId())) {
			// 需要入库设备
			DevicePO deviceCard = toCardDevicePO(pack);
			devices.add(deviceCard);
			SystemConfigure.deviceSnSet.add(pack.getCardId());
			logger.info("解析到新电子卡：sn=" + pack.getCardId());
		} else {
			DevicePO card = SystemConfigure.allDevices2_F.get(pack.getCardId());
			// 判断电子卡是否需要更新：如果需要更新并设置电子卡最后工作时间为当前包时间并返回true
			if (card != null && card.isNeedToUpd(pack.getDate()) && card.getId() != null) {
				// 电子卡更新电量
				card.setVoltage(pack.getVoltage());
				updDevices.put(card.getId(), card);
			}
		}
	}

	/***
	 * 解析激发器设备
	 * 
	 * @author xu.baoji
	 * @date 2013-9-24
	 * 
	 * @param pack
	 *            位置信息包
	 * @param devices
	 *            设备添加列表
	 * @param updDevices
	 *            设备更新map
	 */
	private void disassemblyExciter(LocationInfoPack pack, List<DevicePO> devices,
			Map<Long, DevicePO> updDevices) {
		// 解析激发器设备 ，判断激发器是否入库
		if (pack.getExciterId() != null) {
			if (!SystemConfigure.deviceSnSet.contains(pack.getExciterId())) {
					DevicePO deviceExciter = toExciterDevicePO(pack);
					devices.add(deviceExciter);
					SystemConfigure.deviceSnSet.add(pack.getExciterId());
					logger.info("解析到新激发器设备 ：sn=" + pack.getExciterId());
			} else {
				DevicePO exc = SystemConfigure.allDevices2_F.get(pack.getExciterId());
				if (exc != null) {
					exc.setUpdateTime(new Date(pack.getDate()));
					// 实时更新激发器 工作时间
					updDevices.put(exc.getId(), exc);
				}
			}
		}
	}

	/***
	 * 解析位置信息
	 * 
	 * @author xu.baoji
	 * @date 2013-9-24
	 * 
	 * @param pack
	 *            待解析的位置信息包
	 * @param updRealtimeLocations
	 *            更新实时位置列表
	 * @param saveRealtimeLocations
	 *            添加实时位置列表
	 * @param locations
	 *            历史位置列表
	 */
	private void disassemblyLocation(LocationInfoPack pack,
			Map<Long, RealtimeLocationPO> updRealtimeLocations,
			Map<Long, RealtimeLocationPO> saveRealtimeLocations, List<LocationPO> locations) {
		// 解析人员位置信息
		Long userId = SystemConfigure.userCardRelations_F.get(pack.getCardId());
		logger.debug("pack.getCardID :" + pack.getCardId() + " ; userID :" + userId);
		// 判断人卡绑定是否存在 和激发器位置信息都存在
		if (userId != null && pack.getExciterId() != null) {
			DevicePO exciter = SystemConfigure.allDevices2_F.get(pack.getExciterId());
			if (exciter != null && exciter.getId() != null) {
				// 实时位置表里面的所有数据都是某个人在系统中出现的最后一次位置
				RealtimeLocationPO realTime = SystemConfigure.realtimeLocation_F.get(userId);
				if (realTime == null) {// 第一次出现
					RealtimeLocationPO realLocation = new RealtimeLocationPO(userId,
							exciter.getId(), pack.getDate());
					// 添加实时位置 和历史位置
					saveRealtimeLocations.put(userId, realLocation);
					locations.add(new LocationPO(userId, exciter.getId(), pack.getDate()));
					// 将新的实时位置 更新到 缓存
					SystemConfigure.updateRealtimeLocation(userId, realLocation);
					logger.info("解析到位置信息userId=" + userId + ",exciterId=" + pack.getExciterId()
							+ ",cardId=" + pack.getCardId());
				} else {
					if (realTime.getDeviceId() != null
							&& realTime.getDeviceId().longValue() != exciter.getId().longValue()) {
						// 如果本包时间比实时位置中的时间早，则丢弃该包
						if (pack.getDate() <= realTime.getOccurrenceTime()) {
							logger.info("本包时间有误，丢弃！ times:" + pack.getDate());
						} else {
							RealtimeLocationPO realLocation = new RealtimeLocationPO(userId,
									exciter.getId(), pack.getDate());
							// 更新实时位置，添加历史位置
							locations.add(new LocationPO(userId, exciter.getId(), pack.getDate()));
							updRealtimeLocations.put(userId, realLocation);
							// 更新最新位置
							SystemConfigure.updateRealtimeLocation(userId, realLocation);
							logger.info("解析到位置信息userId=" + userId + ",exciterId="
									+ pack.getExciterId() + ",cardId=" + pack.getCardId());
						}
					}
				}
			}
		}
	}

	/**
	 * 把读卡器对应的数据包转换成设备对象
	 * 
	 * @param pack
	 *            数据包对象
	 * @return DevicePO
	 */
	private DevicePO toCardReaderDevicePO(CardReaderPack pack) {
		// 获得读卡器 自定义 request 对象中 读卡器实体
		Request request = systemConfigure.getReaderRequest(pack.getIpAndPort());
		DevicePO cardReader = request.getReaderDevice();
		switch (pack.getCommand()) {
		case CardReaderPack.COMMAND_GETSN:
			break;
		case CardReaderPack.COMMAND_GETID:
			cardReader.setDeviceId(pack.getDeviceId());
			cardReader.setSn(pack.getSn());
			cardReader.setName(pack.getSn());
			logger.info("解析读卡器ip:port=" + pack.getIpAndPort() + "获得读卡器sn=" + pack.getSn() + "deviceId="
					+ pack.getDeviceId());
			break;
		case CardReaderPack.COMMAND_GETVERSION:
			cardReader.setVersion(pack.getVersion());
			logger.info("解析读卡器ip:port=" + pack.getIpAndPort() + "获得读卡version=" + pack.getVersion());
			break;
		}
		cardReader.setUpdateTime(new Date(pack.getDate()));
		// 判断当前读卡器线程是否还需要 继续运行
		if (!request.isNeedRunCardReaderProcessor()) {
			CardReaderProcessor cardReaderProcessor = request.getCardReaderProcessor();
			// 将当前读卡器线程 停止
			cardReaderProcessor.stop();
			// 判断当前读卡器器 sn 是否存在
			if (!SystemConfigure.deviceSnSet.contains(cardReader.getSn())) {
				return cardReader;
			}
		}
		return null;
	}

	/**
	 * 把携带电子卡信息对应的数据包转换成设备对象
	 * 
	 * @param pack
	 *            数据包对象
	 * @return
	 */
	private DevicePO toCardDevicePO(LocationInfoPack pack) {
		DevicePO card = new DevicePO(pack.getCardId(), 3, new Date(pack.getDate()),
				pack.getVoltage());
		// 首次创建时updateTime = createTime
		card.setUpdateTime(card.getCreateTime());
		card.setSiteId(pack.getSiteId());
		return card;
	}

	/**
	 * 把携带激发器信息的数据包转换成设备对象
	 * 
	 * @param pack
	 *            数据包对象
	 * @return DevicePO
	 */
	private DevicePO toExciterDevicePO(LocationInfoPack pack) {
		DevicePO exciter = new DevicePO(pack.getExciterId(), 2, new Date(pack.getDate()));
		// 首次创建时updateTime =createTime
		exciter.setUpdateTime(exciter.getCreateTime());
        // TODO 根据本地监测端口获取对应 siteId @gaohui 2013-10-14
		exciter.setSiteId(pack.getSiteId());
		return exciter;
	}
}
