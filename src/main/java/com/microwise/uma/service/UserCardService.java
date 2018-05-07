package com.microwise.uma.service;

import java.util.List;

import com.microwise.uma.po.UserCard;

/**
 * 人卡绑定关系service接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:18:57
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface UserCardService {

	/**
	 * 查找所有的人卡绑定关系
	 * 
	 * @return List<UserCard>
	 */
	public List<UserCard> findAllRelations();

}
