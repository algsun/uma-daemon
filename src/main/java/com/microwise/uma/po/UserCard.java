package com.microwise.uma.po;

import java.io.Serializable;

/**   
* 用户-卡关系对象
* @author houxiaocheng 
* @date 2013-4-24 下午2:30:52 
* 
* @check 2013-05-06 xubaoji svn:2915
*/
public class UserCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**id 编号*/
	private Long id;
	
	/**用户id 编号*/
	private Long userId;
	
	/**电子卡sn编号*/
	private String cardSn;

	public UserCard() {

	}

	public UserCard(Long id, Long userId, String cardSn) {
		this.id = id;
		this.userId = userId;
		this.cardSn = cardSn;
	}

	public UserCard(Long userId, String cardSn) {
		this.userId = userId;
		this.cardSn = cardSn;
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

	public String getCardSn() {
		return cardSn;
	}

	public void setCardSn(String cardSn) {
		this.cardSn = cardSn;
	}

}
