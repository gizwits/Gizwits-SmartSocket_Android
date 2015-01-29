/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SearchListAdapter.java
 * Package Name:com.gizwits.framework.adapter
 * Date:2015-1-27 14:46:58
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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.sdk.SettingManager;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class SearchListAdapter. <br/>
 * 搜索设备列表适配器 <br/>
 * date: 2015-1-27 14:46:58 <br/>
 * 
 * @author Lien
 */
public class SearchListAdapter extends BaseAdapter {

	/** The inflater. */
	private LayoutInflater inflater;

	/** The set manager. */
	private SettingManager setManager;

	/** The context. */
	private Context context;

	/** check isWifiDevice 0:wifidevice 1:device. */
	private int i = 0;

	/** The current devices. */
	private List<XPGWifiDevice> currentDevices;

	/**
	 * 设备列表数据适配器构造方法(Wifi查询数据列表).
	 * 
	 * @param c
	 *            上下文环境
	 * @param list
	 *            设备列表
	 */
	public SearchListAdapter(Context c, List<XPGWifiDevice> list) {
		this.i = 0;
		this.context = c;
		this.inflater = LayoutInflater.from(context);
		setManager = new SettingManager(c);
		changedatas(list);
	}

	/**
	 * Changedatas.
	 * 
	 * @param devices
	 *            the devices
	 */
	private void changedatas(List<XPGWifiDevice> devices) {
		if (currentDevices != null && currentDevices.size() > 0) {
			currentDevices.clear();
		} else {
			currentDevices = new ArrayList<XPGWifiDevice>();
		}
		currentDevices = devices;
		// for (XPGWifiDevice device : devices) {
		//
		// if (device.isLAN() && !device.isBind(setManager.getUid())) {
		// currentDevices.add(device);
		// }
		// }

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return currentDevices.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return currentDevices.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * Gets the device.
	 * 
	 * @param position
	 *            the position
	 * @return the device
	 */
	public XPGWifiDevice getDevice(int position) {
		return currentDevices.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_list_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		XPGWifiDevice device = currentDevices.get(position);
		holder.tvName.setText(device.getProductName()
				+ device.getMacAddress().substring(
						device.getMacAddress().length() - 4));
		return convertView;
	}

	/**
	 * 
	 * ClassName: Class ViewHolder. <br/>
	 * <br/>
	 * date: 2015-1-27 14:46:58 <br/>
	 * 
	 * @author Lien
	 */
	private static class ViewHolder {

		/** The tv name. */
		TextView tvName;
	}

}
