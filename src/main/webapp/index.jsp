<%--
首页

@author gaohui
@date 2013-06-09
--%>
<%@page import="com.microwise.uma.util.GsonUtil" %>
<%@page import="com.microwise.uma.controller.PackToPoContoller" %>
<%@page import="com.microwise.uma.dao.DaoMonitor" %>
<%@page import="com.microwise.uma.quartz.PersistenceState" %>
<%@page import="com.microwise.uma.processor.CardReaderProcessor" %>
<%@page import="com.microwise.uma.bean.Request" %>
<%@page import="com.microwise.uma.controller.SystemConfigure" %>
<%@page import="java.util.concurrent.ConcurrentLinkedQueue" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Queue" %>
<%@page import="sun.java2d.pipe.AlphaPaintPipe" %>
<%@page import="com.microwise.uma.collection.handler.UmaTCPHandler" %>
<%@page import="com.microwise.uma.util.ConfigFactory" %>
<%@page import="java.util.List" %>
<%@page import="com.microwise.uma.po.DevicePO" %>
<%@page import="com.microwise.uma.service.DeviceService" %>
<%@page import="org.springframework.web.context.WebApplicationContext" %>
<%@page import="org.springframework.context.ApplicationContext" %>
<%@page import="com.microwise.uma.util.ThreadStateUtil" %>
<%@page import="com.microwise.uma.controller.AllController" %>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <title>Galaxy Uma-Daemon 人员管理后台中间件</title>
    <meta http-equiv="refresh" content="5; URL=<%= basePath%>">
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico"/>
</head>
<body>
<h1>Galaxy Uma Daemon (人员管理后台中间件)</h1>

<p style="color: #aaa;">每 5 秒刷新一次</p>

    <%ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(application); %>
    <%AllController allController = applicationContext.getBean(AllController.class);%>
<h3>系统线程状态：</h3>
<ul>
    <%for (Thread thread : allController.getThreads()) {%>
    <li><%=thread.getName()%>    <%=ThreadStateUtil.getThreadState(thread)%>
    </li>
    <%}%>
</ul>

    <%PersistenceState persistenceState = PersistenceState.getInst(); %>
<h3>统计信息：</h3>
<ul>
    <li>历史位置包入库次数：<%=persistenceState.packetWrites() %>
    </li>
    <li>历史位置包入库速度：<%=persistenceState.getPacketWritesPerSecond() %> 次/秒</li>
</ul>

<h3>tcp 服务器监听端口：</h3>
<ul>
    <% for (Integer port : SystemConfigure.portSites.keySet()) { %>
    <li><%=port %>==> <%=SystemConfigure.portSites.get(port) %>
    </li>
    <%} %>
</ul>

<h3>激发器白名单：</h3>
<ul>
    <% for (String siteId : SystemConfigure.usedExciterMap.keySet()) { %>
    <li><%=siteId %>==> <%=SystemConfigure.usedExciterMap.get(siteId) %>
    </li>
    <%} %>
</ul>

    <%UmaTCPHandler umaTCPHandler = applicationContext.getBean(UmaTCPHandler.class); %>
<h3>系统中数据队列状态：</h3>
<ul>
    <li>接收数据的字节队列：</li>
    <% Map<String, Queue<Byte>> allByteQueue = umaTCPHandler.getAllByteQueueMap(); %>
    <ul>
        <%for (String ipPort : allByteQueue.keySet()) { %>
        <li>读卡器ip:port = <%=ipPort %>的字节队列size = <%=allByteQueue.get(ipPort).size() %>
        </li>
        <%}%>
    </ul>
    <li>基本数据包队列size = <%=applicationContext.getBean(AllController.class).getBasePackQueue().size() %>
    </li>
    <% PackToPoContoller packContoller = applicationContext.getBean(PackToPoContoller.class); %>
    <li>读卡器包队列 size = <%=packContoller.getCardReaderPackQueue().size()%>
    </li>
    <li>位置信息包队列size = <%=packContoller.getLocationList().size()%>
    </li>
</ul>
    <%Map<String, Request> readerRequests = applicationContext.getBean(SystemConfigure.class).getReaderRequestMap(); %>
<h3>共有<%=readerRequests.size() %>个读卡器</h3>
<ul>
    <%for (String ipAndPort : readerRequests.keySet()) {%>
    <%Request readerRequest = readerRequests.get(ipAndPort); %>
    <%CardReaderProcessor cr = readerRequest.getCardReaderProcessor(); %>
    <li>
        <p> 读卡器ip:port=<%=readerRequest.getIpAndPort() %>,sn=<%=readerRequest.getReaderDevice().getSn() %>
            ,deviceId=<%=readerRequest.getReaderDevice().getDeviceId() %>
        </p>

        <p>读卡器线程状态：<%=cr == null ? "没有开启" : cr.getMessage() %>
        </p>
    </li>
    <%}%>
</ul>

<h3>后台http服务接口：</h3>
<span><%=basePath %>ServiceServlet?requestCode=updReg</span> <br>
<span><%=basePath %>ServiceServlet?requestCode=updUserCard</span><br>
<span><%=basePath %>ServiceServlet?requestCode=updExciter</span><br>
<span><%=basePath %>ServiceServlet?requestCode=updCardReader</span><br>
<span><%=basePath %>ServiceServlet?requestCode=getUserPhoto</span><br>
<span><%=basePath %>ServiceServlet?requestCode=getPeopleInZone&zoneId=?</span><br>
<span><%=basePath %>ServiceServlet?requestCode=getZonePeoples&zoneId=?minute=?</span><br>
<span><%=basePath %>ServiceServlet?requestCode=getUserInfo&zoneId=?</span><br>
<span><%=basePath %>ServiceServlet?requestCode=getUserLocus&exciterSn=?</span>

    <%
    DaoMonitor daoMonitor = applicationContext.getBean(DaoMonitor.class);
    daoMonitor.print();
%>

<p>启动时间: <%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date) application.getAttribute("app.startTime"))%>
</p>
<a href="CountUserStayTimes.jsp"><h3>人员停留时间统计</h3></a>
</html>