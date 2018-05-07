package com.microwise.uma.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.ZoneDao;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserPO;

/**
 * 区域 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-08-23
 */
@Repository
@Scope("prototype")
public class ZoneDaoImpl extends BaseDao implements ZoneDao {

    @Override
    public List<UserActionPO> findPeopleInZone(String zoneId) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("zoneId", zoneId);
        return getSqlSession().selectList("mybatis.ZoneDao.findPeopleInfZoneByZoneId", params);
    }

    @Override
    public List<UserActionPO> findZonePeoples(String zoneId, Date date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("date", date.getTime());
        return getSqlSession().selectList("mybatis.ZoneDao.findZonePeoples", params);
    }

    @Override
    public List<UserPO> findUserInfoByZoneId(String zoneId, String date) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("date", date);
        return getSqlSession().selectList("mybatis.ZoneDao.findUserInfoByZoneId", params);
    }

    @Override
    public List<String> findUserActionZones() {
        return getSqlSession().selectList("mybatis.ZoneDao.findUserActionZones");
    }
}
