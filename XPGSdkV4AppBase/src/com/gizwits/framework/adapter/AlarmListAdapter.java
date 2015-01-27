/**
 * Project Name:XPGSdkV4AppBase
 * File Name:AlarmListAdapter.java
 * Package Name:com.gizwits.framework.adapter
 * Date:2015-1-27 14:46:48
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
package com.gizwits.framework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.entity.DeviceAlarm;

// TODO: Auto-generated Javadoc
/**
 * wifi热点列表数据适配器.
 *
 * @author Lien
 */
public class AlarmListAdapter extends BaseAdapter {

	/** The context. */
	private Context context;

	/** The inflater. */
	private LayoutInflater inflater;

	/** The list. */
	private List<DeviceAlarm> list;

	/**
	 * Instantiates a new wifi list adapter.
	 * 
	 * @param c
	 *            the c
	 * @param list
	 *            the list
	 */
	public AlarmListAdapter(Context c, List<DeviceAlarm> list) {
		this.context = c;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		DeviceAlarm deviceAlarm = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.alarm_list_item, null);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvTime.setText(deviceAlarm.getTime());
		holder.tvDesc.setText(deviceAlarm.getDesc());

		return convertView;
	}
	
	

	/**
	 *  
	 * ClassName: Class ViewHolder. <br/> 
	 * <br/>
	 * date: 2015-1-23 12:17:11 <br/> 
	 *
	 * @author Lien
	 */
	private static class ViewHolder {
		
		/** The tv time. */
		TextView tvTime;
		
		/** The tv desc. */
		TextView tvDesc;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
