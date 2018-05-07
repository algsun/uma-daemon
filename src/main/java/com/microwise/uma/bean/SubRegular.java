package com.microwise.uma.bean;

import java.util.ArrayList;
import java.util.List;

import com.microwise.uma.po.Regular;

/**
 * 次高级别的规则信息：即父规则下一级别规则信息
 * 
 * @author Administrator
 * 
 */
public class SubRegular {

	
	/**
	 * 类型
	 */
	private int type ;

	/**
	 * 子规则信息
	 */
	private List<Regular> subRegs = new ArrayList<Regular>();

	public SubRegular() {
	}
	
	public SubRegular(int type ) {
		this.type = type;
	}
	
	
	public SubRegular(int type, List<Regular> subRegs) {
		this.type = type;
		this.subRegs = subRegs;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Regular> getSubRegs() {
		return subRegs;
	}

	public void setSubRegs(List<Regular> subRegs) {
		this.subRegs = subRegs;
	}

	public void addSubRegular(Regular subReg){
		this.subRegs.add(subReg);
	}
	
}
