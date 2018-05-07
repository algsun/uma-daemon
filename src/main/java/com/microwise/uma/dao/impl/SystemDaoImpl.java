package com.microwise.uma.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.SystemDao;
import com.microwise.uma.po.PortSiteMapping;

/**
 * 系统dao 实现
 * 
 * @author xu.baoji
 * @date 2013-10-11
 */
@Repository
@Scope("prototype")
public class SystemDaoImpl extends BaseDao implements SystemDao {

	@Override
	public List<PortSiteMapping> findPortSiteMappings() {
		return getSqlSession().selectList("mybatis.SystemDao.findPortSiteMappings");
	}

	@Override
	public List<String> findUsedExciterSn(String siteId) {
		return getSqlSession().selectList("mybatis.SystemDao.findUsedExciterSn", siteId);
	}
}
