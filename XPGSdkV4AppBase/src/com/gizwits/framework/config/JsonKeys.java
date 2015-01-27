/**
 * Project Name:XPGSdkV4AppBase
 * File Name:JsonKeys.java
 * Package Name:com.gizwits.framework.config
 * Date:2015-1-22 18:22:20
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
package com.gizwits.framework.config;

/**
 * 
 * ClassName: Class JsonKeys. <br/>
 * Json对应字段表<br/>
 * 
 * @author Lien
 */
public class JsonKeys {

	/** 产品名. */
	public final static String PRODUCT_NAME = "智能云空调";

	/** 实体字段名，代表对应的项目. */
	public final static String KEY_ACTION = "entity0";

	/** 开关. */
	public final static String ON_OFF = "switch";

	/** 定时开机. */
	public final static String TIME_ON = "on_timing";

	/** 定时关机. */
	public final static String TIME_OFF = "off_timing";
	/**
	 * 模式 0.制冷, 1.送风, 2.除湿, 3.自动
	 */
	public final static String MODE = "mode";

	/** 设定温度 16 - 30. */
	public final static String SET_TEMP = "set_temp";
	/**
	 * 风速 0.低风, 1.中风, 2.高风
	 */
	public final static String FAN_SPEED = "fan_speed";

	/** 摆风. */
	public final static String FAN_SHAKE = "fan_swing";

	/** 室内温度 -10 - 35. */
	public final static String ROOM_TEMP = "room_temp";

	/** 停机报警. */
	public final static String ALARM_SHUTDOWM = "alert_shutdown";

	/** 水满报警. */
	public final static String ALARM_FULL = "alert_full";

	/** 室温故障. */
	public final static String FAULT_ROOMTEMP = "fault_roomtemp";
}
