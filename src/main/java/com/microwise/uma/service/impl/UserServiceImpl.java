package com.microwise.uma.service.impl;

import com.microwise.uma.dao.UserDao;
import com.microwise.uma.po.UserPO;
import com.microwise.uma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户信息 Service 实现
 *
 * @author li.jianfei
 * @date 2013-08-21
 */
@Service
@Transactional
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<String> getUserPhoto() {
        return userDao.getUserPhoto();
    }

    @Override
    public UserPO getUserInfo(long userId) {
        return userDao.getUserInfo(userId);
    }
    
    @Override
    public List<String> getUserLocations(String zoneId, String exciterSn, String cardNO) {
    	return userDao.getUserLocations(zoneId, exciterSn, cardNO);
    }
}
