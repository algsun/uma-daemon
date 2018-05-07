package com.microwise.uma.service;

import java.util.List;
import java.util.Queue;

import com.microwise.uma.po.DevicePO;

/**   
* 设备Service
* @author houxiaocheng 
* @date 2013-4-24 下午2:17:39 
* 
* @check 2013-05-07 xubaoji svn:3187
*/
public interface DeviceService {

	/**
	 * 查询所有的可用设备
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllDevices();

	/**
	 * 查询所有的读卡器
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllCardReaders();

	/**
	 * 查询所有的激发器和电子卡
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllCardAndExciterDevices();

	/**
	 * 批量保存设备
	 * 
	 * @param devices
	 */
	public void saveDevices(Queue<DevicePO> devices);
	
	/**
	 * 更新设备信息
	 * @param devices 更新设备队列
	 */
	public void updateDevices(Queue<DevicePO> devices);
}
