package com.xpg.appbase.database;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName StringFactory.java
 * @Description TODO
 * @author Derek zhang dzhang@xtremeprog.com
 * @date 2012-7-31
 * 
 */
public class StringFactory {

	public static String simpleToString(Object object){
		
		StringBuffer buffer = new StringBuffer();
		Class<? extends Object> oClass = object.getClass();
		Field[] declaredFields = oClass.getDeclaredFields();
		Method[] declaredMethods = oClass.getDeclaredMethods();
		
		try {
			for (Field field : declaredFields) {
				String fieldName = field.getName();
				buffer.append(fieldName).append(" : ");
				String setMethodName = new StringBuffer().append("get").append(fieldName).toString();
				Method method = findMethod(setMethodName,declaredMethods);
//				System.out.println("method name: "+ method.getName());
				String parameType = method.getReturnType().getSimpleName();
//				System.out.println("parame name : "+ parameType);
				Object value = method.invoke(object, null);
				buffer.append(String.valueOf(value)).append(" ; ");
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
		
		return buffer.toString();
	}
	
	public static Method findMethod(String setMethodName,
			Method[] declaredMethods) {
		Method result = null ;
		
		for (Method method : declaredMethods) {
			if(method.getName().equalsIgnoreCase(setMethodName)){
				result = method ;
			}
		}
		
		return result ;
	}
	
}
