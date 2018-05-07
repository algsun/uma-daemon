package com.microwise.uma.dao;

import java.util.List;

import com.microwise.uma.po.LocationPO;

/**
 * 位置Dao接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:39:35
 * 
 * @check 2013-05-07 xubaoji svn:3181
 */
public interface LocationDao {

	/**
	 * 查询某个人在某个时间之后的所有位置点信息
	 * 
	 * @param userId
	 *            用户id
	 * @param times
	 *            时间戳（毫秒）
	 * @return List<LocationPO>
	 */
	public List<LocationPO> getLoationPointsByUserId(Long userId, Long times);

	/**
	 * 保存位置点信息
	 * 
	 * @param locationPO
	 *            位置对象
	 */
	public void saveLocationInfo(LocationPO locationPO);

}
