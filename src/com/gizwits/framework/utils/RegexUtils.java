package com.gizwits.framework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

 class RegexUtils {
	static boolean flag = false;
	static String regex = "";

	private static boolean check(String str, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 *  判断字符串是否为null或者为空.
	 *
	 * @param str the str
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str == "" || str.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 验证手机号码
	 * 
	 * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
	 * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean checkCellphone(String cellphone) {
		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
		return check(cellphone, regex);
	}
	
	/**
	 * 验证密码
	 * 
	 * 
	 * @param cellphone
	 * @return
	 */
	public static boolean checkPassword(String password) {
		String regex = "^[\\x10-\\x1f\\x21-\\x7f]+$";
		return check(password, regex);
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return check(email, regex);
	}
}