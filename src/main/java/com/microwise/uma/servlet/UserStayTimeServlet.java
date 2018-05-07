package com.microwise.uma.servlet;

import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.proxy.CollectionProxy;
import com.microwise.uma.service.UserStayTimeService;
import com.microwise.uma.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

/**
 * Http接口服务Servlet
 *
 * @author houxiaocheng
 * @date 2013-4-22 下午1:17:46
 * @check 2013-05-07 xubaoji svn:3168
 */
public class UserStayTimeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
            .getLogger(UserStayTimeServlet.class);

    private UserStayTimeService userStayTimeService;

    @Override
    public void init() throws ServletException {
        super.init();
        // 负责系统初始化{包括后台数据接收、解析入库线程的初始化及启动}
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        userStayTimeService = appContext.getBean(UserStayTimeService.class);
    }

    public UserStayTimeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String startDateStr = request.getParameter("startDate").toString();
        String endDateStr = request.getParameter("endDate").toString();
        Date startDate = null;
        Date endDate = null;
        boolean result = true;
        String message = "统计成功";
        try {
            startDate = TimeUtil.parse(startDateStr, "yyyy-MM-dd");
            endDate = TimeUtil.parse(endDateStr, "yyyy-MM-dd");
            while (startDate.before(endDate)) {
                userStayTimeService.countUserStayTimeInZone(startDate);
                startDate = new TimeUtil(startDate).addDays(1).toDate();
            }
        } catch (Exception e) {
            logger.error("调用人员停留时间统计接口出错", e);
            message = "统计失败";
            result = false;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(
                "{\"success\":" + result + ",\"message\":\"" + message + "\"}");
    }
}
