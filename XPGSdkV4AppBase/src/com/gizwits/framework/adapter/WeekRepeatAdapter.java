package com.gizwits.framework.adapter;

import java.util.ArrayList;

import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeekRepeatAdapter extends BaseAdapter {
	/** The String. */
	private String[] mList = { "一", "二", "三", "四", "五", "六", "日" };
	
	/** The ArrayList boolean Selected. */
	private ArrayList<Boolean> mSelectList;
	
	/** The inflater. */
	private LayoutInflater inflater;

	/**
	 * 日期选择适配器构造方法.
	 * 
	 * @param ctx
	 *            上下文环境
	 * @param list
	 *            设备列表
	 */
	public WeekRepeatAdapter(Context ctx, ArrayList<Boolean> select) {
		this.mSelectList = select;
		this.inflater = LayoutInflater.from(ctx);
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
		TextView mTextView;
		if (convertView == null) {
			convertView =  inflater.inflate(R.layout.item_date_selected, null);
		}
		
		mTextView=(TextView) convertView.findViewById(R.id.tvDate);
		mTextView.setText(mList[position]);
		
		//背景资源选择
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
		
		//字体颜色选择
		if(isSelected){
			textColor=R.color.white;
		}else{
			textColor=R.color.text_blue;
		}
		mTextView.setTextColor(textColor);
		return convertView;
	}

}
