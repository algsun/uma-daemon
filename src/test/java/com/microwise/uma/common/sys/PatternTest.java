package com.microwise.uma.common.sys;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author xu.baoji
 * @date 2013-9-23
 */
public class PatternTest {

	@Test
	public void patternTest() {
		Pattern p = Pattern.compile("6\\{(\\d+)\\}207\\{(\\d+)\\}");
		Matcher m = p.matcher("46{0123}207{0123456789}46{1323313}201{123}");
		int start = 0;
		String s = "";
		while (m.find(start)) {
			start = m.end();
			s = m.group(1);
		}
		Assert.assertNotNull(start);
		Assert.assertNotNull(s);
		Assert.assertTrue(s.length() > 0);
	}

	@Test
	public void bufferTest() {
		ByteBuffer buffer = ByteBuffer.wrap(new byte[] { 'a', '1', '2' });
		int a = buffer.getShort();
		System.out.println(a);
	}
}
