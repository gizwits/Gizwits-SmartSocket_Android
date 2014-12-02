package com.xpg.appbase.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class BaseDao {
	private SQLiteDatabase db;
	private final Context context;
	private DBOpenHelper dbOpenHelper;
	private static final String logTag = "dbData" ;
	
	public BaseDao(Context _context) {
		this.context = _context;
	}

	/**
	 * 打开数据库
	 * @param dbName 数据库名
	 * @param createString 建表语句
	 * @param tableName 表名
	 * @throws SQLiteException
	 */
	public void open(String dbName, String[] createString ,String tableName) throws SQLiteException {
		Log.i(logTag, "dbName: "+ dbName + " createString: "+ createString + " tableName: "+ tableName);
		dbOpenHelper = new DBOpenHelper(context, dbName, createString , tableName);
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}

	/**
	 * 插入数据
	 * @param object 数据对象
	 * @param except 排除插入的key
	 * @return
	 */
	public long insert(String DB_TABLE , Object object , String except) {
		ContentValues newValues = new ContentValues();

		fillContentValuse(object, newValues,except);

		return db.insert(DB_TABLE, null, newValues);
	}

	/**
	 * 进行数据的解析
	 * @param object
	 * @param newValues
	 * @param except
	 */
	public void fillContentValuse(Object object, ContentValues newValues, String except) {
		Class<? extends Object> oClass = object.getClass();
		Field[] declaredFields = oClass.getDeclaredFields();
		Method[] declaredMethods = oClass.getDeclaredMethods();
		try {
			for (Field field : declaredFields) {
				String fieldName = field.getName();
				if(except != null && fieldName.equalsIgnoreCase(except)){
					continue;
				}
				String setMethodName;
				if(field.getType().getName().equalsIgnoreCase("boolean") || field.getType().getName().equalsIgnoreCase("Long"))
				{
					setMethodName = new StringBuffer().append("is")
							.append(fieldName).toString();
				}
				else
				{
					setMethodName = new StringBuffer().append("get")
							.append(fieldName).toString();
				}
				Method method = StringFactory.findMethod(setMethodName,
						declaredMethods);
				String parameType = method.getReturnType().getSimpleName();
				Object value = method.invoke(object, new Object[]{});
				
				setDifferentParameType(fieldName, parameType, value, newValues);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清除数据表的内容
	 * @return
	 */
	public long deleteAllData(String DB_TABLE) {
		return db.delete(DB_TABLE, null, null);
	}

	/**
	 * 删除数据
	 * @param key
	 * @param id
	 * @return
	 */
	public long deleteOneData(String DB_TABLE, String key, long id) {
		return db.delete(DB_TABLE, key + "=" + id, null);
	}

	public long updateOneData(String DB_TABLE , String key, long id, Object object) {
		ContentValues updateValues = new ContentValues();

		fillContentValuse(object, updateValues,null);

		return db.update(DB_TABLE, updateValues, key + "=" + id, null);
	}
	
	private void setDifferentParameType(String keyName, String parameType,
			Object value, ContentValues newValues) {
		if (parameType.equalsIgnoreCase("string")) {
			newValues.put(keyName, String.valueOf(value));
		}
		if (parameType.equalsIgnoreCase("int") || parameType.equalsIgnoreCase("Integer") ) {
			newValues.put(keyName, (Integer) value);
		}
		if (parameType.equalsIgnoreCase("float") || parameType.equalsIgnoreCase("Float") ) {
			newValues.put(keyName, (Float) value);
		}
		if (parameType.equalsIgnoreCase("double") || parameType.equalsIgnoreCase("Double") ) {
			newValues.put(keyName, (Double) value);
		}
		if (parameType.equalsIgnoreCase("long") || parameType.equalsIgnoreCase("Long") ) {
			newValues.put(keyName, (Long) value); 
		}
		if (parameType.equalsIgnoreCase("boolen") || parameType.equalsIgnoreCase("Boolean") ) {
			newValues.put(keyName, (Boolean) value); 
		}
//		Log.i( logTag, "value: "+  String.valueOf(value));
	}

	
	 public ArrayList<Object> queryAllData(String DB_TABLE , Class<?> targetClass) {
		 String[] keys = getTableFieldName(targetClass);
		 Cursor results = db.query(DB_TABLE, keys ,null, null, null, null, null); 
		 ArrayList<Object> list = ConvertToPeople(results , targetClass);
		 results.close();
		 return list;
	 }
	
	 public ArrayList<Object> queryOneData(String DB_TABLE, Class<?> targetClass, String condition) {
		String[] keys = getTableFieldName(targetClass);
		Cursor results = db.query(DB_TABLE, keys, condition , null, null, null, null);
		ArrayList<Object> list = ConvertToPeople(results , targetClass);
		results.close();
		return list;
	 }
	 
		/**
		 * 获得数据库对象
		 * @return
		 */
		public SQLiteDatabase getDB(){
			return db;
		}

	
	private String[] getTableFieldName(Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		String[] keys = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			keys[i] = fields[i].getName();
		 }
		return keys;
	}
	
	private ArrayList<Object> ConvertToPeople(Cursor cursor, Class<?> targetClass) {
		int resultCounts = cursor.getCount();
		if (resultCounts == 0 || !cursor.moveToFirst()) {
			return null;
		}
		
		ArrayList<Object> objectList = new ArrayList<Object>();
		Field[] fields = targetClass.getDeclaredFields();
		Method[] methods = targetClass.getDeclaredMethods();
		
		try {
			for (int i = 0; i < resultCounts; i++) {
				Object result = targetClass.newInstance();
				
				for (Field tempFields : fields) {
					int columnIndex = cursor.getColumnIndex(tempFields.getName());  
					Class<?> type = tempFields.getType();

					Object[] objects = new Object[1];
					
					if(type.getSimpleName().equalsIgnoreCase("String")){
						objects[0] = cursor.getString(columnIndex);
					}
					if(type.getSimpleName().equalsIgnoreCase("double") || type.getName().equalsIgnoreCase("Double")){
						objects[0]  = cursor.getDouble(columnIndex);
					}
					if(type.getSimpleName().equalsIgnoreCase("float") || type.getName().equalsIgnoreCase("Float")){
						objects[0]  = cursor.getFloat(columnIndex);
					}
					if(type.getSimpleName().equalsIgnoreCase("int") || type.getName().equalsIgnoreCase("Integer")){
						objects[0]  = cursor.getInt(columnIndex);
					}
					if(type.getSimpleName().equalsIgnoreCase("Long")|| type.getName().equalsIgnoreCase("long") ){
						objects[0] = cursor.getLong(columnIndex);
					}
					if(type.getSimpleName().equalsIgnoreCase("boolean") || type.getName().equalsIgnoreCase("Boolean")){
						int intresult = cursor.getInt(columnIndex);
						if(intresult == 0)
							objects[0] = false;
						else
							objects[0] = true;
					}
					
					String setMethodName = "set"+tempFields.getName();
					Method method = StringFactory.findMethod(setMethodName, methods);
					
					method.invoke(result, objects);
				}
				
				objectList.add(result);
				cursor.moveToNext();
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		Log.i( logTag, "list size :  "+ objectList.size() );
		return objectList;
	}

	/** Close the database */
	public void close() {
		if (db != null) {
			db.close();
			db = null;
		}
	}

}
