package com.microwise.uma.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.LocationDao;
import com.microwise.uma.po.LocationPO;

/**   
* 位置dao接口的实现
* @author houxiaocheng 
* @date 2013-4-24 下午3:05:14 
* 
* @check 2013-05-07 xubaoji svn:3181
*/
@Repository
@Scope("prototype")
public class LocationDaoImpl extends BaseDao implements LocationDao {

	@Override
	public List<LocationPO> getLoationPointsByUserId(Long userId, Long times) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("curTime", times);
		return getSqlSession().selectList(
				"mybatis.LocationDao.findLoation", params);

	}

	@Override
	public void saveLocationInfo(LocationPO locationPO) {
		getSqlSession().insert("mybatis.LocationDao.saveLoationBacth",
				locationPO);
	}

}
