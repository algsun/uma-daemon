package com.microwise.uma.common.service;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.service.UserStayTimeService;
import com.microwise.uma.util.TimeUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;

/**
 * @author xiedeng
 * @date 2014-9-28
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserStayTimeServiceTest extends CleanDBTest {

    @Autowired
    private UserStayTimeService userStayTimeService;

    @Before
    public void before() throws Exception {
        CleanDBTest.xmlInsert2("dbxml/UserCardServiceTest.xml");
    }

    @Test
    public void testCountUserStayTimeInZone() {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = TimeUtil.parse("2012-02-20", "yyyy-MM-dd");
            endDate = new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while (startDate.before(endDate)) {
            userStayTimeService.countUserStayTimeInZone(startDate);
            startDate = new TimeUtil(startDate).addDays(1).toDate();
        }
    }

}
