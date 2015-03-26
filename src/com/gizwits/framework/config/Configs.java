/**
 * Project Name:XPGSdkV4AppBase
 * File Name:Configs.java
 * Package Name:com.gizwits.framework.config
 * Date:2015-1-27 14:47:04
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.framework.config;

import com.xtremeprog.xpgconnect.XPGWifiSDK.XPGWifiLogLevel;

// TODO: Auto-generated Javadoc
/**
 * app配置参数.
 * 
 * @author Lien
 */
public class Configs {
	
	/**  日志保存文件名. */
	public static final int DEVICE_NAME_KEEP_LENGTH = 8;
	
	/**  设定是否为debug版本. */
	public static final boolean DEBUG = true;
	
	/**  设定AppID，参数为机智云官网中查看产品信息得到的AppID. */
	public static final String APPID = "ecb16888bb794c68b15606f8247f3e31";
	
	/**  指定该app对应设备的product_key，如果设定了过滤，会过滤出该peoduct_key对应的设备. */
	public static final String PRODUCT_KEY = "e3cf7332b7834a03a92d9e14a3f6d352";
	
	/**  设定日志打印级别. */
	public static final XPGWifiLogLevel LOG_LEVEL = XPGWifiLogLevel.XPGWifiLogLevelAll;
	
	/**  日志保存文件名. */
	public static final String LOG_FILE_NAME = "BassApp.log";

}
