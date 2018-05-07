package com.microwise.uma.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.microwise.uma.dao.BaseDao;
import com.microwise.uma.dao.DeviceDao;
import com.microwise.uma.po.DevicePO;

/**
 * 设备dao接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:04:22
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Repository
@Scope("prototype")
public class DeviceDaoImpl extends BaseDao implements DeviceDao {

	/** 读卡器TYPE */
	private static final int CARDREADER_TYPE = 1;

	/** 激发器TYPE */
	private static final int EXCITER_TYPE = 2;

	/** 电子卡TYPE */
	private static final int CARD_TYPE = 3;

	@Override
	public void saveDeivce(DevicePO device) {

	}

	@Override
	public List<DevicePO> findAllCardReader() {
		List<DevicePO> cardReaders = getSqlSession().selectList(
				"mybatis.DeviceDao.findDevices", CARDREADER_TYPE);
		return cardReaders;
	}

	@Override
	public List<DevicePO> findAllExciter() {
		List<DevicePO> exciters = getSqlSession().selectList(
				"mybatis.DeviceDao.findDevices", EXCITER_TYPE);
		return exciters;
	}

	@Override
	public List<DevicePO> findAllCard() {
		List<DevicePO> cards = getSqlSession().selectList(
				"mybatis.DeviceDao.findDevices", CARD_TYPE);
		return cards;
	}

	@Override
	public void saveDeivcesBatch(Queue<DevicePO> devices) {
		List<DevicePO> devList = new ArrayList<DevicePO>();
		if (devices != null && devices.size() > 0) {
			while (!devices.isEmpty()) {
				devList.add(devices.poll());
			}
		}
		getSqlSession().insert("mybatis.DeviceDao.saveDeivceBatch", devList);
	}

	@Override
	public void updateDeviceInfo(DevicePO device) {

		getSqlSession().update("mybatis.DeviceDao.updDeivce", device);

	}
}
