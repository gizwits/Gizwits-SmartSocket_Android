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

/**
 *  
 * ClassName: Class ManageListAdapter. <br/> 
 * 日期选择适配器
 * <br/>
 * date: 2015-2-10 14:46:55 <br/> 
 *
 * @author Sunny
 */
public class ManageDetailsAdapter extends BaseAdapter{

	/** The inflater. */
	private LayoutInflater inflater;

	/** The context. */
	private Context context;
	
	/** The int Selected. */
	private int Selected=0;
	
	/**
	 * 日期选择适配器构造方法(Wifi查询数据列表).
	 * 
	 * @param c
	 *            上下文环境
	 */
	public ManageDetailsAdapter(Context c){
		this.context = c;
		this.inflater = LayoutInflater.from(context);
	}
	
	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return 9;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
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
