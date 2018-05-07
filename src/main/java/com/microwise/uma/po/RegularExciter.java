package com.microwise.uma.po;

import java.io.Serializable;

/**
 * 行为规则 与激发器对应关系 实体
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:31:23
 * 
 * @check 2013-05-06 xubaoji svn:2915
 */
public class RegularExciter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**id 编号*/
	private Long id; 
	
	/**规则id */
	private Long tempId; 
	
	/**设备id */
	private Long deviceId;
	
	/**激发器序号*/
	private Long sequence;

	public RegularExciter() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
}
