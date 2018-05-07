package com.microwise.uma.processor;

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microwise.uma.controller.AllController;
import com.microwise.uma.controller.PackToPoContoller;
import com.microwise.uma.controller.SystemConfigure;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.service.DeviceService;
import com.microwise.uma.service.LocationService;
import com.microwise.uma.service.RealtimeLocationService;
import com.microwise.uma.util.ConfigureProperties;

/**
 * 持久化线程
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:28:32
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Component
public class PackPersistenceProcessor implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PackPersistenceProcessor.class);

	/** 包解析到可持久化对象控制器 */
	@Autowired
	private PackToPoContoller poController;

	/** 设备Service接口 */
	@Autowired
	private DeviceService deviceService;

	/** 位置service接口 */
	@Autowired
	private LocationService locationService;

	/** 实时位置service接口 */
	@Autowired
	private RealtimeLocationService realtimeService;

	/** 系统初始化配置信息 */
	@Autowired
	private SystemConfigure sysconfuger;

	/** 系统 队列 总控制器 */
	@Autowired
	private AllController allController;

	/**
	 * 运行持久化线程
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("持久化线程启动");
		while (!SystemConfigure.exit) {
			try {
				logger.debug("---------信息录入---------");
				Queue<DevicePO> devices = poController.persistenceDevice();
				logger.debug("---------新增设备信息录入---------设备数：" + devices.size());
				// 先判断设备是否有新增
				if (devices != null && devices.size() > 0) {
					deviceService.saveDevices(devices);
					sysconfuger.reloadAllDevices();
				}
				Queue<DevicePO> updateDevices = poController.persistenceUpdateDevice();
				// 判断设备信息是否有更新
				if (updateDevices != null && updateDevices.size() > 0) {
					deviceService.updateDevices(updateDevices);
					sysconfuger.reloadAllDevices();
				}
				// 最新位置点入库
				Queue<RealtimeLocationPO> realSavelist = poController
						.persistenceRealtimeLocationForSave();
				// 最新位置点更新
				Queue<RealtimeLocationPO> realUpdatelist = poController
						.persistenceRealtimeLocationForUpdate();
				if (realSavelist.size() > 0 || realUpdatelist.size() > 0) {
					realtimeService.saveOrUpdateLocations(realSavelist, realUpdatelist);
				}
				Queue<LocationPO> locations = poController.persistenceLocation();
				logger.debug("---------位置点信息录入---------点数：" + locations.size());
				if (locations != null && locations.size() > 0) {
					locationService.saveLocationsBatch(locations);
					// 将历史位置信息 添加到 历史位置队列 locationQueue
					allController.addBeachToHistroyLocationQueue(locations);
				}

				logger.debug("---------信息录入结束---------");
				Thread.sleep(SystemConfigure.getConfigure(
						ConfigureProperties.PERSISTENCE_PROCESSOR_SLEEPTIME,
						SystemConfigure.PERSISTENCE_PROCESSOR_SLEEPTIME));
			} catch (Exception e) {
				logger.error("入库线程异常:", e);
			}
		}
	}

}
