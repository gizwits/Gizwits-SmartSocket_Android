package com.xpg.appbase.database;

public class DBConstants {
	public static final String DB_NAME = "xpgybase.db";
	private static final int DB_VERSION = 1;
	public static final String USER_TABLE = "user_table";
	public static final String DEVICE_TABLE = "device_table";
	public static final String ALARM_TABLE = "alarm_table";
	
	public static final String ALARM_TABLE_CREATE = "create table if not exists "
			+ ALARM_TABLE
			+ "("
			+ " aID integer primary key autoincrement,"
			+ " code varchar(20)," + " msg varchar(50)," + " time varchar(30))";
	
	public static final String DEVICE_TABLE_CREATE = "create table if not exists "
			+ DEVICE_TABLE
			+ "("
			+ " device_id integer primary key autoincrement,"
			+ " mac varchar(50),"
			+ " ip varchar(20),"
			+ " did varchar(20),"
			+ "passcode varchar(20),name varchar(50))";
	

	public static final String DBUSER_TABLE_CREATE = "create table if not exists "
			+ USER_TABLE
			+ " ("
			+ " user_id integer primary key autoincrement,"
			+ " object_id varchar(20)," + " user_name varchar(20))";
	
	public static String[] getCreateStr() {

		String[] AllCreate = new String[] { DEVICE_TABLE_CREATE,
				DBUSER_TABLE_CREATE, ALARM_TABLE_CREATE};
		return AllCreate;
	}
}
