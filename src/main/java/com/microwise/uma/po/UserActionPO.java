package com.microwise.uma.po;

import java.io.Serializable;

/**
 * 用户行为对象
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:31:10
 * 
 * @check 2013-05-06 xubaoji svn:2929
 */
public class UserActionPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 未匹配
	 */
	public static final int CHECK_FLAG_NOPAIR = 0;
	/**
	 * 正常匹配
	 */
	public static final int CHECK_FLAG_PAIR = 1;
	/**
	 * 异常匹配：未找到对应的往或者返规则 ,但是因为超时或者逻辑关系而需要修改为已匹配状态
	 */
	public static final int CHECK_FLAG_ERRPAIR = 2;

	/** id 编号 */
	private Long id;

	/** 用户id */
	private Long userId;

	/** 用户姓名 */
	private String userName;

	/** 用户照片 */
	private String userPhoto;

	/** 卡号 */
	private String sn;

	/** 行为规则id 编号 */
	private Long tempId;

	/** 行为规则名称 */
	private String actionName;

	/** 行为规则类型 */
	private Integer type;

	/** 行为发生时间 */
	private Long occtime;

	/** 开始时间 */
	private Long startTime;

	/** 结束时间 */
	private Long endTime;

	/** 父规则Id */
	private Long parentActionTempletId;

	/** 区域ID */
	private String zoneId;

	/**
	 * 往返规则匹配标识
	 */
	private Integer checkFlag;

	public UserActionPO() {

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getOcctime() {
		return occtime;
	}

	public void setOcctime(Long occtime) {
		this.occtime = occtime;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Long getParentActionTempletId() {
		return parentActionTempletId;
	}

	public void setParentActionTempletId(Long parentActionTempletId) {
		this.parentActionTempletId = parentActionTempletId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	/**
	 * 判断两个行为是否重复
	 * 
	 * @param po
	 * @return false / true
	 */
	public boolean isEqual(UserActionPO po) {
		if (po != null && po.getParentActionTempletId() != null
				&& this.parentActionTempletId != null && this.type != null && po.getType() != null) {
			if (this.getParentActionTempletId().longValue() == po.getParentActionTempletId()
					.longValue() && this.getType().intValue() == po.getType().intValue()) {
				return true;
			}
		}
		return false;
	}

	public Integer getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(Integer checkFlag) {
		this.checkFlag = checkFlag;
	}

}
