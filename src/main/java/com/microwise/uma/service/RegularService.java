package com.microwise.uma.service;

import java.util.List;

import com.microwise.uma.bean.ParentRegular;
import com.microwise.uma.po.AnalyseStatePO;
import com.microwise.uma.po.Regular;

/**
 * 规则service接口
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:18:45
 * 
 * @check 2013-05-07 xubaoji svn:2915
 */
public interface RegularService {

	/**
	 * 获取所有的规则
	 * 
	 * @return List<Regular>
	 */
	public List<Regular> findRegulars();

	/**
	 * 获取所有的单程规则
	 * 
	 * @return List<Regular>
	 */
	public List<Regular> findOneDirectionRegulars();

	/**
	 * 获取所有的单向子规则（包含规则下面的具体RegularExciter设置）
	 * 
	 * @return List<Regular>
	 */
	public List<Regular> findOneDirectionwholeRegInfo();

	/**
	 * 获取解析状态
	 * 
	 * @return List<Regular>
	 */
	public List<AnalyseStatePO> findAnalySeState();
	
	
	/**
	 * 获得所有的父规则：包含子规则信息
	 * 
	 * @return List<ParentRugular>
	 */
	public List<ParentRegular> findAllRegulars();
	
	/**
	 * @return 规则最大长度
	 */
	public int getMaxRegularLength();
}
