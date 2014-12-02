package com.xpg.appbase.entity;

import java.io.Serializable;

import android.util.Log;

import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class Device implements Serializable {


	/** The is online. */
	private boolean isOnline;

	/** mac地址. */
	private String mac;

	/** ip地址. */
	private String ip;

	/** 设备名字. */
	private String name;

	/** 设备did. */
	private String did;

	/** 设备的productKey. */
	private String productKey;

	/** 设备的passcode. */
	private String passcode;

	/** 设备的id */
	private int device_id;

	/**
	 * Instantiates a new device.
	 * 
	 * @param titleName
	 *            the title name
	 */
	public Device() {

	}

	/**
	 * 把设备的IP地址，MAC地址等信息从XPGWifiDevice存进这个类.
	 * 
	 * @param name
	 *            the name
	 * @param device
	 *            the device
	 */
	public Device(String name, XPGWifiDevice device) {
		if (device != null) {
			this.mac = device.GetMacAddress();
			this.did = device.GetDid();
			this.productKey = device.GetProductKey();
			isOnline = true;
			this.isOnline = device.IsOnline();
			this.ip = device.GetIPAddress();
			this.passcode = device.GetPasscode();
			Log.i("passcode", passcode);

		}
		this.name = device.GetProductName();
	}

	/**
	 * Gets the did.
	 * 
	 * @return the did
	 */
	public String getDid() {
		return did;
	}

	/**
	 * Sets the did.
	 * 
	 * @param did
	 *            the new did
	 */
	public void setDid(String did) {
		this.did = did;
	}

	/**
	 * Gets the passcode.
	 * 
	 * @return the passcode
	 */
	public String getPasscode() {
		return passcode;
	}

	/**
	 * Sets the passcode.
	 * 
	 * @param passcode
	 *            the new passcode
	 */
	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}


	/**
	 * Gets the mac.
	 * 
	 * @return the mac
	 */
	public String getMac() {
		return mac;
	}

	/**
	 * Sets the mac.
	 * 
	 * @param mac
	 *            the new mac
	 */
	public void setMac(String mac) {
		this.mac = mac;
	}

	/**
	 * Gets the product key.
	 * 
	 * @return the product key
	 */
	public String getProductKey() {
		return productKey;
	}

	/**
	 * Gets the ip.
	 * 
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 * 
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if is online.
	 * 
	 * @return true, if is online
	 */
	public boolean isOnline() {
		return isOnline;
	}

	/**
	 * Sets the online.
	 * 
	 * @param isOnline
	 *            the new online
	 */
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	/**
	 * Sets the product key.
	 * 
	 * @param productKey
	 *            the new product key
	 */
	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}



}
