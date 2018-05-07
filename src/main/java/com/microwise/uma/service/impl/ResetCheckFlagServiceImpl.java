package com.microwise.uma.service.impl;

import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.dao.UserActionDao;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.service.ResetCheckFlagService;
import com.microwise.uma.util.ConfigureProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiedeng
 * @date 14-8-21
 */
@Component
public class ResetCheckFlagServiceImpl implements ResetCheckFlagService {

    private static final Logger logger = LoggerFactory.getLogger(ResetCheckFlagServiceImpl.class);

    @Autowired
    private UserActionDao userActionDao;

    /**
     * 往行为超时时间
     */
    private static long actionTimeOut = 24;

    @Override
    public void resetCheckFlag() {
        actionTimeOut = SystemConfigure.getConfigure(ConfigureProperties.GO_ACTION_TIME_OUT, actionTimeOut);
        actionTimeOut = actionTimeOut * 60 * 60 * 1000;
        List<UserActionPO> userActions = userActionDao.findUserActionsByCheckFlag(UserActionPO.CHECK_FLAG_NOPAIR);
        for (UserActionPO userAction : userActions) {
            long userTimes = userAction.getOcctime();
            if ((System.currentTimeMillis() - userTimes) > actionTimeOut) {
                userAction.setCheckFlag(UserActionPO.CHECK_FLAG_ERRPAIR);
                userActionDao.updateActionFlag(userAction);
            }
        }
    }
}
