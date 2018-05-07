package com.microwise.uma.util;

import com.google.gson.Gson;

/**
 * gson 工具类
 * 
 * @author xu.baoji
 * @date 2013-9-17
 */
public class GsonUtil {

	/***
	 * 将object 实体转换为 json 数据
	 * @author xu.baoji
	 * @date 2013-9-17
	 *
	 * @param o
	 * @return string 
	 */
	public static String objectToJson(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

}
