package com.gizwits.aircondition.sdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.util.Log;

/**
 * SharePreference处理类
 * 
 * @author Sunny Ding
 */
public class SettingManager {

	SharedPreferences spf;
	private Context c;

	// =================================================================
	//
	// SharePreference文件中的变量名字列表
	//
	// =================================================================

	// Sharepreference文件的名字
	private final String SHARE_PREFERENCES = "set";
	// 用户名
	private final String USER_NAME = "username";
	// 手机号码
	private final String PHONE_NUM = "phonenumber";
	// 密码
	private final String PASSWORD = "password";
	// 用户名
	private final String TOKEN = "token";
	// 用户ID
	private final String UID = "uid";

	private final String UNIT = "unit";

	static String filter = "=====";

	public SettingManager(Context c) {
		this.c = c;
		spf = c.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
	}

	/**
	 * SharePreference clean
	 */
	public void clean() {
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

	public void getUnit(boolean isC) {
		spf.edit().putBoolean(UNIT, isC).commit();
	}

	public boolean getUnit() {
		return spf.getBoolean(UNIT, true);
	}
}
