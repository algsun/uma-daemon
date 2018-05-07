package com.microwise.uma.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


/**   
* 基本Dao -- 创建数据库连接
* @author houxiaocheng 
* @date 2013-4-24 下午2:39:00 
* 
* @check 2013-05-07 xubaoji svn:2915
*/
public class BaseDao extends SqlSessionDaoSupport{


	protected static SqlSessionFactory sqlSessionFactory;

	/**
	 * 获取数据库连接session
	 * @param sqlSessionFactory
	 */
	@Autowired
	@Qualifier("uma-sqlSessionFactory")
	protected void setSqlSessionFactoryForAutowire(
			SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
		BaseDao.sqlSessionFactory = sqlSessionFactory;
	}
	
}


