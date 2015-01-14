/**
 * Project Name:XPGSdkV4AppBase
 * File Name:BaseActivity.java
 * Package Name:com.gizwits.aircondition.activity
 * Date:2014-12-30 23:15:52
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
package com.gizwits.aircondition.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gizwits.aircondition.sdk.CmdCenter;
import com.gizwits.aircondition.sdk.SettingManager;
import com.gizwits.aircondition.utils.Historys;
import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiDeviceListener;
import com.xtremeprog.xpgconnect.XPGWifiSDKListener;
import com.xtremeprog.xpgconnect.XPGWifiSSID;

/**
 * 所有activity的基类。该基类实现了XPGWifiDeviceListener和XPGWifiSDKListener两个监听器，并提供全局的回调方法。
 * .
 * 
 * @author Lien Li
 */
public class BaseActivity extends Activity {

	/**
	 * 设备列表.
	 */
	protected static List<XPGWifiDevice> deviceslist = new ArrayList<XPGWifiDevice>();
	protected static List<XPGWifiDevice> bindlist = new ArrayList<XPGWifiDevice>();

//	/**
//	 * The is init.
//	 */
//	static boolean isInit = false;

	/**
	 * 指令管理器.
	 */
	protected CmdCenter mCenter;

	/**
	 * SharePreference处理类.
	 */
	protected SettingManager setmanager;

	protected static XPGWifiDevice mXpgWifiDevice;

	/**
	 * XPGWifiDeviceListener
	 * <p/>
	 * 设备属性监听器。 设备连接断开、获取绑定参数、获取设备信息、控制和接受设备信息相关.
	 */
	protected XPGWifiDeviceListener deviceListener = new XPGWifiDeviceListener() {

		@Override
		public void didDeviceOnline(XPGWifiDevice device, boolean isOnline) {
			BaseActivity.this.didDeviceOnline(device, isOnline);
		}

		@Override
		public void didDisconnected(XPGWifiDevice device) {
			BaseActivity.this.didDisconnected(device);
		}

		@Override
		public void didLogin(XPGWifiDevice device, int result) {
			BaseActivity.this.didLogin(device, result);
		}

		@Override
		public void didReceiveData(XPGWifiDevice device,
				ConcurrentHashMap<String, Object> dataMap, int result) {
			BaseActivity.this.didReceiveData(device, dataMap, result);
		}

	};

	/**
	 * XPGWifiSDKListener
	 * <p/>
	 * sdk监听器。 配置设备上线、注册登录用户、搜索发现设备、用户绑定和解绑设备相关.
	 */
	private XPGWifiSDKListener sdkListener = new XPGWifiSDKListener() {

		@Override
		public void didBindDevice(int error, String errorMessage, String did) {
			Log.d("Base扫描结果", "error=" + error + ";errorMessage="
					+ errorMessage + ";did=" + did);
			BaseActivity.this.didBindDevice(error, errorMessage, did);
		}

		@Override
		public void didChangeUserEmail(int error, String errorMessage) {
			BaseActivity.this.didChangeUserEmail(error, errorMessage);
		}

		@Override
		public void didChangeUserPassword(int error, String errorMessage) {
			// Log.d("BaseActivity", "didChangeUserPassword");
			BaseActivity.this.didChangeUserPassword(error, errorMessage);
		}

		@Override
		public void didChangeUserPhone(int error, String errorMessage) {
			BaseActivity.this.didChangeUserPhone(error, errorMessage);
		}

		@Override
		public void didDiscovered(int error, List<XPGWifiDevice> devicesList) {

			BaseActivity.this.didDiscovered(error, devicesList);
		}

		@Override
		public void didGetSSIDList(int error, List<XPGWifiSSID> ssidInfoList) {
			BaseActivity.this.didGetSSIDList(error, ssidInfoList);
		}

		@Override
		public void didRegisterUser(int error, String errorMessage, String uid,
				String token) {
			BaseActivity.this.didRegisterUser(error, errorMessage, uid, token);
		}

		@Override
		public void didRequestSendVerifyCode(int error, String errorMessage) {
			BaseActivity.this.didRequestSendVerifyCode(error, errorMessage);
		}

		@Override
		public void didSetDeviceWifi(int error, XPGWifiDevice device) {
			BaseActivity.this.didSetDeviceWifi(error, device);
		}

		@Override
		public void didUnbindDevice(int error, String errorMessage, String did) {
			BaseActivity.this.didUnbindDevice(error, errorMessage, did);
		}

		@Override
		public void didUserLogin(int error, String errorMessage, String uid,
				String token) {
			BaseActivity.this.didUserLogin(error, errorMessage, uid, token);
		}

		@Override
		public void didUserLogout(int error, String errorMessage) {
			BaseActivity.this.didUserLogout(error, errorMessage);
		}

	};;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setmanager = new SettingManager(this);
		mCenter = CmdCenter.getInstance(this);
		Historys.put(this);
		mCenter.getXPGWifiSDK().setListener(sdkListener);
	}

	/**
	 * 用户登出回调借口.
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 */
	protected void didUserLogout(int error, String errorMessage) {
		// TODO Auto-generated method stub

	}

	/**
	 * 用户登陆回调接口.
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 * @param uid
	 *            用户id
	 * @param token
	 *            授权令牌
	 */
	protected void didUserLogin(int error, String errorMessage, String uid,
			String token) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设备解除绑定回调接口.
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 * @param did
	 *            设备注册id
	 */
	protected void didUnbindDevice(int error, String errorMessage, String did) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设备配置结果回调
	 * 
	 * @param error
	 *            结果代码
	 * @param device
	 *            设备对象
	 */
	protected void didSetDeviceWifi(int error, XPGWifiDevice device) {
		// TODO Auto-generated method stub

	}

	/**
	 * 请求手机验证码回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 */
	protected void didRequestSendVerifyCode(int error, String errorMessage) {
		// TODO Auto-generated method stub

	}

	/**
	 * 注册用户结果回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 * @param uid
	 *            the 用户id
	 * @param token
	 *            the 授权令牌
	 */
	protected void didRegisterUser(int error, String errorMessage, String uid,
			String token) {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取ssid列表回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param ssidInfoList
	 *            ssid列表
	 */
	protected void didGetSSIDList(int error, List<XPGWifiSSID> ssidInfoList) {
		// TODO Auto-generated method stub

	}

	/**
	 * 搜索设备回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param devicesList
	 *            设备列表
	 */
	protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
		// TODO Auto-generated method stub

	}

	/**
	 * 更换注册手机号码回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 */
	protected void didChangeUserPhone(int error, String errorMessage) {
		// TODO Auto-generated method stub

	}

	/**
	 * 更换密码回调接口
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 */
	protected void didChangeUserPassword(int error, String errorMessage) {
		// TODO Auto-generated method stub

	}

	/**
	 * 更换注册邮箱
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 */
	protected void didChangeUserEmail(int error, String errorMessage) {
		// TODO Auto-generated method stub

	}

	/**
	 * 绑定设备结果回调
	 * 
	 * @param error
	 *            结果代码
	 * @param errorMessage
	 *            错误信息
	 * @param did
	 *            设备注册id
	 */
	protected void didBindDevice(int error, String errorMessage, String did) {
		// TODO Auto-generated method stub

	}

	/**
	 * 接收指令回调
	 * <p/>
	 * sdk接收到模块传入的数据回调该接口
	 * 
	 * @param device
	 *            设备对象
	 * @param dataMap
	 *            json数据表
	 * @param result
	 *            状态代码
	 */
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		// TODO Auto-generated method stub

	}

	/**
	 * 登陆设备结果回调接口
	 * 
	 * @param device
	 *            设备对象
	 * @param result
	 *            状态代码
	 */
	protected void didLogin(XPGWifiDevice device, int result) {
		// TODO Auto-generated method stub

	}

	/**
	 * 断开连接回调接口
	 * 
	 * @param device
	 *            设备对象
	 */
	protected void didDisconnected(XPGWifiDevice device) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设备上下线通知
	 * 
	 * @param device
	 *            设备对象
	 * @param isOnline
	 *            上下线状态
	 */
	protected void didDeviceOnline(XPGWifiDevice device, boolean isOnline) {
		// TODO Auto-generated method stub

	}

	/**
	 * 通过did和mac在列表寻找对应的device.
	 * 
	 * @param mac
	 *            the mac
	 * @param did
	 *            the did
	 * @return the XPG wifi device
	 */
	public static XPGWifiDevice findDeviceByMac(String mac, String did) {
		XPGWifiDevice xpgdevice = null;
		Log.i("count", BaseActivity.deviceslist.size() + "");
		for (int i = 0; i < BaseActivity.deviceslist.size(); i++) {
			XPGWifiDevice device = deviceslist.get(i);
			if (device != null) {
				Log.i("deivcemac", device.getMacAddress());
				if (device != null && device.getMacAddress().equals(mac)
						&& device.getDid().equals(did)) {
					xpgdevice = device;
					break;
				}
			}

		}

		return xpgdevice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	public void onResume() {
		super.onResume();
		mCenter.getXPGWifiSDK().setListener(sdkListener);
		Log.i("Base", this.getClass().getSimpleName() + "***sdkListener="
				+ sdkListener.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	public void onPause() {
		super.onPause();

	}

	/**
	 * Gets the online list.
	 * 
	 * @return the online list
	 */
	protected void initBindList() {
		if (bindlist != null && bindlist.size() > 0)
			bindlist.clear();
		for (XPGWifiDevice xpgDevice : deviceslist) {
			if (xpgDevice.isBind(setmanager.getUid())) {
				bindlist.add(xpgDevice);
			}
		}
	}
}
