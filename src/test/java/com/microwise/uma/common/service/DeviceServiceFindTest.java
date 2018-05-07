package com.microwise.uma.common.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.DevicePO;
import com.microwise.uma.service.DeviceService;

/**
 * 设备查询 测试
 * 
 * @author xubaoji
 * @date 2013-4-26
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeviceServiceFindTest extends CleanDBTest {

	/** 设备service */
	@Autowired
	private DeviceService deviceService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/DeviceServiceTest.xml");
	}

	/***
	 * 查询所有激发器和电子卡设备 测试
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindAllCardAndExciterDevices() {
		
		List<DevicePO> deviceList= deviceService.findAllCardAndExciterDevices();
		Assert.assertNotNull(deviceList);
		Assert.assertEquals(5, deviceList.size());
		
	}

	/**
	 * 查询所有读卡器设备 测试
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindAllCardReadersTest() {
		List<DevicePO> deviceList=deviceService.findAllCardReaders();
		Assert.assertNotNull(deviceList);
		Assert.assertEquals(1, deviceList.size());
	}

	/**
	 * 查询所有设备 测试
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindAllDevicesTest() {
		
		List<DevicePO> deviceList= deviceService.findAllDevices();
	    Assert.assertNotNull(deviceList);
	    Assert.assertEquals(6, deviceList.size());
	}

}
