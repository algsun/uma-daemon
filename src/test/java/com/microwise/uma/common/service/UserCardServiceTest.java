package com.microwise.uma.common.service;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.microwise.uma.common.sys.test.CleanDBTest;
import com.microwise.uma.po.UserCard;
import com.microwise.uma.service.UserCardService;

/**
 * 人卡 绑定关系 service 测试
 * 
 * @author xubaoji
 * @date 2013-4-27
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserCardServiceTest extends CleanDBTest {

	/**
	 * 人员与卡关系 service
	 */
	@Autowired
	private UserCardService userCardService;

	@Before
	public void before() throws Exception {
		CleanDBTest.xmlInsert2("dbxml/UserCardServiceTest.xml");
	}

	/**
	 * 获得所有人员与卡绑定关系信息
	 * 
	 * @author 许保吉
	 * @date 2013-4-27
	 */
	@Test
	public void testFindAllRelations() {
		List<UserCard> userCardList = userCardService.findAllRelations();
		Assert.assertNotNull(userCardList);
		Assert.assertEquals(2, userCardList.size());
	}

}
