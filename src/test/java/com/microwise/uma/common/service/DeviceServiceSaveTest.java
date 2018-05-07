package com.microwise.uma.common.service;

import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.service.DeviceService;

/**
 * 设备添加测试
 * 
 * @author xubaoji
 * @date  2013-4-27 
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceServiceSaveTest extends CleanDBTest{
	
	

	/** 设备service */
	@Autowired
	private DeviceService deviceService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/DeviceServiceTest.xml");
	}

	/**
	 * 批量添加 设备测试
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testSaveDevicesTest() {
		Queue<DevicePO> devices=new ConcurrentLinkedQueue<DevicePO>();
		DevicePO  device=new DevicePO();	
		device.setType(1);
		device.setDeviceId("0002");
		device.setName("读卡器");
		device.setSn("12345677");
		device.setSiteId("123456");
		device.setCreateTime(new Date());
		devices.add(device);
		
		DevicePO  device1=new DevicePO();	
		device1.setType(2);
		device1.setName("激发器");
		device1.setSn("1234410");
		device1.setSiteId("123456");
		device.setCreateTime(new Date());
		devices.add(device1);
		
		DevicePO  device2=new DevicePO();	
		device2.setType(3);
		device2.setName("电子卡");
		device2.setSn("12344435");
		device2.setSiteId("123456");
		device.setCreateTime(new Date());
		devices.add(device2);
		
		deviceService.saveDevices(devices);
		
		List<DevicePO> findDeviceList=deviceService.findAllDevices();
		
		Assert.assertNotNull(findDeviceList);
		Assert.assertEquals(9, findDeviceList.size());
		
	}

}
