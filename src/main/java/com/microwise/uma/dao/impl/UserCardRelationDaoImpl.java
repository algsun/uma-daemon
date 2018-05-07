package com.microwise.uma.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.UserCardRelationDao;
import com.microwise.uma.po.UserCard;

/**
 * 用户-卡绑定关系dao接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:06:36
 * 
 * @check 2013-05-07 xubaoji svn:2927
 */
@Repository
@Scope("prototype")
public class UserCardRelationDaoImpl extends BaseDao implements
		UserCardRelationDao {

	@Override
	public List<UserCard> findAllRelations() {
		List<UserCard> res = getSqlSession().selectList(
				"mybatis.UserCardRelationDao.findRelations");
		return res;
	}

}
