package com.gizwits.framework.adapter;

import java.util.ArrayList;

import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.powersocket.R;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class WeekRepeatAdapter extends BaseAdapter {
	private Context mContext;
	private String[] mList = { "一", "二", "三", "四", "五", "六", "日" };
	private ArrayList<Boolean> mSelectList;

	public WeekRepeatAdapter(Context ctx, ArrayList<Boolean> select) {
		this.mContext = ctx;
		this.mSelectList = select;
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
		// int px = line * getCount();
		// parent.setLayoutParams(new
		// android.widget.RelativeLayout.LayoutParams(
		// px, line));

		TextView mTextView;
		if (convertView == null) {
			mTextView = new Button(mContext);
			convertView = mTextView;
			LayoutParams mLayoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, line);
			mTextView.setGravity(Gravity.CENTER);
			mTextView.setLayoutParams(mLayoutParams);
			convertView.setTag(mTextView);
		} else {
			mTextView = (Button) convertView.getTag();
		}

		mTextView.setText(mList[position]);
		
		int backGroundRes = 0;
		boolean isSelected=mSelectList.get(position);
		int textColor=0;
		if (position == 0) {
			if(!isSelected){
				backGroundRes=R.drawable.date_select_left;
			}else{
				backGroundRes=R.drawable.date_select_left2;
			}
		} else if (position == 6) {
			if(!isSelected){
				backGroundRes=R.drawable.date_select_right;
			}else{
				backGroundRes=R.drawable.date_select_right2;
			}
		} else {
			if(!isSelected){
				backGroundRes=R.drawable.date_select_mid;
			}else{
				backGroundRes=R.drawable.date_select_mid2;
			}
		}
		mTextView.setBackgroundResource(backGroundRes);
		
		if(isSelected){
			textColor=R.color.white;
		}else{
			textColor=R.color.text_blue;
		}
		mTextView.setTextColor(textColor);
		return mTextView;
	}

}
