package com.microwise.uma.dao;

import java.util.List;

import com.microwise.uma.po.Regular;

/**
 * 规则Dao接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:40:05
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface RegularDao {

	/**
	 * 根据type查询对应的规则
	 * 
	 * @param type
	 *            规则类型{ 1：单程 2：往返 3：往 4：返}
	 * @return
	 */
	public List<Regular> findRegularByType(int type);

	/**
	 * 根据type查询对应的规则
	 * 
	 * @return
	 */
	public List<Regular> findRegularByType();
	
	/**
	 * 根据类型和父规则ID查询子规则信息
	 * 
	 * @param parentId
	 * @param type
	 * @return 规则信息List 
	 */
	public List<Regular> findRegularByParentId(long parentId , int type);
	
	/**
     * 如果无规则，返回 0
     *
	 * @return 最大规则长度
	 */
	public int getMaxRegularLen();

}
