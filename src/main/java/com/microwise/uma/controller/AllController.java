package com.microwise.uma.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.pack.BasePack;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.processor.CircuitedActionCheckProcessor;
import com.microwise.uma.processor.MessagePackParseProcessor;
import com.microwise.uma.processor.PackDisassemblyProcessor;
import com.microwise.uma.processor.PackPersistenceProcessor;
import com.microwise.uma.processor.RegularCheckProcessor;

/**
 * 数据处理线程总控制器(用来启动系统业务功能)
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:50:41
 * 
 * @check 2013-05-06 xubaoji svn:3059
 */
@Component
public class AllController {

	private static final Logger logger = LoggerFactory.getLogger(AllController.class);

	/** 待解析基本数据包 */
	private Queue<BasePack> basePackQueue = new ConcurrentLinkedQueue<BasePack>();

	/** 已入库的历史位置队列：用来判断规则匹配线程是否执行 */
	private Queue<LocationPO> historyLocationQueue = new ConcurrentLinkedQueue<LocationPO>();

	/** 用户往返规则队列：用来判断往返规则匹配线程是否执行 */
	private Queue<UserActionPO> userActionQueue = new ConcurrentLinkedQueue<UserActionPO>();

	/** 对分包做排错、类型判断线程 分为：读卡器信息包、位置信息包 */
	@Autowired
	private MessagePackParseProcessor messagePackParseProcessor;

	/** 解析包并封装成可持久化对象线程 */
	@Autowired
	private PackDisassemblyProcessor packDisassemblyProcessor;

	/** 持久化数据包线程 */
	@Autowired
	private PackPersistenceProcessor packPersistanceProcessor;

	/** 规则匹配线程 */
	@Autowired
	private RegularCheckProcessor regularCheckProcessor;

	/**
	 * 往返规则查找线程
	 */
	@Autowired
	private CircuitedActionCheckProcessor circuitedCheckProcessor;

	/** 存放系统线程 */
	private List<Thread> threads = new ArrayList<Thread>();

	public AllController() {
	}

	/**
	 * 运行所有的服务线程
	 */
	public void runAllThread() {

		/*
		 * new Thread("系统主线程") { public void run() {
		 */
		try {// 等待系统初始化完毕（从数据库读数据、配置文件读取等完成后再运行）
			 // TODO 这样不太科学啊
			Thread.sleep(1);

			/*
			 * Thread checkPack = new Thread(messageBufferCheckProcessor,
			 * "拆包线程"); checkPack.start(); threads.add(checkPack);
			 */

			Thread parsePack = new Thread(messagePackParseProcessor, "数据包分类线程");
			parsePack.start();
			threads.add(parsePack);

			Thread disassemblyPack = new Thread(packDisassemblyProcessor, "解包线程");
			disassemblyPack.start();
			threads.add(disassemblyPack);

			Thread persistancePack = new Thread(packPersistanceProcessor, "入库线程");
			persistancePack.start();
			threads.add(persistancePack);

			Thread regularCheck = new Thread(regularCheckProcessor, "规则匹配线程");
			regularCheck.start();
			threads.add(regularCheck);

			Thread circuitedCheck = new Thread(circuitedCheckProcessor, "往返行为检出线程");
			circuitedCheck.start();
			threads.add(circuitedCheck);
			/*
			 * } }.start();
			 */
		} catch (Exception e) {
			logger.info("系统主线程异常", e);
		}
	}

	/***
	 * 获得需要规则匹配的用户
	 * 
	 * @return Set<Long> 需要进行规则匹配的用户列表
	 */
	public Set<Long> getRegularCheckUsers() {
		Set<Long> userIds = new LinkedHashSet<Long>();
		for (LocationPO location : historyLocationQueue) {
			userIds.add(location.getUserId());
		}
		historyLocationQueue.clear();
		return userIds;
	}

	/***
	 * 批量添加 历史位置到 历史位置队列
	 * 
	 * @param locations
	 */
	public void addBeachToHistroyLocationQueue(Collection<LocationPO> locations) {
		historyLocationQueue.addAll(locations);
	}

	/***
	 * 获得需要 进行往返规则匹配的用户id 列表
	 *
	 * @return Set<Long> 用户id 列表
	 */
	public Set<Long> getCircuitedActionCheckUserIds(){
		Set<Long> userIds = new LinkedHashSet<Long>();
		for (UserActionPO userAction : userActionQueue) {
			userIds.add(userAction.getUserId());
		}
		userActionQueue.clear();
		return userIds;
	}

	/***
	 * 批量添加元素 到用户往返规则队列中
	 * 
	 * @param userActions
	 *            用户往返规则
	 */
	public void addBeachToUserActionQueue(Collection<UserActionPO> userActions) {
		userActionQueue.addAll(userActions);
	}

	/**
	 * 压数据包到待解析队列中 不用做时间判断
	 * 
	 * @param pack
	 *            数据包队列
	 */
	public void push(BasePack pack) {
		this.basePackQueue.offer(pack);
	}

	/**
	 * 获取待解析数据包队列
	 * 
	 * @return LinkedList<BasePack>
	 */
	public List<BasePack> checkBasePackList() {
		List<BasePack> buffList = new ArrayList<BasePack>();
		while (!basePackQueue.isEmpty()) {
			buffList.add(basePackQueue.poll());
		}
		return buffList;
	}

	/***
	 * 判断 是否有 基本数据包需要 分类
	 * 
	 * @author xu.baoji
	 * @date 2013-9-17
	 * 
	 * @return true 需要 false 不需要
	 */
	public boolean needCheckBasePack() {
		if (basePackQueue.size() > 0) {
			return true;
		}
		return false;
	}

	public Queue<BasePack> getBasePackQueue() {
		return basePackQueue;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	/**
	 * 可控制线程 增加异常记录次数，避免系统出现问题后线程不停的启动而造成日志文件迅速增长导致磁盘空间不足
	 * 
	 * 原理： 继承Thread类，增加times属性
	 * 
	 * @author Administrator
	 * 
	 */
	class UmaDaemonThread extends Thread {

		/**
		 * 发生错误的次数
		 */
		private int errorTimes;

		public void umaStart() {
			try {
				super.start();
			} catch (Exception e) {
				e.printStackTrace();
				errorTimes++;
				super.start();
			}

		}

		public int getErrorTimes() {
			return errorTimes;
		}

		public void setErrorTimes(int errorTimes) {
			this.errorTimes = errorTimes;
		}
	}
}
