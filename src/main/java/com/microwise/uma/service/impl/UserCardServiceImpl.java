package com.microwise.uma.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.UserCardRelationDao;
import com.microwise.uma.po.UserCard;
import com.microwise.uma.service.UserCardService;

/**
 * 用户-卡绑定service接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:09:21
 * 
 * @check 2013-05-07 xubaoji svn:2913
 */
@Service
@Transactional
@Scope("prototype")
public class UserCardServiceImpl implements UserCardService {

	/**
	 * 用户-卡绑定关系dao接口
	 */
	@Autowired
	UserCardRelationDao userCardRelationDao;

	@Override
	public List<UserCard> findAllRelations() {
		return userCardRelationDao.findAllRelations();
	}

}
