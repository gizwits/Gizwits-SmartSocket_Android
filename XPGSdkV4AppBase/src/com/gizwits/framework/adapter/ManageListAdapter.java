/**
 * Project Name:XPGSdkV4AppBase
 * File Name:ManageListAdapter.java
 * Package Name:com.gizwits.framework.adapter
 * Date:2015-1-27 14:46:55
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
import android.widget.ImageView;
import android.widget.TextView;

import com.xtremeprog.xpgconnect.XPGWifiDevice;
import com.xpg.common.useful.StringUtils;
import com.gizwits.powersocket.R;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class ManageListAdapter. <br/> 
 * 设备管理列表适配器
 * <br/>
 * date: 2015-1-27 14:46:55 <br/> 
 *
 * @author Lien
 */
public class ManageListAdapter extends BaseAdapter {

	/** The inflater. */
	private LayoutInflater inflater;

	/** The wifidevicelist. */
	private List<XPGWifiDevice> devicelist;


	/** The context. */
	private Context context;

	/**
	 * 设备列表数据适配器构造方法(Wifi查询数据列表).
	 * 
	 * @param c
	 *            上下文环境
	 * @param list
	 *            设备列表
	 */
	public ManageListAdapter(Context c, List<XPGWifiDevice> list) {
		this.devicelist = list;
		this.context = c;
		this.inflater = LayoutInflater.from(context);
	}


	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return devicelist.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return devicelist.get(position);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_list_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.ivType = (ImageView) convertView.findViewById(R.id.icon);
			holder.ivArrow = (ImageView) convertView.findViewById(R.id.ivArrow);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		XPGWifiDevice device = devicelist.get(position);
		
		if(StringUtils.isEmpty(device.getRemark()))
		{
			String macAddress=device.getMacAddress();
			int size=macAddress.length();
			holder.tvName.setText(device.getProductName() + macAddress.substring(size-4, size));
		}else
		{
			holder.tvName.setText(device.getRemark());
		}
		
		if (device.isLAN() || device.isOnline()) {
			holder.tvName.setTextColor(context.getResources().getColor(
					R.color.text_blue));
			holder.ivArrow.setVisibility(View.VISIBLE);
		} else {
			holder.tvName.setTextColor(context.getResources().getColor(
					R.color.text_gray));
			holder.ivArrow.setVisibility(View.GONE);
		}
		return convertView;
	}

	/**
	 *  
	 * ClassName: Class ViewHolder. <br/> 
	 * <br/>
	 * date: 2015-1-27 14:46:55 <br/> 
	 *
	 * @author Lien
	 */
	private static class ViewHolder {

		/** The tv name. */
		TextView tvName;
		
		/** The iv type. */
		ImageView ivType;
		
		/** The iv arrow. */
		ImageView ivArrow;
	}

}
