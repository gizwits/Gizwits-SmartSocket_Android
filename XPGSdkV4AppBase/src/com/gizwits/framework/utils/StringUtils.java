package com.gizwits.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串工具类
 * 
 * @author Lien
 * 
 */
public class StringUtils {

	/** 将字符串转移成整数 */
	public static int toInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch (Exception e) {
			return 0;
		}
	}

	/** 判断字符串是否为null或者为空 */
	public static boolean isEmpty(String str) {
		if (str == null || str == "" || str.trim().equals(""))
			return true;
		return false;
	}

	/** 返回一个StringBuffer对象 */
	public static StringBuffer getBuffer() {
		return new StringBuffer(50);
	}

	/** 返回一个StringBuffer对象 */
	public static StringBuffer getBuffer(int length) {
		return new StringBuffer(length);
	}

	/**
	 * 格式一个日期
	 * 
	 * @param longDate
	 *            需要格式日期的长整数的字符串形式
	 * @param format
	 *            格式化参数
	 * @return 格式化后的日期
	 */
	public static String getStrDate(String longDate, String format) {
		if (isEmpty(longDate))
			return "";
		long time = Long.parseLong(longDate);
		Date date = new Date(time);
		return getStrDate(date, format);
	}

	/**
	 * 格式一个日期
	 * 
	 * @param longDate
	 *            需要格式日期的长整数
	 * @param format
	 *            格式化参数
	 * @return 格式化后的日期
	 */
	public static String getStrDate(long time, String format) {
		Date date = new Date(time);
		return getStrDate(date, format);
	}

	/** 返回当前日期的格式化（yyyy-MM-dd）表示 */
	public static String getStrDate() {
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		return dd.format(new Date());
	}

	/**
	 * 返回当前日期的格式化表示
	 * 
	 * @param date
	 *            指定格式化的日期
	 * @param formate
	 *            格式化参数
	 * @return
	 */
	public static String getStrDate(Date date, String formate) {
		SimpleDateFormat dd = new SimpleDateFormat(formate);
		return dd.format(date);
	}

	/**
	 * sql特殊字符转义
	 * 
	 * @param keyWord
	 *            关键字
	 * */
	public static String sqliteEscape(String keyWord) {
		keyWord = keyWord.replace("/", "//");
		keyWord = keyWord.replace("'", "''");
		keyWord = keyWord.replace("[", "/[");
		keyWord = keyWord.replace("]", "/]");
		keyWord = keyWord.replace("%", "/%");
		keyWord = keyWord.replace("&", "/&");
		keyWord = keyWord.replace("_", "/_");
		keyWord = keyWord.replace("(", "/(");
		keyWord = keyWord.replace(")", "/)");
		return keyWord;
	}
	
	/**
	 * sql特殊字符反转义
	 * 
	 * @param keyWord
	 *            关键字
	 * */
	public static String sqliteUnEscape(String keyWord) {
		keyWord = keyWord.replace("//", "/");
		keyWord = keyWord.replace("''", "'");
		keyWord = keyWord.replace("/[", "[");
		keyWord = keyWord.replace("/]", "]");
		keyWord = keyWord.replace("/%", "%");
		keyWord = keyWord.replace("/&", "&");
		keyWord = keyWord.replace("/_", "_");
		keyWord = keyWord.replace("/(", "(");
		keyWord = keyWord.replace("/)", ")");
		return keyWord;
	}

}
