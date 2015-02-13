package com.xpg.common.useful;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * <P>
 * 字符串处理的通用方法
 * <P>
 * 
 * @author Lien Li
 * @version 1.00
 * 
 * */
public class StringUtils {

	/** 字符串编码 */
	public static final String ENCODING_UTF8 = "utf-8";

	/**
	 * 返回一个StringBuffer对象
	 * 
	 * @return 返回StringBuffer对象,长度50
	 * */
	public static StringBuffer getBuffer() {
		return new StringBuffer(50);
	}

	/**
	 * 
	 * 返回一个指定长度的StringBuffer对象
	 * 
	 * @param length
	 *            指定长度
	 * 
	 * @return 返回StringBuffer对象
	 * */
	public static StringBuffer getBuffer(int length) {
		return new StringBuffer(length);
	}

	/**
	 * 判断字符串是否为null或者为空
	 * 
	 * @param str
	 *            传入的字符串
	 * @return boolean true or false
	 * */
	public static boolean isEmpty(String str) {
		if (str == null || str == "" || str.trim().equals(""))
			return true;
		return false;
	}

	/**
	 * 判断字符串是否为0或者为空
	 * 
	 * @param str
	 *            传入的字符串
	 * @return boolean true or false
	 * */
	public static boolean isNumEmpty(String str) {
		if (str == null || str == "" || str.trim().equals("")
				|| str.trim().equals("0"))
			return true;
		return false;
	}

	/**
	 * 将一个字符串进行UTF8编码后返回
	 * 
	 * @param data
	 *            传入的字符串
	 * @return String
	 * */
	public static String encode(String data) {
		try {
			return URLEncoder.encode(data, ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 将字符串转移成整数
	 * 
	 * @param num
	 *            传入的字符串
	 * @return int
	 * */
	public static int toInt(String num) {
		try {
			return Integer.parseInt(num);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 将字符串转移成长整数
	 * 
	 * @param num
	 *            传入的字符串
	 * @return long
	 * */
	public static long toLong(String num) {
		try {
			return Long.parseLong(num);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 将字符串转移成浮点数
	 * 
	 * @param num
	 *            传入的字符串
	 * @return long
	 * */
	public static float toFloat(String num) {
		try {
			return Float.parseFloat(num);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 将字符串转移成布尔数
	 * 
	 * @param num
	 *            传入的字符串
	 * @return boolean
	 * */
	public static boolean toBoolean(String num) {
		if (isEmpty(num))
			return false;
		if (num.equals("true"))
			return true;
		return false;
	}

	/**
	 * sql特殊字符转义
	 * 
	 * @param keyWord
	 *            关键字
	 * 
	 * @return 转义后的字符串
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
	 * 
	 * @return 反转义后的字符串
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
	 * @param time
	 *            需要格式日期的长整数
	 * @param format
	 *            格式化参数
	 * @return 格式化后的日期
	 */
	public static String getStrDate(long time, String format) {
		Date date = new Date(time);
		return getStrDate(date, format);
	}

	/**
	 * 返回当前日期的格式化表示
	 * 
	 * @param date
	 *            指定格式化的日期
	 * @param formate
	 *            格式化参数
	 * @return 格式化后的日期
	 */
	public static String getStrDate(Date date, String formate) {
		SimpleDateFormat dd = new SimpleDateFormat(formate);
		return dd.format(date);
	}

	/**
	 * 
	 * 返回当前日期的格式化（yyyy-MM-dd）表示
	 * 
	 * @return 格式化的字符串
	 * 
	 * */
	public static String getStrDate() {
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd");
		return dd.format(new Date());
	}

	/**
	 * 返回指定个数的随机数字串
	 * 
	 * @param num
	 *            随机数的位数
	 * @return String
	 * 
	 * */
	public static String getRandomStr(int num) {
		StringBuffer temp = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < num; i++) {
			temp.append(rand.nextInt(10));
		}
		return temp.toString();
	}

}
