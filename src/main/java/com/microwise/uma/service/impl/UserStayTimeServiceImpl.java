package com.microwise.uma.service.impl;

import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.dao.ZoneDao;
import com.microwise.uma.po.UserCensusPO;
import com.microwise.uma.service.UserStayTimeService;
import com.microwise.uma.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiedeng
 * @date 14-9-22
 */
@Component
public class UserStayTimeServiceImpl implements UserStayTimeService {

    private static final String PATTERN = "yyyy-MM-dd";

    @Autowired
    UserActionDao userActionDao;

    @Autowired
    ZoneDao zoneDao;

    private long startTime = 0;

    private long endTime = 0;

    private long duration = 0;

    private boolean isIgnore = true;

    @Override
    public void countUserStayTimeInZone(Date date) {
        Date currentDate = TimeUtil.convert(date, PATTERN);
        long startTime = new TimeUtil(currentDate).minusDays(1).toDate().getTime();
        long endTime = new TimeUtil(currentDate).minusSeconds(1).toDate().getTime();
        List<String> zones = zoneDao.findUserActionZones();
        for (String zoneId : zones) {
            countZoneUserAction(startTime, endTime, zoneId);
        }
    }

    private void countZoneUserAction(long startTime, long endTime, String zoneId) {
        List<UserCensusPO> userCensusList = userActionDao.findUserCensusWithoutSingleRule(startTime, endTime, zoneId);
        if (userCensusList == null || userCensusList.isEmpty()) {
            return;
        }
        this.startTime = userCensusList.get(0).getGoTime();
        Iterator<UserCensusPO> iterator = userCensusList.iterator();
        while (iterator.hasNext()) {
            UserCensusPO userCensusPO = userCensusList.get(0);
            if (userCensusList.size() == 1) {
                this.endTime = userCensusPO.getBackTime();
                break;
            }
            UserCensusPO userCensusPO2 = userCensusList.get(1);
            compareUserCensus(userCensusPO, userCensusPO2, userCensusList);
        }
        if (this.endTime > endTime) {
            this.endTime = endTime;
        }
        duration += this.endTime - this.startTime;
        Date createTime = new Date();
        createTime.setTime(startTime);
        if (userActionDao.isExistUserStayTimesRecord(zoneId, createTime)) {
            userActionDao.updateUserStayTimes(zoneId, duration, createTime);
        } else {
            userActionDao.saveUserStayTimes(zoneId, duration, createTime);
        }
        clearTimeVariable();
    }

    private void compareUserCensus(UserCensusPO userCensus, UserCensusPO userCensus2, List<UserCensusPO> userCensusList) {
        if (userCensus2.getGoTime() <= userCensus.getBackTime()) {
            if (userCensus2.getBackTime() < userCensus.getBackTime()) {
                userCensusList.remove(userCensus2);
            } else {
                this.endTime = userCensus2.getBackTime();
                userCensusList.remove(userCensus);
            }
        } else {
            endTime = userCensus.getBackTime();
            duration += endTime - startTime;
            startTime = userCensus2.getGoTime();
            userCensusList.remove(userCensus);
        }
    }

    private void clearTimeVariable() {
        this.startTime = 0;
        this.endTime = 0;
        this.duration = 0;
    }


}
