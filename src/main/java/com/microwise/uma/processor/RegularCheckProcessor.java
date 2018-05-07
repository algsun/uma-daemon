package com.microwise.uma.processor;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.microwise.uma.bean.GoBackRegular;
import com.microwise.uma.bean.OneWayRegular;
import com.microwise.uma.bean.ParentRegular;
import com.microwise.uma.collection.client.UDPClient;
import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.po.Regular;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserPO;
import com.microwise.uma.service.LocationService;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.service.UserService;
import com.microwise.uma.util.ConfigureProperties;

/**
 * 规则统计线程 ：1、取出位置信息并组装成解析串 2、规则匹配 3、更新入库 4、维护分析状态表
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:05:16
 * 
 * @check 2013-05-07 xubaoji svn:3208
 * @check 2013-10-14 @gaohui #5820
 */
@Component
public class RegularCheckProcessor implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RegularCheckProcessor.class);

	/** 记录分析时间 */
	private Long lastCheckTime = 0L;

	/** 位置service */
	@Autowired
	private LocationService locationServ;

	/** 用户行为service */
	@Autowired
	private UserActionService userActionService;

	/** 用户信息 Service */
	@Autowired
	private UserService userService;

	/** 用户id 编号 */
	private Long userId = -1L;

	/** 队列 主控制器 */
	@Autowired
	private AllController allController;

	/**
	 * 规则统计线程 运行
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("规则匹配线程启动");
		while (!SystemConfigure.exit) {
			try {
				userId = -1L;
				logger.debug("-----规则分析开始-----");
				// 如果 存在规则 并且规则最大长度 大于0  进行匹配逻辑
				if (SystemConfigure.regulars != null && SystemConfigure.MaxRegLen > 0) {
					// 获得需要进行规则匹配的用户id 列表循环匹配
					for (Long userId : allController.getRegularCheckUsers()) {
						this.userId = userId;
						// 每个人规则分析前都要重新设置最后检查时间
						lastCheckTime = 0L;
						List<LocationPO> userLocations = locationServ.findLocationsByUserId(userId);
						// 用来存储 用户行为的集合
						List<UserActionPO> userActions = new LinkedList<UserActionPO>();
						
						// 当用户位置 记录 小于最大 规则长度 不做匹配
						if (userLocations == null || userLocations.size() < SystemConfigure.MaxRegLen) {
							continue;
						}

                        if (userLocations.size() >= SystemConfigure.MaxRegLen
								&& userLocations.size() >= 2) { // 规则最短为两个点
							try {
								lastCheckTime = userLocations.get(
										userLocations.size() - SystemConfigure.MaxRegLen + 1)
										.getCurrentTime();
							} catch (Exception e) {
								logger.info("最后匹配规则时间获取失败！本次匹配不做入库，等待下次匹配！");
								continue;
							}

							logger.debug("-userID : " + userId + " ; 本次匹配记录时间：" + lastCheckTime
									+ " ， 用户位置点个数：" + userLocations.size());
							// 将用户位置记录中激发器sn转换为 string 字符串
							String matcherS = locationToMatcherStr(userLocations);
							// 匹配规则
							userActions = matcherUserLocations(matcherS);

							logger.debug("-userID : " + userId + " ， 共找到行为个数 ："
									+ userActions.size());
							// 用户行为入库
							userActionService.saveUserActions(userActions);
							// 将 匹配的往返规则 批量添加到 用户规则队列
							allController.addBeachToUserActionQueue(userActions);
							// 更新分析记录时间
							userActionService.updateAnalyseStateOfLoaction(userId, lastCheckTime);
							logger.debug("--userID : " + userId + " 解析结束！-- ");
						}
					}
				}
				logger.debug("-----规则分析结束-----");
				Thread.sleep(SystemConfigure.getConfigure(
						ConfigureProperties.REGULAR_CHECK_SLEEPTIME,
						SystemConfigure.REGULAR_CHECK_SLEEPTIME));
			} catch (Exception e) {
				logger.error("规则匹配线程异常", e);
			}
		}
	}

	/**
	 * 拼装待匹配格式串
	 * 
	 * @param userLocations
	 *            用户的位置点队列
	 * @return
	 */
	private String locationToMatcherStr(List<LocationPO> userLocations) {
		StringBuilder matcherStr = new StringBuilder();
		for (LocationPO location : userLocations) {
			matcherStr.append("'")
                      .append(location.getDeviceId())
                      .append("'");

			matcherStr.append("{");
			matcherStr.append(location.getCurrentTime());
			matcherStr.append("}");
		}
		return matcherStr.toString();
	}

	/**
	 * 匹配所有的规则
	 * 
	 * @param matcherStr
	 *            待匹配位置str
	 * @return
	 */
	private List<UserActionPO> matcherUserLocations(String matcherStr) {
		List<UserActionPO> poList = new LinkedList<UserActionPO>();
        for (Long aLong : SystemConfigure.regulars.keySet()) {
            // 取得父规则
            ParentRegular pRegular = SystemConfigure.regulars.get(aLong);
            if (pRegular != null) {
                if (pRegular instanceof OneWayRegular) { // 如果是单程规则
                    poList.addAll(matcherOneWayAction(pRegular, matcherStr));
                } else if (pRegular instanceof GoBackRegular) { // 如果是往返规则
                    poList.addAll(mactherGoBackAction(pRegular, matcherStr));
                }
            }
        }
		return poList;
	}

	/**
	 * 匹配单程规则 : 不做去重，在一个人的所有行为匹配之后统一做去重
	 * 
	 * @param pRegular
	 *            父规则
	 * @param matcherStr
	 *            用户位置待匹配字符串
	 * @return 匹配后的行为列表
	 */
	private List<UserActionPO> matcherOneWayAction(ParentRegular pRegular, String matcherStr) {
		List<UserActionPO> poList = new LinkedList<UserActionPO>();
		OneWayRegular oneWayReg = (OneWayRegular) pRegular;
		if (oneWayReg.getSubRegulars() != null && oneWayReg.getSubRegulars().getSubRegs() != null) {
			for (Regular regular : oneWayReg.getSubRegulars().getSubRegs()) {
				if (regular.toPattern() != null) {
					mactherAction(poList, regular, matcherStr, oneWayReg.getId(), oneWayReg.getId()); // 单程规则使用父规则ID
				}
			}
		}
		return poList;
	}

	/**
	 * 匹配往返规则 :
	 * 
	 * @return 匹配后的行为列表
	 */
	private List<UserActionPO> mactherGoBackAction(ParentRegular pRegular, String matcherStr) {
		List<UserActionPO> poList = new LinkedList<UserActionPO>();
		GoBackRegular goBackRegular = (GoBackRegular) pRegular;
		// 获取往行为
		if (goBackRegular.getGoRegulars() != null
				&& goBackRegular.getGoRegulars().getSubRegs() != null) {
			for (Regular regular : goBackRegular.getGoRegulars().getSubRegs()) {
				if (regular.toPattern() != null) {
					mactherAction(poList, regular, matcherStr, regular.getId(),
							goBackRegular.getId()); // 往规则使用子规则Id
				}
			}
		}
		// 获取返行为
		if (goBackRegular.getBackRegulars() != null
				&& goBackRegular.getBackRegulars().getSubRegs() != null) {
			for (Regular regular : goBackRegular.getBackRegulars().getSubRegs()) {
				if (regular.toPattern() != null) {
					mactherAction(poList, regular, matcherStr, regular.getId(),
							goBackRegular.getId()); // 往规则使用子规则Id
				}
			}
		}
		return poList;
	}

	/**
	 * 匹配单个规则
	 * 
	 * @param poList
	 *            匹配后的结果保存列表
	 * @param regular
	 *            规则对象
	 * @param matcherStr
	 *            用户历史位置点
	 * @param actionTempId
	 *            要记录的规则Id
	 * @param parentActTempId
	 *            父规则Id
	 */
	private void mactherAction(List<UserActionPO> poList, Regular regular, String matcherStr,
			Long actionTempId, Long parentActTempId) {

		Pattern p = regular.toPattern();
		Matcher m = p.matcher(matcherStr);
		int start = 0;
		while (m.find(start)) {
			try { // 防止时间转换出现问题
				start = m.end();
				UserPO user = userService.getUserInfo(userId);
				UserActionPO userAc = new UserActionPO();
				userAc.setUserId(userId);

				userAc.setUserId(user.getUserId());
				userAc.setUserName(user.getUserName());
				userAc.setUserPhoto(user.getUserPhoto());
				userAc.setSn(user.getCardNO());

				userAc.setTempId(actionTempId);
				userAc.setParentActionTempletId(parentActTempId);
				userAc.setStartTime(Long.parseLong(m.group(1)));
				userAc.setOcctime(userAc.getStartTime());
				// 根据时间判断是否需要本次入库
				if (userAc.getStartTime() >= lastCheckTime) {
					// 如果行为的触发时间比时间刻度记录时间晚，则该行为不做入库
					continue;
				}
				userAc.setEndTime(Long.parseLong(m.group(2)));
				userAc.setType(regular.getType());
				// 时间设置 : 如果为返归则，则时间为endTime
				// if (userAc.getType() == 4) {
				// userAc.setOcctime(userAc.getEndTime());
				// }
				// modified by houxiaocheng
				// 行为统一采用结束时间作为行为发生时间
				userAc.setOcctime(userAc.getEndTime());
				userAc.setZoneId(regular.getZoneId());

				if (userAc.getType() == 3 || userAc.getType() == 4) {
					// UDP 通知往或返规则
					Gson gson = new Gson();
					UDPClient client = new UDPClient();
					client.send(gson.toJson(userAc).getBytes(Charsets.UTF_8));
					client.close();
				}
				// 判断用户当前匹配到的规则与用户上一个规则是否重复
				if (!isRepeatedAction(userAc)) {
					logger.info("匹配到规则userId=" + userId + "regularType=" + regular.getType()
							+ "regularName=" + regular.getName());
					poList.add(userAc);
				} else {
					logger.info("被过滤的重复规则 userId=" + userId + "regularType=" + regular.getType()
							+ "regularName=" + regular.getName());
				}
				// 更新用户最后一次规则 记录
				SystemConfigure.userLastActionMap.put(userId, userAc);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	/***
	 * 判断用户规则是否与该用户最后一次规则重复
	 * 
	 * @author xu.baoji
	 * @date 2013-9-29
	 * 
	 * @param action
	 * @return true 是重复，false 不是重复
	 */
	private boolean isRepeatedAction(UserActionPO action) {
		// 获得 用户的上一个行为
		UserActionPO lastPo = SystemConfigure.userLastActionMap.get(action.getUserId());
		if (lastPo == null) {
			return false;
		}
		return lastPo.isEqual(action);
	}
}
