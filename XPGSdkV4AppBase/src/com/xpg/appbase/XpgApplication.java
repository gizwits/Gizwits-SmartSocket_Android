/**
 * Project Name:XPGSdkV4AppBase
 * File Name:XpgApplication.java
 * Package Name:com.xpg.appbase
 * Date:2014-12-15 14:17:52
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
package com.xpg.appbase;

import android.app.Application;

import com.xpg.appbase.config.Configs;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class XpgApplication. <br/> 
 * <br/>
 * date: 2014-12-15 14:17:52 <br/> 
 *
 * @author Lien
 */
public class XpgApplication extends Application {
	
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	public void onCreate() {
		super.onCreate();
		
		XPGWifiSDK.sharedInstance().startWithAppID(getApplicationContext(), Configs.APPID);
		
		XPGWifiSDK.sharedInstance().setLogLevel(Configs.LOG_LEVEL,null, true);
	}
}
