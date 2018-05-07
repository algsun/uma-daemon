package com.microwise.uma.common.service;

import java.util.LinkedList;
import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.LocationPO;
import com.microwise.uma.service.LocationService;

/**
 * 位置信息查询测试
 * 
 * @author xubaoji
 * @date 2013-4-27
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationServiceFindTest extends CleanDBTest {

	/**
	 * 位置service
	 */
	@Autowired
	private LocationService locationService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/LocationServiceTest.xml");
	}
	
	/**通过用户编号获得 用户位置信息测试*/
	@Test
	public void testFindLocationByUserId(){
		List<LocationPO> locationList=locationService.findLocationsByUserId(1L);
		Assert.assertNotNull(locationList);
		Assert.assertEquals(4, locationList.size());
	}

	/**
	 * 批量保存历史位置测试
	 */
	@Test
	public void testSaveLocationBatch() {
		LinkedList<LocationPO> locationList = new LinkedList<LocationPO>();
		LocationPO location = new LocationPO();
		location.setUserId(2L);
		location.setDeviceId(1L);
		location.setCurrentTime(System.currentTimeMillis());
		locationList.add(location);

		LocationPO location1 = new LocationPO();
		location1.setUserId(2L);
		location1.setDeviceId(2L);
		location1.setCurrentTime(System.currentTimeMillis() + 50);
		locationList.add(location1);

		LocationPO location2 = new LocationPO();
		location2.setUserId(2L);
		location2.setDeviceId(3L);
		location2.setCurrentTime(System.currentTimeMillis() + 100);
		locationList.add(location2);
		locationService.saveLocationsBatch(locationList);

		List<LocationPO> locationList2 = locationService.findLocationsByUserId(2L);

		Assert.assertNotNull(locationList2);
		Assert.assertEquals(3, locationList2.size());

	}

}
