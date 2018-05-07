package com.microwise.uma.po;

import java.io.Serializable;

/**
 * 解析记录对象
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:32:16
 * 
 * @check 2013-05-06 xubaoji svn:2989
 */
public class AnalyseStatePO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**id 编号*/
	private Long id;
	
	/**用户 id 编号*/
	private Long userId;
	
	/**历史记录分析统计时间*/
	private Long locationTime;
	
	/**规则分析统计时间*/
	private Long actionTime;

	public AnalyseStatePO() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLocationTime() {
		return locationTime;
	}

	public void setLocationTime(Long locationTime) {
		this.locationTime = locationTime;
	}

	public Long getActionTime() {
		return actionTime;
	}

	public void setActionTime(Long actionTime) {
		this.actionTime = actionTime;
	}


}
