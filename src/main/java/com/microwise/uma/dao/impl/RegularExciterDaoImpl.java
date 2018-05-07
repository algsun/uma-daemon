package com.microwise.uma.dao.impl;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.RegularExciterDao;
import com.microwise.uma.po.RegularExciter;

/**   
* 规则顺序dao接口
* @author houxiaocheng 
* @date 2013-4-24 下午3:06:08 
* 
* @check 2013-05-07 xubaoji svn:2927
*/
@Repository
@Scope("prototype")
public class RegularExciterDaoImpl extends BaseDao implements RegularExciterDao{

	@Override
	public List<RegularExciter> findRegularExcitersByTempId(Long tempId) {
		List<RegularExciter> res = getSqlSession().selectList(
				"mybatis.RegularExciterDao.findRegularExciters" , tempId);
		return res;
	}

}


