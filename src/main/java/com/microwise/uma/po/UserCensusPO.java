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
public class UserCensusPO implements Serializable {

	private static final long serialVersionUID = 1L;

	/** id 编号 */
	private Long id;

	/** 用户id */
	private Long userId;

	/** 行为规则id 编号 */
	private Long tempId;

	/** 行为规则类型 1、单程，2、往返，3、往，4、返*/
	private Integer type;

	/** 开始时间 */
	private Long goTime;

	/** 结束时间 */
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
