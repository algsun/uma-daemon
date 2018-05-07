package com.microwise.uma.service;

import java.util.Date;
import java.util.List;

import com.microwise.uma.po.CircuitedActionPO;
import com.microwise.uma.po.UserActionPO;

/**
 * 用户行为service
 * 
 * @author houxiaocheng
 * @date 2013-4-25 上午11:39:12
 * 
 * @check 2013-05-07 xubaoji svn:2994
 */
public interface UserActionService {

	/**
	 * 更新位置分析时间
	 * 
	 * @param userId
	 *            用户ID
	 * @param times
	 *            本次分析的最后时间
	 */
	public void updateAnalyseStateOfLoaction(Long userId, Long times);

	/**
	 * 更新行为分析时间
	 * 
	 * @param userId
	 *            用户ID
	 * @param times
	 *            本次分析的最后时间
	 */
	public void updateAnalyseStateOfAction(Long userId, Long times);

	/**
	 * 用户行为入库
	 * 
	 * @param actions
	 *            用户行为队列
	 */
	public void saveUserActions(List<UserActionPO> actions);

	
	/**
	 * 用户往返行为入库
	 * 
	 * @param actions
	 *            用户匹配后的往返行为List
	 */
	public void saveCircuitedActions(List<CircuitedActionPO> actions);
	
	/**
	 * 获得所有已经产生行为的用户的最新行为
	 * 
	 * @param date
	 *            行为失效时间：比如如果需要设置24小时前的行为失效，则可以设置Date为 最新的系统时间 -1 天
	 * 
	 * @return List<UserActionPO> 所有有行为用户的最新行为
	 */
	public List<UserActionPO> getUserLastAction(Date date);

	/**
	 * 查询分析记录时间后某个用户的行为记录
	 * TODO 注释有问题
     *
	 * @param userId
	 *            用户id
	 * @param date
	 *            时间刻度
	 * @return List<UserActionPO>
	 */
	public List<UserActionPO> getUserActions(Long userId);

	/**
	 * 更新用户行为分析状态标识
	 * 		把用户行为的checkFlag字段修改为当前设置的值:避免重复查找多次匹配
	 * 
	 * @param userActions
	 *            已分析过的用户行为
	 */
	public void updateUserActionFlags(List<UserActionPO> userActions);
}
