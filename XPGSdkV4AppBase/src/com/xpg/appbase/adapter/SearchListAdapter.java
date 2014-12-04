package com.xpg.appbase.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class SearchListAdapter extends BaseAdapter {

	private LayoutInflater inflater;

	/** The devicelist. */
	private List<XPGWifiDevice> devicelist;

	/** The context. */
	private Context context;

	/**
	 * 设备列表数据适配器构造方法.
	 * 
	 * @param c
	 *            上下文环境
	 * @param list
	 *            设备列表
	 */
	public SearchListAdapter(Context c, List<XPGWifiDevice> list) {
		this.devicelist = list;
		this.context = c;
		this.inflater =  LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return devicelist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		XPGWifiDevice device = devicelist.get(position);
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_list_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText("智能空调" + device.GetMacAddress());
		return convertView;
	}

	private static class ViewHolder {

		TextView tvName;
	}

}
