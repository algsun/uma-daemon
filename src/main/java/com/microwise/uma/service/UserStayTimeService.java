package com.microwise.uma.service;

import java.util.Date;

/**
 * @author xiedeng
 * @date 14-9-22
 */
public interface UserStayTimeService {

    /**
     * 统计区域中人员停留的时间
     */
    public void countUserStayTimeInZone(Date date);
}
