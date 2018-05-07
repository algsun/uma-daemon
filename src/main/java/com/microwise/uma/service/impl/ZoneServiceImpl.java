package com.microwise.uma.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.ZoneDao;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserPO;
import com.microwise.uma.service.ZoneService;

/**
 * 区域 Service 实现
 *
 * @author li.jianfei
 * @date 2013-08-23
 */
@Service
@Transactional
@Scope("prototype")
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneDao zoneDao;

    @Override
    public List<UserActionPO> findPeopleInZone(String zoneId) {
        return zoneDao.findPeopleInZone(zoneId);
    }
    
    @Override
    public List<UserActionPO> findZonePeoples(String zoneId, Date date) {
    	return zoneDao.findZonePeoples(zoneId,date);
    }
    
    @Override
    public List<UserPO> findUserInfoByZoneId(String zoneId, String date) {
    	return zoneDao.findUserInfoByZoneId(zoneId,date);
    }

}
