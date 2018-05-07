package com.microwise.uma.common.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.RealtimeLocationPO;
import com.microwise.uma.service.RealtimeLocationService;

/**
 * 位置信息查询测试
 * 
 * @author xubaoji
 * @date 2013-4-27
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RealtimeLocationServiceFindTest extends CleanDBTest {

	/**
	 * 实时位置service
	 */
	@Autowired
    private RealtimeLocationService realtimeLocationService;	
	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/RealtimeLocationServiceTest.xml");
	}
	
	/**查询所有实时位置信息*/
	@Test
	public void testFindLocationByUserId(){
		List<RealtimeLocationPO> locationList=realtimeLocationService.findAll();
		Assert.assertNotNull(locationList);
		Assert.assertEquals(4, locationList.size());
	}

}
