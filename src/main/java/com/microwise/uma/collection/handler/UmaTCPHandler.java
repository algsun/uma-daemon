package com.microwise.uma.collection.handler;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.microwise.uma.bean.Request;
import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.pack.BasePack;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.processor.CardReaderProcessor;

/**
 * 数据请求handler
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:00:04
 * 
 * @check 2013-05-06 xubaoji svn:3232
 * @check 2013-10-14 @gaohui #5890
 */
@Component
public class UmaTCPHandler extends IoHandlerAdapter {

	private static Logger logger = LoggerFactory.getLogger(UmaTCPHandler.class);

	/** 字节队列 每个读卡器对应一个 基于线程同步的SynchronousQueue 队列 */
	private Map<String, Queue<Byte>> allByteQueueMap = new ConcurrentHashMap<String, Queue<Byte>>();

	/** 用来获得基本数据包队列 */
	@Autowired
	private AllController allController;

	/** 系统初始化实体 */
	@Autowired
	private SystemConfigure systemConfigure;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.debug("Message received : " + message);

		// 获得 session  ip 和 port 组合
		String ipAndPort = getIpAndPort(session);
		if (message != null) {
			IoBuffer msg = (IoBuffer) message;

			// 获得设备实际发送数据 字节数组
			byte[] realityBytes = new byte[msg.limit()];
			msg.get(realityBytes);

			// 将 接受到的字节数组存放进对应的字节队列中
			for (byte b : realityBytes) {
				getByteQueue(ipAndPort).offer(b);
			}
			logger.debug("IP:PORT为：" + ipAndPort + "的读卡器上传的实际字节数据：" + Arrays.toString(realityBytes));
			// 将读卡器发送数据 拆分为数据包
			InetSocketAddress localAddress = (InetSocketAddress)session.getLocalAddress();
			packetSplitter(ipAndPort,SystemConfigure.portSites.get(localAddress.getPort()));
		}

	}
	
	/**
	 * 将读卡器发送数据 拆分为数据包
	 * 
	 * @author xu.baoji
	 * @date 2013-9-23
	 */
	private void packetSplitter(String ipAndPort,String siteId) {
		Queue<Byte> byteQueue = getByteQueue(ipAndPort);
		while (!byteQueue.isEmpty()) {
            BasePack basePack = getBasePackFromQueue(byteQueue);
            // 不能解析出这一包，等待接收数据再次处理
            if (basePack == null) {
                return;
            }
            basePack.setIpAndPort(ipAndPort);
            basePack.setSiteId(siteId);
            allController.push(basePack);
		}
	}

	/**
	 * 从byte队列中组装一个基本包
	 * 
	 * @param byteQueue
	 *            一个数据接收队列
	 * 
	 * @return BaseaPack 一个基本数据包
	 */
	private BasePack getBasePackFromQueue(Queue<Byte> byteQueue) {
		// 判断是否至少大于一个包头（包头长度为7）
		if (byteQueue != null && byteQueue.size() > 7) {
			byte header = byteQueue.peek();
			if ((header & 0xff) != 06) { // 判断是否为包开头
				// 如果不是包开始，则说明是错包，继续取下一个byte判断是否为包头
				byteQueue.poll();
				return null;
			}
			Byte[] headers = new Byte[3];// 取前三个byte ，第二位第三位为长度
			byteQueue.toArray(headers);

			int len = (headers[1] << 8) | (headers[2] & 0xff); // 转换长度
			// 队列长度大于或等于一个完整的包长（3 为 起始符和包长占有的字节长度）
			if (byteQueue.size() >= len + 3) {
				ByteBuffer buffer = ByteBuffer.allocate(len + 3);
				// 用for循环从队列中poll出一个整包放到bytebuffer中
				for (int i = 0; i < len + 3; i++) {
					buffer.put(byteQueue.poll());
				}
				buffer.position(0);
				// 验证通过
				BasePack pack = new BasePack();
				pack.setDate(System.currentTimeMillis());
				pack.setDatas(buffer);
				return pack;
			}
		}
		return null;
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("Message Sent : sessionId : " + session.getId() + " ; message :" + message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("异常", cause);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("session closed" + session.getId());
		// 当读卡器 关闭时 由于下次再开启后 读卡器端口会改变 要清理  读卡器的一些资源
		String ipAndPort = getIpAndPort(session);
		// 清理 该读卡器对应的  request 
		systemConfigure.removeReaderRequest(ipAndPort);
		// 清理 该读卡器 对应的 字节队列
		allByteQueueMap.remove(ipAndPort);
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		try {
			// 获得读卡器 自定义 request 对象
			String ipAndPort = getIpAndPort(session);
			Request request = systemConfigure.getReaderRequest(ipAndPort);
			// 添加 session 和 系统时间到request 对象
			request.setSession(session);
			request.setTimes(System.currentTimeMillis());
			request.setIpAndPort(ipAndPort);

			// 获得读卡器对象
			DevicePO readerDevice = request.getReaderDevice();
			if (readerDevice == null) {
				// 从系统缓存中获得
				readerDevice = systemConfigure.getCardReaders().get(ipAndPort);
				if (readerDevice == null) {
					readerDevice = new DevicePO();
					// 添加读卡器 ip 和端口
					InetSocketAddress remoteAddress = (InetSocketAddress) session.getRemoteAddress();
					readerDevice.setIp(remoteAddress.getAddress().getHostAddress());
					readerDevice.setPort(remoteAddress.getPort());
					
					readerDevice.setCreateTime(new Date()); // 读卡器创建时间
					readerDevice.setType(1);
					
					InetSocketAddress localAddress = (InetSocketAddress)session.getLocalAddress();
					// 设置读卡器 站点
					readerDevice.setSiteId(SystemConfigure.portSites.get(localAddress.getPort()));
					request.setReaderDevice(readerDevice);
				}
			}

			// 获得 读卡器 线程
			CardReaderProcessor cardReaderProcessor = request.getCardReaderProcessor();
			if (cardReaderProcessor == null) {
				// 开启读卡器线程
				if (request.isNeedRunCardReaderProcessor()) {
					 cardReaderProcessor = applicationContext
							.getBean(CardReaderProcessor.class);
					 cardReaderProcessor.setIpAndPort(ipAndPort);
					 new Thread(cardReaderProcessor, "读卡器线程").start();
					 request.setCardReaderProcessor(cardReaderProcessor);
					logger.info("开启读卡器线程 ip：port=" + ipAndPort);
				} else {
					logger.info("读卡器不需要开启线程ip：port=" + ipAndPort);
				}
			}
		} catch (Throwable e) {
			logger.info("开启连接异常", e);
		}
		logger.info("session open" + session.getId());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		//logger.info("session idle " + session.getId());
	}

	/**
	 * 根据读卡器 ip 获得 该读卡器 的byte 队列
	 * 
	 * @param ipAndPort 读卡器ip
	 * @author 许保吉
	 * @date 2013.09.23
	 * 
	 * @return Queue<Byte> 基于线程同步的队列
	 */
	private Queue<Byte> getByteQueue(String ipAndPort) {
		Queue<Byte> byteQueue = allByteQueueMap.get(ipAndPort);
		if (byteQueue == null) {
			byteQueue = new ConcurrentLinkedQueue<Byte>();
			allByteQueueMap.put(ipAndPort, byteQueue);
		}
		return byteQueue;
	}

	/** 此get 方法只为首页 展示 读卡器 字节接受队列 状态使用 */
	public Map<String, Queue<Byte>> getAllByteQueueMap() {
		return allByteQueueMap;
	}
	
	/**获得session 连接的 ip和 port 组合 */
	private  String  getIpAndPort(IoSession session){
		InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
		String ip = socketAddress.getAddress().getHostAddress();
		int port = socketAddress.getPort();
		return ip+":"+port;
	}
}
