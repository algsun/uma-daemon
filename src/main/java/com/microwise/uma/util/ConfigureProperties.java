package com.microwise.uma.util;

/**
 * config.properties文件读取
 * 
 * @author houxiaocheng
 * @date 2013-4-26 下午1:20:36
 */
public class ConfigureProperties {

	/**
	 * // 绑定端口
	 */
	public static final String BINDPORT = "bindPort";

	/**
	 * // 缓存大小
	 */
	public static final String BUFFERSIZE = "buffersize";

	/**
	 * // 最大更新记录标识
	 */
	public static final String MAXUPDATEFLAG = "maxUpdateFlag";

	/**
	 * #数据包拆包线程休眠时间间隔 1秒
	 */
	public static final String BUFFER_CHECK_PROCESSOR_SLEEPTIME = "bufferCheckProcessorSleepTime";

	/**
	 * 数据包分类线程休眠时间间隔
	 */
	public static final String BUFFER_TO_BASEPACK_PROCESSOR_SLEEPTIME = "bufferToBasePackProcessorSleepTime";

	/**
	 * 数据包解析封装线程休眠时间间隔
	 */
	public static final String DISASSEMBLY_PROCESSOR_SLEEPTIME = "disassemblyProcessorSleepTime";

	/**
	 * 数据包入库线程休眠时间间隔
	 */
	public static final String PERSISTENCE_PROCESSOR_SLEEPTIME = "persistenceProcessorSleepTime";

	/**
	 * 规则匹配线程休眠时间间隔
	 */
	public static final String REGULAR_CHECK_SLEEPTIME = "regularCheckSleepTime";

	/**
	 * 往行为超时时间
	 */
	public static final String GO_ACTION_TIME_OUT = "goActionTimeOut";

	/**
	 * UDP server IP
	 */
	public static final String UDP_SERVER_IP = "UDPServerIP";

	/**
	 * UDP server port
	 */
	public static final String UDP_SERVER_PORT = "UDPServerPort";
	
	/**激发器白名单*/
	public static final String USED_EXCITERS="usedExciters";
	
	/**是否使用激发器白名单过滤*/
	public static final String IS_FILTER_EXCITER="isFilterExciter";

	public static String getProperty(String proKey, String defaultValue) {
		return ConfigFactory.getInstance().getConfig("config.properties").get(proKey, defaultValue);
	}

	public static String getProperty(String proKey) {
		return ConfigFactory.getInstance().getConfig("config.properties").get(proKey);
	}

}
