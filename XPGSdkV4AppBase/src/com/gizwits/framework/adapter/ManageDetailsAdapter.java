package com.gizwits.framework.adapter;

import com.gizwits.framework.config.DeviceDetails;
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
	
	private int Selected=0;
	
	public ManageDetailsAdapter(Context c){
		this.context = c;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return 9;
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
		DeviceDetails mDetails=DeviceDetails.findByNum(position+1);
		if(position==Selected){
			holder.ivDetails.setBackgroundColor(context.getResources().getColor(R.color.background_blue));
			holder.ivDetails.setImageResource(mDetails.getResSelected());
		}else{
			holder.ivDetails.setBackgroundColor(context.getResources().getColor(R.color.white));
			holder.ivDetails.setImageResource(mDetails.getRes());
		}
		
		holder.tvDetails.setText(mDetails.getName());
		
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
