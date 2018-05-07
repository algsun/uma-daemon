package com.microwise.uma.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.AnalyseStateDao;
import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.po.AnalyseStatePO;

/**
 * 分析记录接口Dao实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:03:55
 * 
 * @check 2013-05-07 xubaoji svn:3012
 */
@Repository
@Scope("prototype")
public class AnalyseStateDaoImpl extends BaseDao implements AnalyseStateDao {

	@Override
	public AnalyseStatePO findStateByUseId(Long userId) {
		return getSqlSession().selectOne("mybatis.AnalyseStateDao.findAnalyse",
				userId);
	}

	@Override
	public void updateLocationTime(Long userId, Long times) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("userId", userId);
		params.put("locationTime", times);
		getSqlSession().update(
				"mybatis.AnalyseStateDao.updateAnalyseLocationTime", params);
	}

	@Override
	public void updateActionTime(Long userId, Long times) {
		Map<String, Long> params = new HashMap<String, Long>();
		params.put("userId", userId);
		params.put("actionTime", times);
		getSqlSession().update(
				"mybatis.AnalyseStateDao.updateAnalyseActionTime", params);
	}

	@Override
	public void saveAnalyseState(AnalyseStatePO analyseState) {
		getSqlSession().insert(
				"mybatis.AnalyseStateDao.saveAnalyseState", analyseState);
	}

}
