package com.microwise.uma.dao;

import com.microwise.uma.po.AnalyseStatePO;

/**
 * 分析状态记录Dao
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:38:36
 * 
 * @check 2013-05-07 xubaoji svn:3012
 */
public interface AnalyseStateDao {

	/**
	 * 查找用户分析位置记录信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public AnalyseStatePO findStateByUseId(Long userId);

	/**
	 * 更新分析记录中用户位置记录时间
	 * 
	 * @param userId
	 *            用户id
	 * @param times
	 *            分析时间 位置记录表中的时间
	 */
	public void updateLocationTime(Long userId, Long times);

	/**
	 * 更新分析记录中用户行为分析记录时间
	 * 
	 * @param userId
	 *            用户id
	 * @param times
	 *            分析时间--行为记录表中的时间
	 */
	public void updateActionTime(Long userId, Long times);

	/**
	 * 保存分析状态
	 * 
	 * @param analyseState
	 *            分析记录对象
	 */
	public void saveAnalyseState(AnalyseStatePO analyseState);

}
