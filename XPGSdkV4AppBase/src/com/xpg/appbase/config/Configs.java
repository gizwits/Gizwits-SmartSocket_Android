package com.xpg.appbase.config;

import com.xpg.appbase.XpgApplication;
import com.xtremeprog.xpgconnect.XPGWifiLogLevel;
/**
 * app配置参数
 * @author Lien
 * */
public class Configs {
	// 设定是否为debug版本
	public static final boolean DEBUG = true;
	// 设定AppID，参数为机智云官网中查看产品信息得到的AppID
	public static final String APPID = "42a7563f305342ae805cbb21d968a0ce";
	// 设置设备json下载路径（该路径必须存在，开发者自行创建文件夹,最好是手机内存）,sdk根据下载的json文件解析出相关的设备描述等信息
	public static final String PRODUCT_PATH = "/Devices";
	// 指定该app对应设备的product_key，如果设定了过滤，会过滤出该peoduct_key对应的设备
	public static final String PRODUCT_KEY = "6f3074fe43894547a4f1314bd7e3ae0b";
	// 是否过滤指定 PRODUCT_KEY 的设备，NO 表示不过滤，会列出所有发现到的设备
	public static final boolean PRODUCT_FILTER = true;
	// 设定日志打印级别
	public static final XPGWifiLogLevel LOG_LEVEL = XPGWifiLogLevel.XPGWifiLogLevelAll;

}
