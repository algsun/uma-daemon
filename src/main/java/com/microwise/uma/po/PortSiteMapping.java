package com.microwise.uma.po;

/**
 * 监听端口 和 站点 对应实体
 * 
 * @author xu.baoji
 * @date 2013-10-15
 */
public class PortSiteMapping {

	/**id*/
	private int id;

	/**监听端口*/
	private int port;

	/**监听端口对应站点*/
	private String siteId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

}
