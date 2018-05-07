package com.microwise.uma.service;

import java.util.List;
import java.util.Queue;

import com.microwise.uma.po.RealtimeLocationPO;

/**
 * 实时位置service接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:18:28
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
public interface RealtimeLocationService {

	/**
	 * 保存或者更新最新位置
	 * 
	 * @param saveRealtimeList
	 *            保存列表
	 * @param updateRealtimeList
	 *            更新列表
	 */
	public void saveOrUpdateLocations(
			Queue<RealtimeLocationPO> saveRealtimeList,
			Queue<RealtimeLocationPO> updateRealtimeList);

	/**
	 * 查找所有的最新位置点
	 * 
	 * @return List<RealtimeLocationPO>
	 */
	public List<RealtimeLocationPO> findAll();

}
