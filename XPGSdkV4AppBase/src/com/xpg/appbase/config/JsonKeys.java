package com.xpg.appbase.config;

public class JsonKeys {
    /**
     * 产品名
     */
    public final static String PRODUCT_NAME = "智能云空调";

    /** 实体字段名，代表对应的项目. */
    public final static String KEY_ACTION = "entity0";
    /**
     * 开关
     */
    public final static String ON_OFF = "switch";
    /**
     * 定时开机
     */
    public final static String TIME_ON = "on_timing";
    /**
     * 定时关机
     */
    public final static String TIME_OFF = "off_timing";
    /**
     * 模式
     * 0.制冷, 1.送风, 2.除湿, 3.自动
     */
    public final static String MODE = "mode";
    /**
     * 设定温度
     * 16 - 30
     */
    public final static String SET_TEMP = "set_temp";
    /**
     * 风速
     * 0.低风, 1.中风, 2.高风
     */
    public final static String FAN_SPEED = "fan_speed";
    /**
     * 摆风
     */
    public final static String FAN_SHAKE = "fan_swing";
    /**
     * 室内温度
     *  -10 - 35
     */
    public final static String ROOM_TEMP = "room_temp";
    /**
     * 停机报警
     */
    public final static String ALARM_SHUTDOWM = "alert_shutdown";
    /**
     * 水满报警
     */
    public final static String ALARM_FULL = "alert_full";
    /**
     * 室温故障
     */
    public final static String FAULT_ROOMTEMP = "fault_roomtemp";
}
