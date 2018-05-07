package com.microwise.uma.controller;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microwise.uma.pack.CardReaderPack;
import com.microwise.uma.pack.LocationInfoPack;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.po.RealtimeLocationPO;

/**
 * 入库队列控制器
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:52:20
 * 
 * @check 2013-05-06 xubaoji svn:3187
 * @check 2013-10-14 @gaohui #5791
 */
@Component
public class PackToPoContoller {

	private static final Logger logger = LoggerFactory.getLogger(PackToPoContoller.class);

	/*** 设备新增队列 */
	private Queue<DevicePO> deviceList = new ConcurrentLinkedQueue<DevicePO>();

	/** 设备更新队列 */
	private Queue<DevicePO> deviceUpdateList = new ConcurrentLinkedQueue<DevicePO>();

	/** 历史位置信息队列 */
	private Queue<LocationPO> locationList = new ConcurrentLinkedQueue<LocationPO>();

	/** 实时位置待保存Map */
	private Map<Long, RealtimeLocationPO> realtimeLocationForSaveMap = new ConcurrentHashMap<Long, RealtimeLocationPO>();

	/** 实时位置待更新MAP */
	private Map<Long, RealtimeLocationPO> realtimeLocationForUpdateMap = new ConcurrentHashMap<Long, RealtimeLocationPO>();

	/** 位置点包队列（待解析的位置点包） */
	private Queue<LocationInfoPack> locationPackQueue = new ConcurrentLinkedQueue<LocationInfoPack>();

	/** 读卡器包队列（待解析的读卡器包） */
	private Queue<CardReaderPack> cardReaderPackQueue = new ConcurrentLinkedQueue<CardReaderPack>();

	/**
	 * 批量加入待 入库的历史 位置列表
	 * 
	 * @param locationList
	 *            历史位置列表
	 */
	public void addLocationPO(LinkedList<LocationPO> locationList) {
		long time = 0;
		while (!locationList.isEmpty()) {
			LocationPO location = locationList.poll();
			// 后解析的历史位置时间 一定要 大于 上一包历史位置时间
			if (location != null && location.getCurrentTime() != null
					&& location.getCurrentTime() >= time) {
				this.locationList.offer(location);
			} else {
				logger.error("历史位置包时间错误: lastTime : " + time + " ; thisTime = "
						+ location.getCurrentTime() + "userId = " + location.getUserId()
						+ " exciterSn = " + location.getDeviceId());
			}
			time = location.getCurrentTime();
		}
	}

	/**
	 * 批量导出待入库的历史位置队列 并清除
	 * 
	 * @return ConcurrentLinkedQueue<LocationPO>
	 */
	public Queue<LocationPO> persistenceLocation() {
		Queue<LocationPO> locations = new LinkedList<LocationPO>();
		while (!locationList.isEmpty()) {
			locations.add(locationList.poll());
		}
		return locations;
	}

	/**
	 * 批量添加待更新的 实时位置列表
	 * 
	 * @param updMap
	 *            待更新实时位置Map
	 */
	public void addRealtimeLocationPOForUpdate(Map<Long, RealtimeLocationPO> updMap) {
		realtimeLocationForUpdateMap.putAll(updMap);
	}

	/**
	 * 批量导出要入库的实时位置列表，并把之前的存储清空
	 * 
	 * @return
	 */
	public Queue<RealtimeLocationPO> persistenceRealtimeLocationForUpdate() {
		Queue<RealtimeLocationPO> realTimeLocations = new LinkedList<RealtimeLocationPO>();
		for (Long key : realtimeLocationForUpdateMap.keySet()) {
			realTimeLocations.add(realtimeLocationForUpdateMap.remove(key));
		}
		return realTimeLocations;
	}

	/**
	 * 添加待新增设备到新增设备队列
	 * 
	 * @param deviceList
	 *            设备持久化对象队列
	 */
	public void addDevicePO(LinkedList<DevicePO> deviceList) {
		this.deviceList.addAll(deviceList);
	}

	/**
	 * 批量导出要新增的设备列表，并把之前的存储清空
	 * 
	 * @return
	 */
	public Queue<DevicePO> persistenceDevice() {
		Queue<DevicePO> devices = new LinkedList<DevicePO>();
		while (!deviceList.isEmpty()) {
			devices.add(deviceList.poll());
		}
		return devices;
	}

	/**
	 * 添加需要更新的设备 到设备更新队列
	 * 
	 * @param updateDevices
	 *            需要更新的设备列表
	 */
	public void addUpdateDevicePO(Map<Long, DevicePO> updateDevices) {
		for (Long key : updateDevices.keySet()) {
			this.deviceUpdateList.add(updateDevices.get(key));
		}
	}

	/**
	 * 批量导出要更新设备，并把之前的存储清空
	 * 
	 * @return
	 */
	public Queue<DevicePO> persistenceUpdateDevice() {
		Queue<DevicePO> devices = new LinkedList<DevicePO>();
		while (!deviceUpdateList.isEmpty()) {
			devices.add(deviceUpdateList.poll());
		}
		return devices;
	}

	/**
	 * 批量加入位置包
	 * 
	 * @param packList
	 *            位置包队列
	 */
	public void pushLocations(Queue<LocationInfoPack> packList) {
		locationPackQueue.addAll(packList);
	}
	
	/**添加位置包*/
	public void offerLocation(LocationInfoPack location){
		locationPackQueue.offer(location);
	}

	/**
	 * 批量导出解析包并把之前的存储清空（位置信息包）
	 * 
	 * @return 待解析位置包队列
	 */
	public synchronized Queue<LocationInfoPack> checkLocationsPackList() {
		Queue<LocationInfoPack> buffList = new LinkedList<LocationInfoPack>();
		while (!locationPackQueue.isEmpty()) {
			buffList.offer(locationPackQueue.poll());
		}
		return buffList;
	}

	/**
	 * 批量加入读卡器数据包 2013-10-14
	 * 
	 * @param packList
	 *            读卡器数据包队列
	 */
	public void pushCardReaders(Queue<CardReaderPack> packList) {
		cardReaderPackQueue.addAll(packList);
	}

	/**添加 读卡器包*/
	public void offerCardReader(CardReaderPack cardReader){
		cardReaderPackQueue.offer(cardReader);
	}
	
	/**
	 * 批量导出解析包并把之前的存储清空 （读卡器信息包）
	 * 
	 * @return
	 */
	public synchronized Queue<CardReaderPack> checkCardReadersPackList() {
		Queue<CardReaderPack> buffList = new LinkedList<CardReaderPack>();
		while (!cardReaderPackQueue.isEmpty()) {
			buffList.offer(cardReaderPackQueue.poll());
		}
		return buffList;
	}

	/**
	 * 向实时位置添加队列中加入数据
	 * 
	 * @param saveMap
	 *            待保存数据
	 */
	public void addRealtimeLocationPOForSave(Map<Long, RealtimeLocationPO> saveMap) {
		realtimeLocationForSaveMap.putAll(saveMap);
	}

	/**
	 * 批量导出实时位置 添加队列中的数据 ，并清空队列
	 * 
	 * @return
	 */
	public Queue<RealtimeLocationPO> persistenceRealtimeLocationForSave() {
		Queue<RealtimeLocationPO> realTimeLocations = new LinkedList<RealtimeLocationPO>();
		for (Long key : realtimeLocationForSaveMap.keySet()) {
			realTimeLocations.add(realtimeLocationForSaveMap.remove(key));
		}
		return realTimeLocations;
	}

	/**
	 * 判断 是否有需要 解析的数据包
	 * 
	 * @author xu.baoji
	 * @date 2013-9-17
	 * 
	 * @return boolean true 需要 false 不需要
	 */
	public boolean needPackDisassembly() {
		if (locationPackQueue.size() > 0 || cardReaderPackQueue.size() > 0) {
			return true;
		}
		return false;
	}

	public Queue<LocationPO> getLocationList() {
		return locationList;
	}

	public void setLocationList(Queue<LocationPO> locationList) {
		this.locationList = locationList;
	}

	public Queue<CardReaderPack> getCardReaderPackQueue() {
		return cardReaderPackQueue;
	}

	public void setCardReaderPackQueue(Queue<CardReaderPack> cardReaderPackQueue) {
		this.cardReaderPackQueue = cardReaderPackQueue;
	}
}
