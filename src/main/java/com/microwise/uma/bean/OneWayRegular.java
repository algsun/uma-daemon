package com.microwise.uma.bean;

import com.microwise.uma.po.Regular;

/**
 * 单程父规则
 * @author Administrator
 *
 */
public class OneWayRegular extends ParentRegular{

	/**
	 * 子规则 : 单程规则的子规则type统一为1
	 */
	private SubRegular subRegulars = new SubRegular(ONEWAY_TYPE) ;
	
	public OneWayRegular(Long id){
		super();
		this.pid = id;
	}
	
	@Override
	public Long getId() {
		return this.pid;
	}

	@Override
	public void addSubRegular(Regular regular) {
		subRegulars.addSubRegular(regular);
	}

	public SubRegular getSubRegulars() {
		return subRegulars;
	}

	public void setSubRegulars(SubRegular subRegulars) {
		this.subRegulars = subRegulars;
	}
	
}
