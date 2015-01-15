package com.gizwits.framework.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.sdk.SettingManager;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class SearchListAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	private SettingManager setManager;

	/** The context. */
	private Context context;

	/** check isWifiDevice 0:wifidevice 1:device */
	private int i = 0;

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

	private void changedatas(List<XPGWifiDevice> devices) {
		if (currentDevices != null && currentDevices.size() > 0) {
			currentDevices.clear();
		} else {
			currentDevices = new ArrayList<XPGWifiDevice>();
		}
		for (XPGWifiDevice device : devices) {

			if (device.isLAN() && !device.isBind(setManager.getUid())) {
				currentDevices.add(device);
			}
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return currentDevices.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return currentDevices.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public XPGWifiDevice getDevice(int position) {
		return currentDevices.get(position);
	}

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
		holder.tvName.setText("智能空调"
				+ device.getMacAddress().substring(
						device.getMacAddress().length() - 4,
						device.getMacAddress().length()));
		return convertView;
	}

	private static class ViewHolder {

		TextView tvName;
	}

}
