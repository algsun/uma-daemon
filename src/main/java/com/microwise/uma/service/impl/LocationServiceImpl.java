package com.microwise.uma.service.impl;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.AnalyseStateDao;
import com.microwise.uma.dao.LocationDao;
import com.microwise.uma.po.AnalyseStatePO;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.service.LocationService;

/**
 * 位置信息service接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:07:25
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Service
@Transactional
@Scope("prototype")
public class LocationServiceImpl implements LocationService {

	/** 位置dao接口 */
	@Autowired
	private LocationDao locationDao;

	/** 分析记录dao接口 */
	@Autowired
	private AnalyseStateDao analyDao;

	@Override
	public void saveLocationsBatch(Queue<LocationPO> locations) {
		if (locations != null && locations.size() > 0) {
			for (LocationPO locationPo : locations) {
				locationDao.saveLocationInfo(locationPo);
			}
		}
	}

	@Override
	public List<LocationPO> findLocationsByUserId(Long userId) {
		//查询分析记录
		AnalyseStatePO analyse = analyDao.findStateByUseId(userId);
		//如果分析记录不存在，则查询用户所有的位置点，否则从分析记录时间点查起
		Long times = analyse != null ? analyse.getLocationTime() : null;
		return locationDao.getLoationPointsByUserId(userId, times);
	}
}
