package com.microwise.uma.dao;

import java.util.List;

import com.microwise.uma.po.PortSiteMapping;

/**
 * 系统 dao
 * 
 * @author xu.baoji
 * @date 2013-10-11
 */
public interface SystemDao {

	/***
	 * 根据监听端口查询 系统站点
	 * 
	 * @author xu.baoji
	 * @date 2013-10-11
	 *
	 * @return List<PortSiteMapping> 端口和站点对应关系列表
	 */
	public List<PortSiteMapping> findPortSiteMappings();

	/***
	 * 查询 设备白名单过滤表中激发器设备
	 * 
	 * @author xu.baoji
	 * @date 2013-10-12
	 * 
	 * @param siteId
	 *            站点id
	 * @return List<String> 激发器 sn 列表
	 */
	public List<String> findUsedExciterSn(String siteId);

}
