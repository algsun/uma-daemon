package com.microwise.uma.po;

import java.io.Serializable;

/**   
* 实时位置对象
* @author houxiaocheng 
* @date 2013-4-24 下午2:31:47 
* 
*  @check 2013-05-06 xubaoji svn:2915
*/
public class RealtimeLocationPO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	/** id 编号 */
	private Long id;
	
	/** 人员id  */
	private Long userId; 
	
	/**设备id 激发器设备 */
	private Long deviceId;
	
	/** 记录时间 */
	private Long occurrenceTime;

	public RealtimeLocationPO() {

	}

	public RealtimeLocationPO(Long id, Long userId, Long deviceId,
			Long occurrenceTime) {
		this.id = id;
		this.userId = userId;
		this.deviceId = deviceId;
		this.occurrenceTime = occurrenceTime;
	}

	public RealtimeLocationPO(Long userId, Long deviceId, Long occurrenceTime) {
		this.userId = userId;
		this.deviceId = deviceId;
		this.occurrenceTime = occurrenceTime;
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

	public Long getOccurrenceTime() {
		return occurrenceTime;
	}

	public void setOccurrenceTime(Long occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

}
