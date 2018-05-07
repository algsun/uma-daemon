package com.microwise.uma.bean;

import com.microwise.uma.po.Regular;

/**
 * 往返父规则
 * 
 * @author Administrator
 * 
 */
public class GoBackRegular extends ParentRegular {

	/**
	 * 子往规则
	 */
	private SubRegular goRegulars = new SubRegular(GO_TYPE);
	/**
	 * 子返规则
	 */
	private SubRegular backRegulars = new SubRegular(BACK_TYPE);

	/**
	 * 
	 * @param id
	 *            父规则Id
	 */
	public GoBackRegular(Long id) {
		super();
		this.pid = id;
	}

	@Override
	public Long getId() {
		return this.pid;
	}

	@Override
	public void addSubRegular(Regular regular) {
		if(regular != null ){
			switch(regular.getType()){
			case GO_TYPE:
				this.goRegulars.addSubRegular(regular);
				break;
			case BACK_TYPE:
				this.backRegulars.addSubRegular(regular);
				break;
			}
		}
	}

	public SubRegular getGoRegulars() {
		return goRegulars;
	}

	public SubRegular getBackRegulars() {
		return backRegulars;
	}

	public void setGoRegulars(SubRegular goRegulars) {
		this.goRegulars = goRegulars;
	}

	public void setBackRegulars(SubRegular backRegulars) {
		this.backRegulars = backRegulars;
	}
	
	

}
