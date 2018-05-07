package com.microwise.uma.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.RegularDao;
import com.microwise.uma.po.Regular;

/**   
* 规则dao的实现
* @author houxiaocheng 
* @date 2013-4-24 下午3:05:46 
* 
* @check 2013-05-07 xubaoji svn:2927
*/
@Repository
@Scope("prototype")
public class RegularDaoImpl extends BaseDao implements RegularDao {

	@Override
	public List<Regular> findRegularByType(int type) {
		List<Regular> res = getSqlSession().selectList(
				"mybatis.RegularDao.findRegulars", type);
		return res;
	}

	@Override
	public List<Regular> findRegularByType() {
		List<Regular> res = getSqlSession().selectList(
				"mybatis.RegularDao.findAllRegulars");
		return res;
	}

	@Override
	public List<Regular> findRegularByParentId(long parentId, int type) {
		
		Map<String , Long> params = new HashMap<String , Long>();
		params.put("parentId", parentId);
		params.put("type", new Long(type));
		
		List<Regular> res = getSqlSession().selectList(
				"mybatis.RegularDao.findParentRegulars" , params);
		return res;
	}

	@Override
	public int getMaxRegularLen() {
		Integer len = getSqlSession().selectOne(
				"mybatis.RegularDao.findMaxRegularLen" , null);
		return (len != null)? len : 0;
	}

}
