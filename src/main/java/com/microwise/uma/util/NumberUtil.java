package com.microwise.uma.util;


/**
 * @author xu.baoji
 * @date 2013-9-25
 */
public class NumberUtil {
	
	/***
	 * 判断一个string的字符串是是否为纯数字 （整数）
	 * 
	 * @author xu.baoji
	 * @date 2013-9-25
	 *
	 * @param s
	 * @return
	 */
	public static boolean stringIsNumber(String s){
		try {
		      Integer.parseInt(s);
		} catch (Exception e) {
			return false;
		}
       return true;
	}
}
