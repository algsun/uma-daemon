package com.microwise.uma.common.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.service.RealtimeLocationService;

/**
 * 实时位置添加和修改测试
 * 
 * @author xubaoji
 * @date 2013-4-27
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RealtimeLocationServiceSvaeOrUpdateTest extends CleanDBTest {

	/**
	 * 实时位置service
	 */
	@Autowired
	private RealtimeLocationService realtimeLocationService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/RealtimeLocationServiceTest.xml");
	}

	/** 添加 实时位置测试 */
	@Test
	public void testSaveLocationByUserId() {
		ConcurrentLinkedQueue<RealtimeLocationPO> saveRealtimeList = new ConcurrentLinkedQueue<RealtimeLocationPO>();
		RealtimeLocationPO realtimeLocation = new RealtimeLocationPO();
		realtimeLocation.setDeviceId(5L);
		realtimeLocation.setUserId(5L);
		realtimeLocation.setOccurrenceTime(new Date().getTime());
		saveRealtimeList.add(realtimeLocation);

		RealtimeLocationPO realtimeLocation1 = new RealtimeLocationPO();
		realtimeLocation1.setDeviceId(1L);
		realtimeLocation1.setUserId(6L);
		realtimeLocation1.setOccurrenceTime(new Date().getTime());
		saveRealtimeList.add(realtimeLocation1);

		realtimeLocationService.saveOrUpdateLocations(saveRealtimeList, null);
		List<RealtimeLocationPO> realtimeLoctionList = realtimeLocationService
				.findAll();
		Assert.assertNotNull(realtimeLoctionList);
		Assert.assertEquals(6, realtimeLoctionList.size());
	}

	/**
	 * 修改 实时位置测试
	 */
	@Test
	public void testUpdateLocationByUserId() {
		ConcurrentLinkedQueue<RealtimeLocationPO> updateRealtimeList = new ConcurrentLinkedQueue<RealtimeLocationPO>();
		RealtimeLocationPO realtimeLocation = new RealtimeLocationPO();
		realtimeLocation.setDeviceId(1L);
		realtimeLocation.setUserId(5L);
		realtimeLocation.setOccurrenceTime(new Date().getTime());
		updateRealtimeList.add(realtimeLocation);

		RealtimeLocationPO realtimeLocation1 = new RealtimeLocationPO();
		realtimeLocation1.setDeviceId(2L);
		realtimeLocation1.setUserId(6L);
		realtimeLocation1.setOccurrenceTime(new Date().getTime());
		updateRealtimeList.add(realtimeLocation1);

		realtimeLocationService.saveOrUpdateLocations(null, updateRealtimeList);
		List<RealtimeLocationPO> realtimeLoctionList = realtimeLocationService
				.findAll();
		Assert.assertNotNull(realtimeLoctionList);
		Assert.assertEquals(4, realtimeLoctionList.size());
	}

}
