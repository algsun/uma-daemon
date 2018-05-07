package com.microwise.uma.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.po.CircuitedActionPO;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.util.ConfigureProperties;

/**
 * 往返行为检出线程：从用户行为记录表中查找配对的往返行为并记录到往返行为表中
 * 
 * @author 侯晓成
 * @date 2013-05-16 16 :29
 * 
 * 
 */
@Component
public class CircuitedActionCheckProcessor implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(CircuitedActionCheckProcessor.class);

	/**
	 * 以查找出的待入库的往返行为队列
	 */
	private List<CircuitedActionPO> circuitedActions = new ArrayList<CircuitedActionPO>();

	/**
	 * 用户的行为
	 */
	private List<UserActionPO> userActions = new ArrayList<UserActionPO>();

	/**
	 * 本次查找的时间刻度
	 */
	private long checkTime = -1;

	/**
	 * 本次配对处理的行为
	 */
	private List<UserActionPO> checkedActions = new ArrayList<UserActionPO>();

	/**
	 * 用户行为service
	 */
	@Autowired
	private UserActionService userActionService;

	/** 系统队列 总控制器 */
	@Autowired
	private AllController allController;

	/** 往行为超时时间 */
	private static long actionTimeOut = 24;

	@Override
	public void run() {
		logger.info("往返规则匹配线程启动");
		actionTimeOut = SystemConfigure.getConfigure(ConfigureProperties.GO_ACTION_TIME_OUT,
				actionTimeOut);
		logger.info("往返规则超时时间为" + actionTimeOut + "小时");
		actionTimeOut = actionTimeOut * 60 * 60 * 1000;
		while (!SystemConfigure.exit) {
			try {
				// 循环所有需要 往返规则匹配的用户
				for (Long userId : allController.getCircuitedActionCheckUserIds()) {
					// 获取用户行为
					getUserActions(userId);
					if (userActions != null && userActions.size() > 0) {
						// 往返配对
						checkGoBack(userId);
						// 入库操作
						saveCircuitedAction(userId);
						// 更新状态标志
						updateCheckedActions();
					}
				}
				Thread.sleep(1);
			} catch (Exception e) {
				logger.error("往返行为检出线程", e);
			}
		}
	}

	/**
	 * 从数据库查找用户的所有未匹配的行为
	 */
	private void getUserActions(Long userId) {
		// 清理对象 每次查找前做临时存储的清空
		clearAll();
		// 从数据库查找行为：查询时按照行为发生时间从小到大排序
		userActions = userActionService.getUserActions(userId);
	}

	/**
	 * 往返规则配对
	 */
	private void checkGoBack(Long userId) {
		// 配对时应该记录时间刻度
		if (userActions != null && userActions.size() > 0) {
			// 配对Map，用户后面的返规则找往规则:checkMap中只保存往规则
			Map<Long, UserActionPO> checkMap = new HashMap<Long, UserActionPO>();
			for (UserActionPO action : userActions) {
				// 如果是往规则，则更新checkMap
				if (action.getType() == 3) {
					// 判断 往规则是否超时
					if (action.getOcctime() < (System.currentTimeMillis() - actionTimeOut)) {
						action.setCheckFlag(UserActionPO.CHECK_FLAG_ERRPAIR);
						checkedActions.add(action);
						logger.info("往规则匹配超时：userId = " + userId + ",actionName ="
								+ action.getActionName());
						continue;
					}
					// 判断 checkMap 中是否已经存在该往规则
					UserActionPO last = checkMap.get(action.getParentActionTempletId());
					if (last != null) {
						last.setCheckFlag(UserActionPO.CHECK_FLAG_ERRPAIR);
						checkedActions.add(last);
						logger.info("未匹配到返规则的往规则：userId = " + userId + ",actionName ="
								+ action.getActionName());
					}
					// 将该往规则 存放在 checkMap 中，用来给 返规则匹配
					checkMap.put(action.getParentActionTempletId(), action);
					// 设置规则检测时间为 当前往规则时间（规则是否成功匹配没有影响，会通过checkFlag过滤）
					checkTime = action.getOcctime();
				} else if (action.getType() == 4) {
					// 返归则找往规则时需要使用 remove方式来获取 （获取后从checkMap中删除，一个往规则只做一次匹配）
					UserActionPO goAction = checkMap.remove(action.getParentActionTempletId());
					// 返归则不论是否找到与之对应的往规则都需要修改检查状态
					checkedActions.add(action);
					// 如果没有往规则，出现一个单独的返归则，则不作配对，进行下一个规则判断
					if (goAction == null) {
						action.setCheckFlag(UserActionPO.CHECK_FLAG_ERRPAIR);
						logger.info("未匹配到往规则的返规则：userId = " + userId + ",actionName ="
								+ action.getActionName());
						continue;
					} else {
						action.setCheckFlag(UserActionPO.CHECK_FLAG_PAIR);
						goAction.setCheckFlag(UserActionPO.CHECK_FLAG_PAIR);
						// 把检出的往规则记录，做状态修改
						checkedActions.add(goAction);
						// 合成一个往返记录
						CircuitedActionPO circuitedAction = new CircuitedActionPO();
						circuitedAction.setActionTempletId(action.getParentActionTempletId());
						circuitedAction.setUserId(userId);
						circuitedAction.setGoActionTempId(goAction.getTempId()); // 设置往规则Id
						circuitedAction.setGoTime(goAction.getOcctime());
						circuitedAction.setBackActionTempId(action.getTempId());// 设置返规则id
						circuitedAction.setBackTime(action.getOcctime());
						// 把往返规则加入到入库队列中
						logger.info("匹配到往返规则userId=" + userId + ",goActionName = "
								+ goAction.getActionName() + ",outActionName = "
								+ action.getActionName());
						circuitedActions.add(circuitedAction);
					}
				}
			}
		}
	}

	/**
	 * 配对结果进行入库
	 */
	private void saveCircuitedAction(Long userId) {
		// 往返结果入库
		if (circuitedActions != null && circuitedActions.size() > 0) {
			userActionService.saveCircuitedActions(circuitedActions);
		}
		// 分析记录时间刻度入库
		if (checkTime > 0) { // 初始化checkTime 为 -1 ， 如果checkTime> 0 则说明有新的刻度需要入库
			userActionService.updateAnalyseStateOfAction(userId, checkTime);
		}
	}

	/**
	 * 更新配对状态
	 */
	private void updateCheckedActions() {
		userActionService.updateUserActionFlags(checkedActions);
	}

	/**
	 * 对线程对象做初始化，避免状态未修改造成后面的信息错误或者重复入库
	 */
	private void clearAll() {
		this.checkedActions = new ArrayList<UserActionPO>();
		this.checkTime = -1;
		// 入库后应该对该用户的数据做清理，避免重复提交
		circuitedActions.clear();
		this.circuitedActions = new ArrayList<CircuitedActionPO>();
		this.userActions = new ArrayList<UserActionPO>();
	}
}
