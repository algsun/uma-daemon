package com.microwise.uma.proxy.impl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.collection.CollectionService;
import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.ProxyDataProvider;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.proxy.CollectionProxy;
import com.microwise.uma.util.ConfigureProperties;

/**
 * Web部分调用后台服务代理 ： 后台线程服务启动 ， 前后台交互控制
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:04:27
 * 
 * @check 2013-05-07 xubaoji svn:2994
 */
@Component
public class CollectionProxyImpl implements CollectionProxy {
	public static final Logger logger = LoggerFactory.getLogger(CollectionProxyImpl.class);

	/** mina socket服务 */
	@Autowired
	private CollectionService collectionService;

	/** 解析入库线程控制 */
	@Autowired
	private AllController allController;

	/** 初始化参数 */
	@Autowired
	private SystemConfigure systemconfigure;

	/** 数据提供器 */
	@Autowired
	private ProxyDataProvider proxyDataProvider;

	public void systemRun() {
		try {
			// 初始化系统参数
			systemconfigure.init();

			if (SystemConfigure.portSites.isEmpty() || SystemConfigure.usedExciterMap.isEmpty()) {
				logger.error("未配置tcp监听端口对应站点，或者没有配置激发器白名单");
				throw new RuntimeException("未配置tcp监听端口对应站点，或者没有配置激发器白名单");
			}
			// 启动系统 业务线程
			allController.runAllThread();

			// 等待系统配置初始化完毕
			// TODO 可以不睡觉，启动先后不影响 @gaohui 2013-09-23
			Thread.sleep(5000);
			// 启动 系统 tcp 服务监听线程
			collectionService.run(SystemConfigure.getConfigure(ConfigureProperties.BUFFERSIZE,
					SystemConfigure.BUFFER_SIZE),new ArrayList<Integer>(SystemConfigure.portSites.keySet()));
		} catch (Exception e) {
			logger.error("系统启动出错", e);
		}
	}

	@Override
	public void systemStop() {
		collectionService.stop();
		SystemConfigure.exit = true;
	}

	@Override
	public SystemConfigure getSystemConfigure() {
		return this.systemconfigure;
	}

	@Override
	public ProxyDataProvider getProxyDataProvider() {
		return this.proxyDataProvider;
	}

}
