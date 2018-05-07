package com.microwise.uma.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microwise.uma.dao.DeviceDao;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.service.DeviceService;

/**
 * 设备service接口的实现
 * 
 * @author houxiaocheng
 * @date 2013-4-24 下午3:06:59
 * 
 * @check 2013-05-07 xubaoji svn:3187
 */
@Service
@Transactional
@Scope("prototype")
public class DeviceServiceImpl implements DeviceService {

	/** 设备dao接口 */
	@Autowired
	private DeviceDao deviceDao;

	@Override
	public List<DevicePO> findAllDevices() {
		List<DevicePO> allDevices = new ArrayList<DevicePO>();
		//查询电子卡
		List<DevicePO> cards = deviceDao.findAllCard();
		//查询激发器
		List<DevicePO> exciter = deviceDao.findAllExciter();
		//查询读卡器
		List<DevicePO> cardReader = deviceDao.findAllCardReader();
		//把查询到的设备放入到同一个集合中
		allDevices.addAll(cardReader);
		allDevices.addAll(cards);
		allDevices.addAll(exciter);
		return allDevices;
	}

	@Override
	public List<DevicePO> findAllCardReaders() {
		List<DevicePO> allDevices = new ArrayList<DevicePO>();
		List<DevicePO> cardReader = deviceDao.findAllCardReader();
		allDevices.addAll(cardReader);
		return allDevices;
	}

	@Override
	public List<DevicePO> findAllCardAndExciterDevices() {
		List<DevicePO> allDevices = new ArrayList<DevicePO>();
		List<DevicePO> cards = deviceDao.findAllCard();
		List<DevicePO> exciter = deviceDao.findAllExciter();
		allDevices.addAll(cards);
		allDevices.addAll(exciter);
		return allDevices;
	}

	@Override
	public void saveDevices(Queue<DevicePO> devices) {
		deviceDao.saveDeivcesBatch(devices);
	}

	@Override
	public void updateDevices(Queue<DevicePO> devices) {
		try {
			if (devices != null && devices.size() > 0) {
				for (DevicePO device : devices) {
					deviceDao.updateDeviceInfo(device);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
