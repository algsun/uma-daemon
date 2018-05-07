package com.microwise.uma.bean;

import com.microwise.uma.po.Regular;

public abstract class ParentRegular {
	static final int ONEWAY_TYPE = 1;
	static final int GO_TYPE = 3;
	static final int BACK_TYPE = 4;
	
	protected Long pid;
	
	/**
	 * 获取父规则的Id
	 * @return id
	 */
	public abstract Long getId();
	
	/**
	 * 添加一个子规则到父规则
	 * 
	 * @param regular
	 */
	public abstract void addSubRegular(Regular regular);
	
}
