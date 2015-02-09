package com.gizwits.framework.adapter;

import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WeekRepeatAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mList = { "一", "二", "三", "四", "五", "六", "日" };

	public WeekRepeatAdapter(Context ctx) {
		this.mContext = ctx;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mList.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return mList[arg0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int line = DensityUtil.dip2px(mContext, 50);
		int px = line * getCount();
		parent.setLayoutParams(new android.widget.RelativeLayout.LayoutParams(
				px, line));

		Button mButton;
		if (convertView == null) {
			mButton = new Button(mContext);
			LayoutParams mLayoutParams=new LayoutParams(line,line);
			mButton.setLayoutParams(mLayoutParams);
			convertView = inflater.inflate(R.layout.search_list_item, null);
			convertView.setTag(mButton);
		} else {
			mButton = (Button) convertView.getTag();
		}

		return null;
	}

}
