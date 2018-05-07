package com.microwise.uma.common.util;

import junit.framework.Assert;

import org.junit.Test;

import com.microwise.uma.util.NumberUtil;

/**
 * @author xu.baoji
 * @date 2013-9-25
 */
public class NumberUtilTest {
	
	
	@Test
	public void test1(){
		boolean isNumber = NumberUtil.stringIsNumber("aa1");
		Assert.assertTrue(!isNumber);
	    isNumber  = NumberUtil.stringIsNumber("00000");
	    Assert.assertTrue(isNumber);
	}

}
