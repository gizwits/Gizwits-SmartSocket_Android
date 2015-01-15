package com.gizwits.framework.entity;

import java.io.Serializable;

public class DeviceAlarm implements Serializable {

	private static final long serialVersionUID = 1L;

	private String time;
	private String desc;

	/**
	 * Instantiates a new control device.
	 * 
	 * @param titleName
	 *            the title name
	 */
	public DeviceAlarm(String time, String desc) {
		this.time = time;
		this.desc = desc;

	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
