package com.xpg.appbase.entity;

import java.io.Serializable;

public class DeviceAlarm implements Serializable {

	private static final long serialVersionUID = 1L;

	public int Id;

	/** @Fields did : 物理地址 */

	// 错误信息
	private String msg;

	private String time;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTime() {
		return time;
	}

	/**
	 * DateUtil.getDateCN(new Date())
	 * 
	 * @author Administrator
	 * @Title: setTime
	 * @Description: TODO
	 * @param @param time
	 * @return void
	 * @throws
	 */
	public void setTime(String time) {
		this.time = time;
	}

}
