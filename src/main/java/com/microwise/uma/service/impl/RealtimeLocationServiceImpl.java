package com.microwise.uma.service.impl;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.RealTimeLocationDao;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.service.RealtimeLocationService;

/**
 * 实时位置信息service 接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:08:11
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Service
@Transactional
@Scope("prototype")
public class RealtimeLocationServiceImpl implements RealtimeLocationService {

	/**
	 * 实时位置dao接口
	 */
	@Autowired
	private RealTimeLocationDao realtimeDao;

	@Override
	public void saveOrUpdateLocations(
			Queue<RealtimeLocationPO> saveRealtimeList,
			Queue<RealtimeLocationPO> updateRealtimeList) {
		//save队列重的数据需要做insert
		if (saveRealtimeList != null && saveRealtimeList.size() > 0) {
			realtimeDao.saveLocationInfo(saveRealtimeList);
		}
		//update队列中的数据需要做update	
		if (updateRealtimeList != null && updateRealtimeList.size() > 0) {
			for (RealtimeLocationPO realtime : updateRealtimeList) {
				realtimeDao.updateLocationInfoByUserId(realtime);
			}
		}

	}

	@Override
	public List<RealtimeLocationPO> findAll() {
		return realtimeDao.findAll();
	}

}
