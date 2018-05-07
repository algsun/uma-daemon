package com.microwise.uma.po;

/**
 * 用户实体类
 *
 * @author li.jianfei
 * @date 2013-08-23
 */
public class UserPO {

    /** 用户ID */
    private long userId;

    /** 卡号 */
    private String cardNO;

    /** 用户名称 */
    private String userName;

    /** 用户照片 */
    private String userPhoto;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCardNO() {
        return cardNO;
    }

    public void setCardNO(String cardNO) {
        this.cardNO = cardNO;
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
}
