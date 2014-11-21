package com.xpg.appbase.sdk;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

public class CmdCenter {
	private static XPGWifiSDK xpgWifiGCC;
	private static CmdCenter mCenter;
	private SettingManager mSettingManager;

	private CmdCenter(Context c) {
		if (mCenter == null) {
			init(c);
		}
	}

	public static CmdCenter getInstance(Context c) {
		if (mCenter == null) {
			mCenter = new CmdCenter(c);
		}
		return mCenter;
	}

	private void init(Context c) {
		mSettingManager = new SettingManager(c);

		xpgWifiGCC = XPGWifiSDK.sharedInstance();

	}

	/**
	 * Gets the XPG wifi sdk.
	 * 
	 * @return the XPG wifi sdk
	 */
	public XPGWifiSDK getXPGWifiSDK() {
		return xpgWifiGCC;
	}

	// =================================================================
	//
	// 关于账号的指令
	//
	// =================================================================
	/**
	 * 注册账号.
	 * 
	 * @param phone
	 *            注册手机号
	 * @param code
	 *            验证码
	 * @param password
	 *            注册密码
	 */
	public void cRegisterPhoneUser(String phone, String code, String password) {
		xpgWifiGCC.RegisterPhoneUser(phone, password, code);
	}

	/**
	 * 匿名登录
	 * <P>
	 * 如果一开始不需要直接注册账号，则需要进行匿名登录.
	 */
	public void cRegisterAnonymousUser() {
		xpgWifiGCC.RegisterAnonymousUser(mSettingManager.getPhoneId());
	}

	/**
	 * 账号注销.
	 */
	public void cLogout() {
		xpgWifiGCC.UserLogout(mSettingManager.getUid());
		xpgWifiGCC.UserLogout(mSettingManager.getHideUid());
		mSettingManager.clean();
	}

	/**
	 * 账号登陆.
	 * 
	 * @param name
	 *            用户名
	 * @param psw
	 *            密码
	 */
	public void cLogin(String name, String psw) {
		xpgWifiGCC.UserLogin(name, psw);
	}

	/**
	 * 忘记密码.
	 * 
	 * @param phone
	 *            手机号
	 * @param code
	 *            验证码
	 * @param password
	 *            密码
	 */
	public void cChangeUserPasswordWithCode(String phone, String code,
			String password) {
		xpgWifiGCC.changeUserPasswordWithCode(phone, code, password);
	}

	/**
	 * 请求向手机发送验证码.
	 * 
	 * @param phone
	 *            手机号
	 */
	public void cRequestSendVerifyCode(String phone) {
		xpgWifiGCC.RequestSendVerifyCode(phone);
	}

	/**
	 * 发送airlink广播，把需要连接的wifi的ssid和password发给模块。.
	 * 
	 * @param wifi
	 *            wifi名字
	 * @param password
	 *            wifi密码
	 */
	public void cSetAirLink(String wifi, String password) {
		xpgWifiGCC.SetAirLink(wifi, password);
	}

	/**
	 * 设置SSID.
	 * 
	 * @param ssid
	 *            the ssid
	 * @param psw
	 *            the psw
	 */
	public void cSetSSID(String ssid, String psw) {
		xpgWifiGCC.SetSSID(ssid, psw);
	}

	/**
	 * 发送发现设备广播
	 * */
	public void cDiscoverDevice() {
		xpgWifiGCC.DiscoverDevices();
	}

	/**
	 * 绑定后刷新设备列表，该方法会同时获取本地设备以及远程设备列表.
	 * 
	 * @param uid
	 *            用户名
	 * @param token
	 *            密码
	 */
	public void cGetBoundDevices(String uid, String token) {
		xpgWifiGCC.GetBoundDevices(uid, token);
	}

	/**
	 * 绑定设备.
	 * 
	 * @param uid
	 *            用户名
	 * @param token
	 *            密码
	 * @param did
	 *            did
	 * @param passcode
	 *            passcode
	 */
	public void cBindDevice(String uid, String token, String did,
			String passcode) {
		xpgWifiGCC.BindDevice(uid, token, did, passcode);
	}

	// =================================================================
	//
	// 关于控制设备的指令
	//
	// =================================================================

	/**
	 * 发送指令.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @param jsonsend
	 *            the jsonsend
	 */
	public void cWrite(XPGWifiDevice xpgWifiDevice, JSONObject jsonsend) {
		xpgWifiDevice.write(jsonsend.toString());
	}

	/**
	 * 获取设备状态.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @throws JSONException
	 *             the JSON exception
	 */
	public void cGetStatus(XPGWifiDevice xpgWifiDevice) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("cmd", 2);
		xpgWifiDevice.write(json.toString());
	}

	/**
	 * 断开连接.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	public void cDisconnect(XPGWifiDevice xpgWifiDevice) {
		xpgWifiDevice.Disconnect();
	}

	/**
	 * 获取Passcode.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @return the string
	 */
	public String cGetPasscode(XPGWifiDevice xpgWifiDevice) {
		return xpgWifiDevice.GetPasscode();
	}

	/**
	 * 解除绑定.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 * @param uid
	 *            the uid
	 * @param token
	 *            the token
	 */
	public void cUnbindDevice(XPGWifiDevice xpgWifiDevice, String uid,
			String token) {
		xpgWifiDevice.UnbindDevice(uid, token);
	}

}
