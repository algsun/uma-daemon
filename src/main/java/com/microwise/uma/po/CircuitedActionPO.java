package com.microwise.uma.po;

import java.io.Serializable;

public class CircuitedActionPO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 往返规则父规则Id
	 */
	private Long actionTempletId;
	/**
	 * 往规则Id
	 */
	private Long goActionTempId;
	/**
	 * 返归则Id
	 */
	private Long backActionTempId;
	/**
	 * 往规则触发时间
	 */
	private Long goTime;
	/**
	 * 返归则触发时间
	 */
	private Long backTime;

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

	public Long getActionTempletId() {
		return actionTempletId;
	}

	public void setActionTempletId(Long actionTempletId) {
		this.actionTempletId = actionTempletId;
	}

	public Long getGoActionTempId() {
		return goActionTempId;
	}

	public void setGoActionTempId(Long goActionTempId) {
		this.goActionTempId = goActionTempId;
	}

	public Long getBackActionTempId() {
		return backActionTempId;
	}

	public void setBackActionTempId(Long backActionTempId) {
		this.backActionTempId = backActionTempId;
	}

	public Long getGoTime() {
		return goTime;
	}

	public void setGoTime(Long goTime) {
		this.goTime = goTime;
	}

	public Long getBackTime() {
		return backTime;
	}

	public void setBackTime(Long backTime) {
		this.backTime = backTime;
	}

}
