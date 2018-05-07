package com.microwise.uma.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.proxy.CollectionProxy;

/**
 * Http接口服务Servlet
 *
 * @author houxiaocheng
 * @date 2013-4-22 下午1:17:46
 * @check 2013-05-07 xubaoji svn:3168
 */
public class ServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory
            .getLogger(ServiceServlet.class);

    /**
     * 后台服务启动代理接口
     */
    private CollectionProxy collectionProxy;

    @Override
    public void init() throws ServletException {
        super.init();
        // 负责系统初始化{包括后台数据接收、解析入库线程的初始化及启动}
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        collectionProxy = appContext.getBean(CollectionProxy.class);
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String requestCode = request.getParameter("requestCode");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        System.out.println("requestCode : " + requestCode);
        boolean res = true;
        String message = "";
        String resultData = "";
        requestCode = requestCode == null ? null : requestCode.toUpperCase();
        if ("updReg".toUpperCase().equals(requestCode)) {
            res = updateRegular(message);
        } else if ("updUserCard".toUpperCase().equals(requestCode)) {
            res = updateUserCardRelations(message);
        } else if ("updExciter".toUpperCase().equals(requestCode)) {
            res = updateExciterDevices(message);
        } else if ("updCardReader".toUpperCase().equals(requestCode)) {
            res = updateCardReaderDevices(message);
        } else if ("getUserPhoto".toUpperCase().equals(requestCode)) {
            res = true;
            resultData = getUserPhoto();
            if (resultData == null) {
                res = false;
                message = "获取人员照片失败";
            }
            response.getWriter().write(
                    "{\"success\":" + res + ",\"message\":\"" + message + "\"" + ",\"resultData\":" + resultData + "}");
            return;
        } else if ("getPeopleInZone".toUpperCase().equals(requestCode)) {
            res = true;
            resultData = getPeopleInZone(request.getParameter("zoneId"));
            if (resultData == null) {
                res = false;
                message = "获取区域人员失败";
            }
            response.getWriter().write(
                    "{\"success\":" + res + ",\"message\":\"" + message + "\"" + ",\"resultData\":" + resultData + "}");
            return;
        } else if ("getZonePeoples".toUpperCase().equals(requestCode)){
        	res = true;
        	String zoneId = request.getParameter("zoneId");
        	String minute = request.getParameter("minute");
        	resultData = getZonePeoples(zoneId,minute);
        	if (resultData == null) {
				res = false;
				message = "获取区域下一段时间内活动人员失败";
			}
            response.getWriter().write(
                    "{\"success\":" + res + ",\"message\":\"" + message + "\"" + ",\"resultData\":" + resultData + "}");
            return;
		} else if ("getUserInfo".toUpperCase().equals(requestCode)){
        	res = true;
        	String zoneId = request.getParameter("zoneId").trim();
        	resultData = getUserInfo(zoneId);  
        	if (resultData == null) {
				res = false;
				message = "获取区域下当天进入人员失败";
			}
            response.getWriter().write(
                    "{\"success\":" + res + ",\"message\":\"" + message + "\"" + ",\"resultData\":" + resultData + "}");
            return;
		}else if ("getUserLocus".toUpperCase().equals(requestCode)){
        	res = true;
        	String zoneId = request.getParameter("zoneId").trim();
        	String exciterSn = request.getParameter("exciterSn").trim();
        	String cardNO = request.getParameter("cardNO").trim();
        	resultData = getUserLocations(zoneId, exciterSn, cardNO);  
        	if (resultData == null) {
				res = false;
				message = "获取人员在区域活动轨迹失败";
			}
            response.getWriter().write(
                    "{\"success\":" + res + ",\"message\":\"" + message + "\"" + ",\"resultData\":" + resultData + "}");
            return;
		}else if ("view".toUpperCase().equals(requestCode)) {
            String param = request.getParameter("name");
            if (param == null || param.trim().length() == 0) {
                res = false;
                message = "未知请求";
            } else if ("usercard".toUpperCase().equals(param.toUpperCase())) {
                response.getWriter().write(showUserCardListInfo());
                return;
            }
        } else {
            res = false;
            message = "未知请求";
        }
        response.getWriter().write(
                "{\"success\":" + res + ",\"message\":\"" + message + "\"}");
    }

    /**
     * 更新规则处理
     */
    private boolean updateRegular(String message) {
        try {
            collectionProxy.getSystemConfigure().reloadRegulars();
        } catch (Exception e) {
            message = "规则刷新失败！";
            logger.error("",e);
            return false;
        }
        return true;
    }

    /**
     * 重新加载人卡绑定关系
     */
    private boolean updateUserCardRelations(String message) {
        try {
            collectionProxy.getSystemConfigure().reloadUserCardRelations();
        } catch (Exception e) {
            message = "人卡绑定关系刷新失败！";
            logger.error("",e);
            return false;
        }
        return true;
    }

    /**
     * 停用/启用激发器设备
     */
    private boolean updateExciterDevices(String message) {
        try {
            collectionProxy.getSystemConfigure().reloadExciterAndCards();
        } catch (Exception e) {
            message = "激发器状态修改失败！";
            logger.error("",e);
            return false;
        }
        return true;
    }

    /**
     * 更新读卡器
     *
     * @author houxiaocheng
     * @date 2013-4-22 下午1:17:46
     */
    private boolean updateCardReaderDevices(String message) {
        try {
            collectionProxy.getSystemConfigure().reloadCardReader();
        } catch (Exception e) {
            message = "读卡器更新失败！";
            logger.error("",e);
            return false;
        }
        return true;
    }

    /**
     * 获取人员照片列表
     *
     * @return 是否成功
     */
    private String getUserPhoto() {
        String jsonString;
        try {
            jsonString = collectionProxy.getProxyDataProvider().getUserPhoto();
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
        return jsonString;
    }

    /**
     * 获取区域人员列表
     *
     * @param zoneId 区域ID
     * @return 人员列表 Json
     */
    private String getPeopleInZone(String zoneId) {
        String jsonString;
        try {
            jsonString = collectionProxy.getProxyDataProvider().getPeopleInZone(zoneId);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
        return jsonString;
    }
    
    /**
     * 获取区域人员列表
     *
     * @param zoneId 区域ID
     * @return 人员列表 Json
     */
    private String getZonePeoples(String zoneId,String minute) {
        String jsonString;
        try {
        	int minutes = Integer.parseInt(minute);
            jsonString = collectionProxy.getProxyDataProvider().getZonePeoples(zoneId, minutes);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
        return jsonString;
    }
    
    /***
     * 获得 区域当天进入过的人员
     * 
     * @param zoneId
     * @return 区域当天进入过的人员json
     */
    private String getUserInfo(String zoneId){
        String jsonString;
        try {
            jsonString = collectionProxy.getProxyDataProvider().getUserInfo(zoneId);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
        return jsonString;
    }
    
    /***
     * 查询一个人员在区域下的行为轨迹
     * 
     * @author xu.baoji
     * @date 2013-10-8
     *
     * @param zoneId 区域id
     * @param exciterSn 起始位置
     * @param userId 用户id
     * @return 用户轨迹
     */
    private String getUserLocations(String zoneId,String exciterSn,String cardNO){
        String jsonString;
        try {
            jsonString = collectionProxy.getProxyDataProvider().getUserLocations(zoneId, exciterSn, cardNO);
        } catch (Exception e) {
            logger.error("",e);
            return null;
        }
        return jsonString;
    }
    

    private String showUserCardListInfo() {
        Iterator<String> its = SystemConfigure.userCardRelations_F.keySet()
                .iterator();
        StringBuffer show = new StringBuffer();
        while (its.hasNext()) {
            String key = its.next();
            show.append("<br>");
            show.append("key:");
            show.append(key);
            show.append("   , value :");
            show.append(SystemConfigure.userCardRelations_F.get(key));
            show.append("</br>");
        }

        return show.toString();
    }
}
