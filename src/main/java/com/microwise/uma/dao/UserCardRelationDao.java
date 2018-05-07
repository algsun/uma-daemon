package com.microwise.uma.dao;

import java.util.List;

import com.microwise.uma.po.UserCard;

/**
 * 人卡绑定关系Dao接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:40:48
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface UserCardRelationDao {

	/**
	 * 
	 * 查询所有的人卡绑定
	 * 
	 * @return
	 */
	public List<UserCard> findAllRelations();

}
