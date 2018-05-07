package com.microwise.uma.service;

import java.util.List;
import java.util.Queue;

import com.microwise.uma.po.LocationPO;

/**
 * 位置信息service
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:17:45
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
public interface LocationService {

	/**
	 * 批量保存位置信息
	 * 
	 * @param locations
	 *            //位置点队列
	 */
	public void saveLocationsBatch(Queue<LocationPO> locations);

	/**
	 * 根据解析状态获取用户的位置信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<LocationPO> findLocationsByUserId(Long userId);
}
