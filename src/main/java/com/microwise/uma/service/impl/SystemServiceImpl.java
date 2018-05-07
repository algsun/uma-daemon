package com.microwise.uma.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.SystemDao;
import com.microwise.uma.po.PortSiteMapping;
import com.microwise.uma.service.SystemService;

/**
 * 系统 service  实现
 * 
 * @author xu.baoji
 * @date 2013-10-11
 */
@Service
@Transactional
@Scope("prototype")
public class SystemServiceImpl implements SystemService{
	
	@Autowired
	private SystemDao systemDao ;
	
	@Override
	public List<PortSiteMapping> findPortSiteMappings() {
		return systemDao.findPortSiteMappings();
	}
	
	@Override
	public List<String> findUsedExciterSn(String siteId) {
		return systemDao.findUsedExciterSn(siteId);
	}

}
