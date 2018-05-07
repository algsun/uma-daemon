package com.microwise.uma.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.microwise.uma.bean.ParentRegular;
import com.microwise.uma.bean.Request;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.po.PortSiteMapping;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.po.UserActionPO;
import com.microwise.uma.po.UserCard;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.service.RealtimeLocationService;
import com.microwise.uma.service.RegularService;
import com.microwise.uma.service.SystemService;
import com.microwise.uma.service.UserActionService;
import com.microwise.uma.service.UserCardService;
import com.microwise.uma.util.ConfigureProperties;
import com.microwise.uma.util.GsonUtil;

/**
 * 系统参数配置初始化、从数据库初始化数据
 * 
 * @author houxiaocheng
 * @date 2013-4-25 上午9:49:40
 * @check 2013-05-06 xubaoji svn:3207
 */
@Component
public class SystemConfigure {

	private static final Logger logger = LoggerFactory.getLogger(SystemConfigure.class);
	
	/** #数据包拆包线程休眠时间间隔 1秒*/
	public static int BUFFER_CHECK_PROCESSOR_SLEEPTIME = 2 * 1000;

	/**数据包分类线程休眠时间间隔*/
	public static int BUFFER_TO_BASEPACK_PROCESSOR_SLEEPTIME = 2 * 1000;

	/** 数据包解析封装线程休眠时间间隔*/
	public static int DISASSEMBLY_PROCESSOR_SLEEPTIME = 10 * 1000;

	/** 数据包入库线程休眠时间间隔*/
	public static int PERSISTENCE_PROCESSOR_SLEEPTIME = 10 * 1000;

	/** 数据包入库线程休眠时间间隔*/
	public static int REGULAR_CHECK_SLEEPTIME = 15 * 1000;

	// 是否退出. 标记线程是否可以退出
	public static volatile boolean exit = false;

	// spring 容器
	private ApplicationContext appContext;

	/** 设备service*/
	@Autowired
	private DeviceService deviceService;

	/**人卡绑定关系service*/
	@Autowired
	private UserCardService userCardService;

	/** 实时位置service*/
	@Autowired
	private RealtimeLocationService realtimeService;

	/** 行为规则service*/
	@Autowired
	private RegularService regularService;

	/** 用户行为service*/
	@Autowired
	private UserActionService userActionService;
	
	/**系统 service*/
	@Autowired
	private SystemService systemService;

	/** 读卡器 自定义request 存储 map key:读卡器Ip:port value：读卡器链接Request*/
	private Map<String, Request> readerRequestMap = new ConcurrentHashMap<String, Request>();

	/*** 获得数据库中所有读卡器信息 按key 为 ip:port 方式存储 */
	private Map<String, DevicePO> cardReaders = new HashMap<String, DevicePO>();

	/** 实时位置信息表（记录库中的所有记录，避免更新、插入的多次判断）(用来判断数据位置是否为重复点) */
	private Map<Long, RealtimeLocationPO> realtimeLocation = new HashMap<Long, RealtimeLocationPO>();

	/** 人卡绑定记录*/
	private Map<String, Long> userCardRelations = new ConcurrentHashMap<String, Long>();

	/** 所有的设备记录 1-读卡器 2-电子卡+激发器*/
	private Map<String, DevicePO> allDevices1 = new HashMap<String, DevicePO>();
	private Map<String, DevicePO> allDevices2 = new HashMap<String, DevicePO>();

	/** 实时位置信息表（记录库中的所有记录，避免更新、插入的多次判断）(用来判断数据位置是否为重复点)*/
	public static Map<Long, RealtimeLocationPO> realtimeLocation_F = new ConcurrentHashMap<Long, RealtimeLocationPO>();
	
	/** 人卡绑定记录*/
	public static Map<String, Long> userCardRelations_F = new ConcurrentHashMap<String, Long>();

	/** 人员集合 */
	public static List<Long> users_F = Collections.synchronizedList(new ArrayList<Long>());

	/** 所有的设备记录 1-读卡器 2-电子卡+激发器 */
	public static Map<String, DevicePO> allDevices1_F = new ConcurrentHashMap<String, DevicePO>();
	public static Map<String, DevicePO> allDevices2_F = new ConcurrentHashMap<String, DevicePO>();

	/** 电子卡，激发器sn set 集合，用来判断设备是否重复问题 */
	public static Set<String> deviceSnSet = new LinkedHashSet<String>();

	/** 所有的规则:最高级别的规则*/
	public static final Map<Long, ParentRegular> regulars = new ConcurrentHashMap<Long, ParentRegular>();
	
	/** 规则最大长度*/
	public static int MaxRegLen = 0;

	/** 用户最新的行为记录Map */
	// 该属性在 规则匹配 和 提取往返规则 线程中都会用到 将非线程安全的map 该为使用线程安全的map
	public static final Map<Long, UserActionPO> userLastActionMap = new ConcurrentHashMap<Long, UserActionPO>();

	/** 系统初始化参数 -- 绑定端口，更新限制标识 */
	public static int UPDATE_FLAG = 2;
	public static int BUFFER_SIZE = 4096; // 单位 字节 byte

	/**服务 监听端口 和 站点对应关系 key  port  value  siteId */
	public static Map<Integer, String> portSites = new HashMap<Integer, String>();

	/** 激发器白名单  key siteId  value  list<Stirng> 该站点下激发器白名单**/
	public static Map<String, List<String>> usedExciterMap = new HashMap<String, List<String>>();
	
	/**
	 * 把实时位置对象信息转换成map对象，方便取值判断
	 * 
	 * @param locs
	 *            实时位置map 对象 key：用户id value：实时位置对象
	 */
	public synchronized static void updateRealtimeLocation(Map<Long, RealtimeLocationPO> locs) {
		Iterator<Long> it = locs.keySet().iterator();
		while (it.hasNext()) {
			Long userId = it.next();
			realtimeLocation_F.put(userId, locs.get(userId));
		}
	}

	/**
	 * 新增一条实时位置 如果存在 会被 替换
	 * 
	 * @param userId
	 * @param deviceId
	 */
	public static void updateRealtimeLocation(Long userId, RealtimeLocationPO realtimeLocation) {
		realtimeLocation_F.put(userId, realtimeLocation);
	}

	/** 从数据库中初始化最新位置点 */
	public synchronized void reloadRealtimeLocation() {
		realtimeLocation = new HashMap<Long, RealtimeLocationPO>();
		List<RealtimeLocationPO> reList = realtimeService.findAll();
		if (reList != null) {
			for (RealtimeLocationPO realtime : reList) {
				realtimeLocation.put(realtime.getUserId(), realtime);
			}
			updateRealtimeLocation(realtimeLocation);
		}
	}

	/**
	 * 从数据库中初始化人卡绑定关系
	 */
	public void reloadUserCardRelations() {
		logger.info("----数据库 人卡绑定 信息初始化-----");
		synchronized (users_F) {
			userCardRelations = new HashMap<String, Long>();
			List<UserCard> userCards = userCardService.findAllRelations();
			if (userCards != null) {
				for (UserCard userCard : userCards) {
					userCardRelations.put(userCard.getCardSn(), userCard.getUserId());
					users_F.add(userCard.getUserId()); // 初始化所有的用户（绑定了卡的用户）
					logger.info("userID ：" + userCard.getUserId() + "--> sn:"
							+ userCard.getCardSn());
				}
			}
			userCardRelations_F.clear();
			userCardRelations_F.putAll(userCardRelations);
		}
		logger.info("----数据库 人卡绑定 信息初始化结束----共找到：" + userCardRelations.size() + " 个绑定信息！");
	}

	/** 获得 有电子卡的用户列表 */
	public List<Long> getUsers() {
		synchronized (users_F) {
			List<Long> users = new ArrayList<Long>();
			users.addAll(users_F);
			return users;
		}
	}

	/**
	 * 从数据库中初始化所有电子卡和激发器设备
	 */
	public synchronized void reloadAllDevices() {
		// reloadCardReader(); 不需要载入读卡器
		reloadExciterAndCards();
	}

	/**
	 * 从数据库载入读卡器
	 */
	public synchronized void reloadCardReader() {
		synchronized (allDevices1) {
			allDevices1 = new HashMap<String, DevicePO>();
			List<DevicePO> devices = deviceService.findAllCardReaders();
			for (DevicePO device : devices) {
				allDevices1.put(device.getSn(), device);
				logger.info("设备类型：" + device.getType() + " ; 设备ID：" + device.getDeviceId());
			}
		}
		synchronized (allDevices1_F) {
			allDevices1_F.clear();
			allDevices1_F.putAll(allDevices1);
		}
	}

	/**
	 * 从数据库载入电子卡和激发器
	 */
	public synchronized void reloadExciterAndCards() {
		synchronized (allDevices2) {
			allDevices2 = new HashMap<String, DevicePO>();
			List<DevicePO> devices = deviceService.findAllCardAndExciterDevices();
			for (DevicePO device : devices) {
				deviceSnSet.add(device.getSn());
				allDevices2.put(device.getSn(), device);
				logger.debug("设备类型：" + device.getType() + " ; 设备ＳＮ：" + device.getSn());
			}
		}
		allDevices2_F.clear();
		allDevices2_F.putAll(allDevices2);
	}

	/** 规则重载 */
	public void reloadRegulars() {
		logger.info("----规则重载----");
		List<ParentRegular> regs = regularService.findAllRegulars();
		for (ParentRegular reg : regs) {
			regulars.put(reg.getId(), reg);
			logger.info("父规则ID： " + reg.getId());
		}
		MaxRegLen = regularService.getMaxRegularLength();
		logger.info("----规则重载结束---共有父规则-" + regulars.size() + " ; 个。规则最大长度为 ：" + MaxRegLen);
	}

	/**
	 * 获取参数设置，如果参数配置文件中该参数设置有误，则返回默认值
	 */
	public static int getConfigure(String propertiesKey, int defaultValue) {
		try {
			return Integer.parseInt(ConfigureProperties.getProperty(propertiesKey, defaultValue
					+ ""));
		} catch (Exception e) {
			return defaultValue;
		}

	}

	/**
	 * 获取参数设置，如果参数配置文件中该参数设置有误，则返回默认值
	 */
	public static long getConfigure(String propertiesKey, long defaultValue) {
		try {
			return Long
					.parseLong(ConfigureProperties.getProperty(propertiesKey, defaultValue + ""));
		} catch (Exception e) {
			return defaultValue;
		}

	}

	/**
	 * 获取参数设置，如果参数配置文件中该参数设置有误，则返回默认值
	 */
	public static String getConfigure(String propertiesKey, String defaultValue) {
		try {
			return ConfigureProperties.getProperty(propertiesKey, defaultValue + "");
		} catch (Exception e) {
			return defaultValue;
		}

	}

	/**
	 * 系统初始化--从数据库中获取所需信息
	 */
	public synchronized void init() {
		logger.info("----数据库信息初始化-----");
		reloadRealtimeLocation();// 实时位置
		reloadUserCardRelations();// 绑定关系
		reloadAllDevices();// 设备
		reloadRegulars(); // 规则
		loadUserLastActions();// 最新行为
		loadConfigures();
		reloadCardReaders();// 加载 读卡器
		reloadPortSiteMapping();// 加载 系统 监听端口对应站点
		logger.info("系统参数初始化完毕");
	}

	/*** 加载数据库中读卡器 */
	public void reloadCardReaders() {
		List<DevicePO> cardReaders = deviceService.findAllCardReaders();
		for (DevicePO device : cardReaders) {
			String ipAndPort = device.getIp() + ":" + device.getPort();
			this.cardReaders.put(ipAndPort, device);
			// 将 读卡器 sn 存放 进用来判断 设备重复的set 列表 中
			deviceSnSet.add(device.getSn());
			logger.info("加载到读卡器器 ip:port=" + ipAndPort);
		}
	}
	
	/**加载当前 系统站点id*/
	private void reloadPortSiteMapping(){
		List<PortSiteMapping> portSites = systemService.findPortSiteMappings();
		if (portSites == null || portSites.isEmpty()) {
			logger.error("数据库没有配置服务监听端口和站点对应关系");
		}else {
			for (PortSiteMapping portSiteMapping : portSites) {
				SystemConfigure.portSites.put(portSiteMapping.getPort(), portSiteMapping.getSiteId());
				logger.info("服务监听端口 ==> 站点 "+portSiteMapping.getPort()+"==>" +portSiteMapping.getSiteId());
				// 加载 站点下激发器 白名单
				reloadUsedExciterBySiteId(portSiteMapping.getSiteId());
			}
		}
	}
	
	/**加载激发器白名单*/
	private  void reloadUsedExciterBySiteId(String siteId){
		List<String> usedExciters = systemService.findUsedExciterSn(siteId);
		if (usedExciters.isEmpty()) {
			logger.error("请配置激发器白名单siteId = " +siteId);
		}else{
			logger.info("站点siteId = "+siteId+" 下激发器白名单："+GsonUtil.objectToJson(usedExciters));
		}
		usedExciterMap.put(siteId, usedExciters);
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	public Map<String, Request> getSessionMap() {
		return readerRequestMap;
	}

	/***
	 * 获得 读卡器 自定义 request 对象,如果存在 返回，不存在 创建 并放进 session map
	 * 
	 * @author xu.baoji
	 * @date 2013-9-25
	 * 
	 * @param ipAndPort
	 * @return
	 */
	public Request getReaderRequest(String ipAndPort) {
		Request request = readerRequestMap.get(ipAndPort);
		if (request == null) {
			request = new Request();
			readerRequestMap.put(ipAndPort, request);
		}
		return request;
	}
	
	/***
	 * 删除读卡器 自定义 request 
	 * 
	 * @author xu.baoji
	 * @date 2013-10-14
	 *
	 * @param ipAndPort
	 */
	public void removeReaderRequest(String ipAndPort){
		readerRequestMap.remove(ipAndPort);
	}

	/**
	 * 从数据库中加载用户最新的行为
	 */
	public synchronized void loadUserLastActions() {
		List<UserActionPO> userActions = new ArrayList<UserActionPO>();
		userActions = userActionService.getUserLastAction(null);
		for (UserActionPO action : userActions) {
			userLastActionMap.put(action.getUserId(), action);
		}
	}

	/**
	 * 从配置文件获取设置的参数值 更新:绑定端口号，系统缓存大小，更新间隔时间 TODO 获取配置的方式感觉很绕
	 */
	private synchronized void loadConfigures() {
		UPDATE_FLAG = getConfigure("maxUpdateFlag", UPDATE_FLAG);
		BUFFER_SIZE = getConfigure("buffersize", BUFFER_SIZE);
	}

	public Map<String, DevicePO> getCardReaders() {
		return cardReaders;
	}

	public Map<String, Request> getReaderRequestMap() {
		return readerRequestMap;
	}
}
