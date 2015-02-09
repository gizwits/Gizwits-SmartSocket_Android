package com.xpg.common.useful;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * <P>
 * JSON处理的通用方法
 * <P>
 * 
 * @author Sunny Ding
 * @version 1.00
 * 
 * */
public class JSONUtils {
	/**
	 * JSON解释
	 * 
	 * @param str
	 *            JSON字符串
	 * @param name
	 *            关键字
	 * @return result
	 *            关键字对应的值
	 * */
	public static String ParseJSON(String str,String name){
		if(str.isEmpty()||name.isEmpty())
			return null;
		
		String result = null;
		  try {
			   JSONObject myJSobj= new JSONObject(str);
			   result = myJSobj.has(name)?myJSobj.getString(name):null;
			  } catch (JSONException e) {
			   e.printStackTrace();
			  }
		  
		  return result;
	}
}
