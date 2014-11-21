package com.xpg.appbase.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;

/**
 * 
 * SharePreference处理类
 * 
 * @author Sunny Ding
 * */
public class SettingManager {
	SharedPreferences spf;
	private Context c;
	
//=================================================================
//	  
//	  SharePreference文件中的变量名字列表
//	  
//=================================================================
	
	// Sharepreference文件的名字
	private final String SHARE_PREFERENCES = "set";
	// 用户名
	private final String USER_NAME = "username";
	// 手机号码
	private final String PHONE_NUM = "phonenumber";
	// 匿名登录用户名
	private final String HIDE_UID = "hideuid";
	// 匿名登录密码
	private final String HIDE_TOKEN = "hidetoken";
	// 密码
	private final String PASSWORD = "password";
	//用户名
	private final String TOKEN = "token";
	//用户ID
	private final String UID = "uid";
	//服务器域名
	private final String SEVER_NAME="server";

	
	
	static String filter = "=====";

	public SettingManager(Context c) {
		this.c = c;
		spf = c.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
	}

	/**
	 * ANDROID_ID是设备第一次启动时产生和存储的64bit的一个数，当设备被恢复出厂设置后该数重置
	 * ANDROID_ID并不唯一
	 * */
	public String getPhoneId() {
		String android_id = Secure.getString(c.getContentResolver(),
				Secure.ANDROID_ID);
		return android_id;
	}
	/**
	 *set一堆ProductKey在SharePreference的一个变量下
	 *
	 * */
	public void DownLoadProduct_key(String produck_key) {
		String allkeys = spf.getString("keys", "");
		if (allkeys.contains(produck_key)) {
			return;
		} else {
			synchronized (spf) {
				Log.i("add_poduct_key_in", produck_key);
				spf.edit().putString("keys", allkeys + produck_key + filter)
						.commit();
			}
		}

	}
	/**
	 *在SharePreference的一个变量下的一堆ProductKey中获取第一个ProductKey
	 *
	 * */
	public String getDownLoadProduct_key() {
		String allkeys = spf.getString("keys", "");
		String[] keys = allkeys.split(filter);
		if (!keys[0].equals("")) {
			String newkeys = allkeys.replace(keys[0] + filter, "");
			synchronized (spf) {
				Log.i("add_poduct_key", keys[0]);
				spf.edit().putString("keys", newkeys).commit();
			}

			return keys[0];
		} else {
			return null;
		}
	}
	/**
	 *SharePreference clean
	 *
	 * */
	public void clean() {
		setHideToken("");
		setHideUid("");
		setUid("");
		setToken("");
		setPhoneNumber("");
		setPassword("");
		setUserName("");
	}

	public void setUserName(String name) {
		spf.edit().putString(USER_NAME, name).commit();

	}

	public String getUserName() {
		return spf.getString(USER_NAME, "");
	}

	public void setPhoneNumber(String phoneNumber) {
		spf.edit().putString(PHONE_NUM, phoneNumber).commit();
	}

	public String getPhoneNumber() {
		return spf.getString(PHONE_NUM, "");
	}

	public void setHideUid(String uid) {
		spf.edit().putString(HIDE_UID, uid).commit();
	}

	public String getHideUid() {
		return spf.getString(HIDE_UID, "");
	}

	public void setHideToken(String token) {
		spf.edit().putString(HIDE_TOKEN, token).commit();
	}

	public String getHideToken() {
		return spf.getString(HIDE_TOKEN, "");
	}

	public void setPassword(String psw) {
		spf.edit().putString(PASSWORD, psw).commit();
	}

	public String getPassword() {
		return spf.getString(PASSWORD, "");
	}

	public void setToken(String token) {
		spf.edit().putString(TOKEN, token).commit();
	}

	public String getToken() {
		return spf.getString(TOKEN, "");
	}

	public void setUid(String uid) {
		spf.edit().putString(UID, uid).commit();
	}

	public String getUid() {
		return spf.getString(UID, "");
	}
	public String getServerName() {
		return spf.getString(SEVER_NAME, "api.gizwits.com");
	}

	public void setServerName(String server) {
		spf.edit().putString(SEVER_NAME, server).commit();
	}

}
