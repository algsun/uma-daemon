package com.microwise.uma.po;

import java.io.Serializable;

/**
 * 历史位置对象
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:32:02
 * 
 * @check 2013-05-06 xubaoji svn:2915
 */
public class LocationPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id 编号 */
	private Long id;
	
	/** 人员id  */
	private Long userId; 
	
	/**设备id 激发器设备 */
	private Long deviceId;
	
	/** 记录时间 */
	private Long currentTime;

	public LocationPO() {

	}

	public LocationPO(Long userId, Long deviceId, Long currentTime) {
		this.userId = userId;
		this.deviceId = deviceId;
		this.currentTime = currentTime;
	}

	public LocationPO(Long id, Long userId, Long deviceId, Long currentTime) {
		this.id = id;
		this.userId = userId;
		this.deviceId = deviceId;
		this.currentTime = currentTime;
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

	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	public Long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Long currentTime) {
		this.currentTime = currentTime;
	}

}
