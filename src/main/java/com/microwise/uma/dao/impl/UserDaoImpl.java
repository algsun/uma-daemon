package com.microwise.uma.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.UserDao;
import com.microwise.uma.po.UserPO;

/**
 * 用户信息 Dao 实现
 *
 * @author li.jianfei
 * @date 2013-08-21
 */
@Repository
@Scope("prototype")
public class UserDaoImpl extends BaseDao implements UserDao {

    @Override
    public List<String> getUserPhoto() {
        return getSqlSession().selectList("mybatis.UserInfoDao.getUserPhoto");
    }

    @Override
    public UserPO getUserInfo(long userId) {
        return getSqlSession().selectOne("mybatis.UserInfoDao.getUserInfo",userId);
    }
    
    @Override
    public List<String> getUserLocations(String zoneId,	String exciterSn, String cardNO) {
    	Map<String, Object> paramMap= new HashMap<String, Object>();
    	paramMap.put("zoneId", zoneId);
    	paramMap.put("exciterSn", exciterSn);
    	paramMap.put("cardNO", cardNO);
    	return getSqlSession().selectList("mybatis.UserInfoDao.getUserLocations",paramMap);
    }
}
