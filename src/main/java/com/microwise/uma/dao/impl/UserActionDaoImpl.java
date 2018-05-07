package com.microwise.uma.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microwise.uma.po.UserCensusPO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.po.CircuitedActionPO;
import com.microwise.uma.po.UserActionPO;

/**
 * 用户行为dao接口的实现
 *
 * @author houxiaocheng
 * @date 2013-4-24 下午3:06:17
 * @check 2013-05-07 xubaoji svn:2954
 */
@Repository
@Scope("prototype")
public class UserActionDaoImpl extends BaseDao implements UserActionDao {

    @Override
    public void saveUserAction(List<UserActionPO> userActions) {
        getSqlSession().insert("mybatis.UserActionDao.saveActionsBatch",
                userActions);
    }

    @Override
    public List<UserActionPO> findActionsAfterLastTime(Long userId, Long times) {
        Map<String, Long> params = new HashMap<String, Long>();
        params.put("userId", userId);
        params.put("occtime", times);
        return getSqlSession().selectList(
                "mybatis.UserActionDao.findUserActions", params);
    }

    @Override
    public List<UserActionPO> findUserLastAction(Long times) {
        return getSqlSession().selectList(
                "mybatis.UserActionDao.findUserLastActions", times);
    }

    @Override
    public void updateActionFlag(UserActionPO userAction) {
        getSqlSession().update("mybatis.UserActionDao.updateActionFlag", userAction);
    }

    @Override
    public void saveCircuitedAction(List<CircuitedActionPO> userActions) {
        getSqlSession().insert("mybatis.UserActionDao.saveCircuitedActionsBatch",
                userActions);
    }

    @Override
    public List<UserActionPO> findUserActionsByCheckFlag(int checkFlag) {
        return getSqlSession().selectList("mybatis.UserActionDao.findUserActionsByCheckFlag", checkFlag);
    }

    @Override
    public List<UserCensusPO> findUserCensusWithoutSingleRule(long startTime, long endTime, String zoneId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("zoneId", zoneId);
        return getSqlSession().selectList("mybatis.UserActionDao.findUserCensusWithoutSingleRule", params);
    }

    @Override
    public void saveUserStayTimes(String zoneId, long stayTimes, Date createTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("stayTimes", stayTimes);
        params.put("createTime", createTime);
        getSqlSession().insert("mybatis.UserActionDao.saveUserStayTimes", params);
    }

    @Override
    public void updateUserStayTimes(String zoneId, long stayTimes, Date createTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("stayTimes", stayTimes);
        params.put("createTime", createTime);
        getSqlSession().insert("mybatis.UserActionDao.updateUserStayTimes", params);
    }

    @Override
    public boolean isExistUserStayTimesRecord(String zoneId, Date createTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("zoneId", zoneId);
        params.put("createTime", createTime);
        Integer count = (Integer) getSqlSession().selectOne("mybatis.UserActionDao.isExistUserStayTimesRecord", params);
        return count > 0;
    }

}
