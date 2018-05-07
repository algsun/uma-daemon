package com.microwise.uma.common.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.Regular;
import com.microwise.uma.service.RegularService;

/**
 * 规则 service 测试
 * 
 * @author xubaoji
 * @date 2013-4-27
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegularServiceTest extends CleanDBTest {

	/**
	 * 规则service
	 */
	@Autowired
	private RegularService regularService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/RegularServiceTest.xml");
	}

	/**
	 * 测试获得人员规则 解析状态 信息列表
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindAnalySeState() {

	}

	/**
	 * 获得所有单程规则测试 (不携带激发器信息)
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindOneDirectionRegulars() {
//		List<Regular>regularList=regularService.findOneDirectionRegulars();
//		
//		Assert.assertNotNull(regularList);
//		Assert.assertEquals(3, regularList.size());
	}

	/**
	 * 获得所有单程规则 携带有 激发器联信息 测试
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindOneDirectionwholeRegInfo() {
//		 List<Regular> regularList= regularService.findOneDirectionwholeRegInfo();
//		 Assert.assertNotNull(regularList);
//		 Assert.assertEquals(3, regularList.size());
//		 Assert.assertNotNull(regularList.get(0).getRes());
	}

	/**
	 * 获得所有规则测试（不携带激发器信息 ）
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 * 
	 * @return
	 */
	@Test
	public void testFindRegulars() {
		List<Regular> regularList = regularService.findRegulars();
		Assert.assertNotNull(regularList);
		Assert.assertEquals(4, regularList.size());
	}
}
