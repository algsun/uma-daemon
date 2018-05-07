package com.microwise.uma.dao;

import java.util.List;
import java.util.Queue;

import com.microwise.uma.po.RealtimeLocationPO;

/**
 * 实时位置dao接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:39:46
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
public interface RealTimeLocationDao {

	/**
	 * 保存实时位置点
	 * 
	 * @param rlocations
	 *            实时位置队列
	 */
	public void saveLocationInfo(Queue<RealtimeLocationPO> rlocations);

	/**
	 * 更新实时位置
	 * 
	 * @param realtimeLocationPO
	 * @param userId
	 */
	public void updateLocationInfoByUserId(RealtimeLocationPO realtimeLocationPO);

	/**
	 * 查找所有的实时位置
	 * 
	 * @return
	 */
	public List<RealtimeLocationPO> findAll();
}
