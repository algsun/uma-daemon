package com.microwise.uma.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.RealTimeLocationDao;
import com.microwise.uma.po.RealtimeLocationPO;

/**
 * 实时位置dao接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:05:32
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Repository
@Scope("prototype")
public class RealtimeLocationDaoImpl extends BaseDao implements
		RealTimeLocationDao {

	@Override
	public void saveLocationInfo(Queue<RealtimeLocationPO> rlocations) {
		List<RealtimeLocationPO> rePolist = new ArrayList<RealtimeLocationPO>();
		while (!rlocations.isEmpty()) {
			rePolist.add(rlocations.poll());
		}
		if (rePolist.size() > 0) {
			getSqlSession().selectList(
					"mybatis.RealtimeLocationDao.saveLoationBacth", rePolist);
		}
	}

	@Override
	public void updateLocationInfoByUserId(RealtimeLocationPO realtime) {
		getSqlSession().update(
				"mybatis.RealtimeLocationDao.updateRealtimeLocation", realtime);

	}

	@Override
	public List<RealtimeLocationPO> findAll() {
		List<RealtimeLocationPO> res = getSqlSession().selectList(
				"mybatis.RealtimeLocationDao.findRealtimeLocations");
		return res;
	}

}
