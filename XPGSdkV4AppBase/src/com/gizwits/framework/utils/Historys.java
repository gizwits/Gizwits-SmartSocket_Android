/**
 * Project Name:XPGSdkV4AppBase
 * File Name:Historys.java
 * Package Name:com.gizwits.framework.utils
 * Date:2015-1-27 14:47:32
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
package com.gizwits.framework.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

// TODO: Auto-generated Javadoc
/**
 * 历史程序栈.
 *
 * @author Lien Li
 * @version 1.00
 */
public class Historys
{
	
	/** The activity list. */
	private static List<Activity> activityList = null;
	
	/** The instance. */
	private static Historys instance = null;
	
	/**
	 *  返回该类的一个实例.
	 *
	 * @return single instance of Historys
	 */
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
	
	/**
	 * Instantiates a new historys.
	 */
	public Historys()
	{
		activityList = new ArrayList<Activity>();
	}
	
	/**
	 * 将一个程序Activity放入历史栈中.
	 *
	 * @param activity the activity
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
	 * 结束所有在历史栈中的程序，并清空历史栈.
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
	
	/**
	 * Finish.
	 *
	 * @param activity the activity
	 */
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
	 * 回收该类的数据资源.
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
