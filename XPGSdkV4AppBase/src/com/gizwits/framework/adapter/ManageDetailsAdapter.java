package com.gizwits.framework.adapter;

import com.gizwits.powersocket.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ManageDetailsAdapter extends BaseAdapter{

	/** The inflater. */
	private LayoutInflater inflater;

	/** The context. */
	private Context context;
	
	private String[] name={"电视","插座","空调","冰箱","洗衣机","DVD","音响","厨具","风扇"};
	
	private int [] resSelected={R.drawable.head_icon1_down,R.drawable.head_icon2_down
			,R.drawable.head_icon3_down,R.drawable.head_icon4_down,R.drawable.head_icon5_down
			,R.drawable.head_icon6_down,R.drawable.head_icon7_down,R.drawable.head_icon8_down
			,R.drawable.head_icon9_down};
	
	private int [] res={R.drawable.head_icon1,R.drawable.head_icon2,R.drawable.head_icon3
			,R.drawable.head_icon4,R.drawable.head_icon5,R.drawable.head_icon6,R.drawable.head_icon7
			,R.drawable.head_icon8,R.drawable.head_icon9};
	
	private int Selected=0;
	
	public ManageDetailsAdapter(Context c){
		this.context = c;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return name.length;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.manage_details_item, null);
			holder.tvDetails = (TextView) convertView.findViewById(R.id.tvDetails);
			holder.ivDetails = (ImageView) convertView.findViewById(R.id.ivDetails);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(position==Selected){
			holder.ivDetails.setBackgroundColor(context.getResources().getColor(R.color.background_blue));
			holder.ivDetails.setImageResource(resSelected[position]);
		}else{
			holder.ivDetails.setBackgroundColor(context.getResources().getColor(R.color.white));
			holder.ivDetails.setImageResource(res[position]);
		}
		
		holder.tvDetails.setText(name[position]);
		
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
		TextView tvDetails;
		
		/** The iv arrow. */
		ImageView ivDetails;
	}

	public int getSelected() {
		return Selected;
	}

	public void setSelected(int selected) {
		Selected = selected;
	}

	
}
