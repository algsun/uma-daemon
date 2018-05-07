package com.microwise.uma.dao;

import java.util.Date;
import java.util.List;

import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserPO;

/**
 * 区域 Dao
 *
 * @author li.jianfei
 * @date 2013-08-23
 */
public interface ZoneDao {

    /**
     * 查询当前区域人员
     *
     * @param zoneId 区域ID
     * @return 区域人员列表
     */
    public List<UserActionPO> findPeopleInZone(String zoneId);
    
    /***
     * 查询一个时间点 之后的 区域下人员
     * 
     * @author xu.baoji
     * @date 2013-9-25
     *
     * @param zoneId 区域 id 
     * @param date 日期
     * @return
     */
    public List<UserActionPO> findZonePeoples(String  zoneId,Date date);
    
    /***
     * 查询 某天进入过区域的人员
     * 
     * @author xu.baoji
     * @date 2013-10-8
     *
     * @param zoneId 区域id
     * @param date yyyy-mm-dd 格式日期
     * @return       人员列表
     */
    public List<UserPO> findUserInfoByZoneId(String zoneId,String date);

    /**
     * 获取用户活动的区域编号
     * @return
     */
    public List<String> findUserActionZones();
}
