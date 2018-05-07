package com.microwise.uma.dao;

import java.util.List;

import com.microwise.uma.po.RegularExciter;

/**
 * 规则顺序dao接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:40:17
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface RegularExciterDao {

	/**
	 * 根据规则ID查询具体规则设置
	 * 
	 * @param tempId
	 *            归属规则Id
	 * @return List<RegularExciter>
	 */
	public List<RegularExciter> findRegularExcitersByTempId(Long tempId);

}
