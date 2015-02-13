/**
 * Project Name:XPGSdkV4AppBase
 * File Name:XpgApplication.java
 * Package Name:com.gizwits.framework
 * Date:2015-1-22 18:16:04
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
package com.gizwits.framework;

import android.app.Application;

import com.gizwits.framework.config.Configs;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

/**
 * 
 * The Class WApplication.
 * 
 * Application类
 * 
 * @author Lien
 */
public class XpgApplication extends Application {

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	public void onCreate() {
		super.onCreate();
		// 初始化sdk,传入appId,登录机智云官方网站查看产品信息获得 AppID
		XPGWifiSDK.sharedInstance().startWithAppID(getApplicationContext(),
				Configs.APPID);
		// 设定日志打印级别,日志保存文件名，是否在后台打印数据.
		XPGWifiSDK.sharedInstance().setLogLevel(Configs.LOG_LEVEL,
				"BassApp.log", Configs.DEBUG);
	}
}
