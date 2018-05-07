package com.microwise.uma.bean;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.mina.core.session.IoSession;

import com.microwise.uma.pack.CardReaderPack;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.processor.CardReaderProcessor;

/**
 * 自定义 request 实体对象
 * 
 * @author xubaoji
 * @date 2013-5-2
 */
public class Request implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 读卡器 ip 和 port 组合 用来唯一 确认 读卡器 */
	private String ipAndPort;

	/** 读卡器 session */
	private IoSession session;

	/** 当前session 连接时的系统时间 */
	private long times;

	/** 读卡器设备 */
	private DevicePO readerDevice;

	private CardReaderProcessor cardReaderProcessor;

	/** 读卡器包队列 */
	private Queue<CardReaderPack> readerPack = new ConcurrentLinkedQueue<CardReaderPack>();

	public Request(IoSession session, long times) {
		super();
		this.session = session;
		this.times = times;
	}

	public DevicePO getReaderDevice() {
		return readerDevice;
	}

	public void setReaderDevice(DevicePO readerDevice) {
		this.readerDevice = readerDevice;
	}

	public Queue<CardReaderPack> getReaderPack() {
		return readerPack;
	}

	public void setReaderPack(Queue<CardReaderPack> readerPack) {
		this.readerPack = readerPack;
	}

	public Request() {
		super();
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

	public String getIpAndPort() {
		return ipAndPort;
	}

	public void setIpAndPort(String ipAndPort) {
		this.ipAndPort = ipAndPort;
	}

	public CardReaderProcessor getCardReaderProcessor() {
		return cardReaderProcessor;
	}

	public void setCardReaderProcessor(CardReaderProcessor cardReaderProcessor) {
		this.cardReaderProcessor = cardReaderProcessor;
	}

	/*** 是否需要开启 读卡器线程判断 true 需要，false 不需要 如果为false 已经开启的未关闭的读卡器线程要关闭 */
	public boolean isNeedRunCardReaderProcessor() {
		if (readerDevice.getDeviceId() != null && readerDevice.getSn() != null
				&& readerDevice.getVersion() != null) {
			return false;
		} else {
			return true;
		}
	}

}
