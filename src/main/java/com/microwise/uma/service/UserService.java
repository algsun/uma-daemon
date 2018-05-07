package com.microwise.uma.service;

import com.microwise.uma.po.UserPO;

import java.util.List;

/**
 * 用户信息 Service ： 为对外接口服务
 *
 * @author li.jianfei
 * @date 2013-08-21
 */
public interface UserService {
    /**
     * 获取用户照片名称列表
     *
     * @return 用户照片名称列表
     */
    public List<String> getUserPhoto();

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户对象
     */
    public UserPO getUserInfo(long userId);
    
    /***
     * 查询 用户在某个区域下的 轨迹
     * 
     * @author xu.baoji
     * @date 2013-10-8
     * 
     * @param zoneId 区域
     * @param exciterSn 其实激发器
     * @param cardNO 人员卡id
     * @return 激发器轨迹
     */
    public List<String> getUserLocations(String zoneId,String exciterSn,String cardNO);
}
