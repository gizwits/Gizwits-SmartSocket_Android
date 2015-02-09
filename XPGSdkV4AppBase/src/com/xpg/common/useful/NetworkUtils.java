package com.xpg.common.useful;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * 
 * <P>
 * 网络连接判断
 * <P>
 * 
 * @author StephenC
 * @version 1.00
 */
public class NetworkUtils {

	/**
	 * 移动网络是否可用
	 * 
	 * @param context
	 *            上下文
	 * @return   移动网络是否可用
	 * 
	 * */
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * WIFI网络是否可用
	 * 
	 * @param context
	 *            上下文
	 * @return   WIFI网络是否可用
	 * 
	 * */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 当前网络是否可用
	 * 
	 * @param context
	 *            上下文
	 * @return   当前网络是否可用
	 * 
	 * */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}
	
 	/**
 	 * 获取当前WIFI的SSID.
 	 *
 	 * @param context 上下文
 	 * @return ssid
 	 * 
 	 * *
 	 */
	 public static String getCurentWifiSSID(Context context){
		 String ssid = "";
		 if(context!=null){
			 WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
			 WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			 ssid = wifiInfo.getSSID();
			 if (ssid.substring(0, 1).equals("\"")
						&& ssid.substring(ssid.length() - 1).equals("\"")) {
					ssid = ssid.substring(1, ssid.length() - 1);
				}
		 }
		 return ssid;
	 }
}
