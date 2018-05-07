package com.microwise.uma.servlet;

import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.microwise.uma.proxy.CollectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * spring初始化监听器
 * 
 * @author houxiaocheng
 * @date 2013-4-25 下午2:25:58
 * 
 * @check 2013-05-07 xubaoji svn:2954
 */
public class CollectionListener extends ContextLoaderListener {

	private static final Logger log = LoggerFactory
			.getLogger(CollectionListener.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("系统启动");

		ServletContext servletContext = servletContextEvent.getServletContext();
		// 系统启动时间
		servletContext.setAttribute("app.startTime", new Date());
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        CollectionProxy collectionProxy = appContext.getBean(CollectionProxy.class);
        collectionProxy.systemRun();
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info("系统停止");
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        CollectionProxy collectionProxy = appContext.getBean(CollectionProxy.class);
        collectionProxy.systemStop();
	}

}
