package com.microwise.uma.dao;

import java.util.List;
import java.util.Queue;

import com.microwise.uma.po.DevicePO;

/**
 * 设备Dao
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午2:39:26
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
public interface DeviceDao {

	/**
	 * 保存设备
	 * 
	 * @param device
	 *            设备对象
	 */
	public void saveDeivce(DevicePO device);

	/**
	 * 批量保存设备
	 *
     * TODO 拼写错误，应为 device @gaohui 2013-10-14
	 * @param devices
	 *            设备对象
	 */
	public void saveDeivcesBatch(Queue<DevicePO> devices);

	/**
	 * 查询所有的读卡器
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllCardReader();

	/**
	 * 查询激发器
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllExciter();

	/**
	 * 查询所有的电子卡
	 * 
	 * @return List<DevicePO>
	 */
	public List<DevicePO> findAllCard();

	/**
	 * 更新设备信息
	 * 
	 * @param device
	 *            设备对象
	 */
	public void updateDeviceInfo(DevicePO device);

}