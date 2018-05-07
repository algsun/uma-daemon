package com.microwise.uma.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserPO;
import com.microwise.uma.service.UserService;
import com.microwise.uma.service.ZoneService;
import com.microwise.uma.util.TimeUtil;

/**
 * Http接口数据提供器
 *
 * @author li.jianfei
 * @date 2013-08-21
 */
@Component
public class ProxyDataProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private ZoneService zoneService;

    /**
     * 获取用户照片列表
     *
     * @return json
     */
    public String getUserPhoto() {
        List<String> photoList = userService.getUserPhoto();
        Gson gson = new Gson();
        return gson.toJson(photoList);
    }

    /**
     * 获取区域人员列表
     *
     * @param zoneId 区域ID
     * @return 人员列表
     */
    public String getPeopleInZone(String zoneId) {
        List<UserActionPO> userList = zoneService.findPeopleInZone(zoneId);
        Gson gson = new Gson();
        return gson.toJson(userList);
    }
    
    /**
     * 获取区域人员列表
     *
     * @param zoneId 区域ID
     * @param minute 分钟
     * @return 人员列表
     */
    public String getZonePeoples(String zoneId,int minute) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.MINUTE, -minute);
        List<UserActionPO> userList = zoneService.findZonePeoples(zoneId,calendar.getTime());
        Gson gson = new Gson();
        return gson.toJson(userList);
    }
    
    /***
     * 获得区域当天进入过的人员
     * 
     * @author xu.baoji
     * @date 2013-10-8
     *
     * @param zoneId
     * @return 
     */
    public String getUserInfo(String zoneId){
    	String date = TimeUtil.format(TimeUtil.YYYY_MM_DD, new Date());
    	List<UserPO> userList = zoneService.findUserInfoByZoneId(zoneId, date);
    	Gson gson = new Gson();
    	return gson.toJson(userList);
    }
    
    /***
     * 查询一个人员在区域下的行为轨迹
     * 
     * @author xu.baoji
     * @date 2013-10-8
     *
     * @param zoneId 区域id
     * @param exciterSn 起始位置
     * @param userId 用户id
     * @return 用户轨迹
     */
    public  String getUserLocations(String zoneId,String exciterSn,String cardNO){
    	List<String> locations = userService.getUserLocations(zoneId, exciterSn, cardNO);
    	Gson gson = new Gson();
    	return gson.toJson(locations);
    }
}
