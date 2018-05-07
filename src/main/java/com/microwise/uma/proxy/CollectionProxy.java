package com.microwise.uma.proxy;

import com.microwise.uma.controller.ProxyDataProvider;
import com.microwise.uma.controller.SystemConfigure;

/**
 * Web部分调用后台服务代理
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:05:00
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface CollectionProxy {

	/**
	 * 运行后台系统总接口
	 */
	public void systemRun();

	/**
	 * 获取系统初配置管理实例
	 * 
	 * @return SystemConfigure
	 */
	public SystemConfigure getSystemConfigure();

    /**
     * 获取 Http 接口数据提供器
     *
     * @return ProxyDataProvider
     */
    public ProxyDataProvider getProxyDataProvider();

    /**
     * 系统关闭
     */
    void systemStop();
}
