package com.gizwits.aircondition.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 历史程序栈
 * 
 * @author Lien Li
 * @version 1.00
 */
public class Historys
{
	private static List<Activity> activityList = null;
	private static Historys instance = null;
	
	/** 返回该类的一个实例 */
	private static Historys getInstance()
	{
		if(instance == null)
		{
			synchronized ( Historys.class )
			{
				if(instance == null)
					instance = new Historys();
			}
		}
		return instance;
	}
	
	public Historys()
	{
		activityList = new ArrayList<Activity>();
	}
	
	/**
	 * 将一个程序Activity放入历史栈中
	 * @param activity
	 */
	public static void put(final Activity activity)
	{
		if(activityList == null)
			getInstance();
		SystemResource.getExecutorService().execute( new Runnable()
		{
			public void run()
			{
				activityList.add( activity );
				List<Activity> nos = new ArrayList<Activity>();
				int size = activityList.size();
				for(int i = 0; i < size; i++)
				{
					Activity act = activityList.get( i );
					if(act != null && act.isFinishing())
					{
						nos.add( act );
					}
				}
				
				activityList.removeAll( nos );
				nos = null;
			}
		});
	}
	
	/**
	 * 结束所有在历史栈中的程序，并清空历史栈
	 */
	public static void exit()
	{
		if(activityList == null)
			return;
		int size = activityList.size();
		for(int i = 0; i < size; i++)
		{
			Activity act = activityList.get( i );
			finish( act );
			act = null;
		}
		activityList.clear();
	
		android.os.Process.killProcess(android.os.Process.myPid()); 
		System.exit(0);
	}
	
	private static void finish(Activity activity)
	{
		if(activity == null)
			return ;
		try
		{
			activity.finish();
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
		}
	}
	
	/**
	 * 回收该类的数据资源
	 */
	public static void recycle()
	{
		if(activityList != null)
		{
			activityList.clear();
			activityList = null;
		}
		instance = null;
	}
}
