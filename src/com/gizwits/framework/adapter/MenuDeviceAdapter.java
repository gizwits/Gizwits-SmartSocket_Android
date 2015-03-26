package com.gizwits.framework.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.framework.config.Configs;
import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

/**
 * 
 * ClassName: Class DeviceAdapter. <br/>
 * <br/>
 * date: 2015-1-27 14:44:48 <br/>
 * 
 * @author Lien
 */
public class MenuDeviceAdapter extends BaseAdapter {

	/** The inflater. */
	private LayoutInflater inflater;

	/** The choosed pos. */
	private int choosedPos = 0;

	/** The ctx. */
	private Context ctx;

	/** The wifidevicelist. */
	private List<XPGWifiDevice> devicelist ;

	/**
	 * Gets the choosed pos.
	 * 
	 * @return the choosed pos
	 */
	public int getChoosedPos() {
		return choosedPos;
	}

	/**
	 * Sets the choosed pos.
	 * 
	 * @param choosedPos
	 *            the new choosed pos
	 */
	public void setChoosedPos(int choosedPos) {
		this.choosedPos = choosedPos;
		notifyDataSetChanged();
	}

	/**
	 * Instantiates a new device adapter.
	 * 
	 * @param context
	 *            the context
	 * @param objects
	 *            the objects
	 */
	public MenuDeviceAdapter(Context context, List<XPGWifiDevice> objects) {
		ctx = context;
		inflater = LayoutInflater.from(context);
		devicelist=objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int px = DensityUtil.dip2px(ctx, getCount() * 50);
		parent.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, px));

		ViewHolder holder = null;
		XPGWifiDevice device = getItem(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.slibbar_item, null);
			holder = new ViewHolder();
			holder.device_checked_tv = (ImageView) convertView
					.findViewById(R.id.device_checked_tv);
			holder.deviceName_tv = (TextView) convertView
					.findViewById(R.id.deviceName_tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String name="";
		if (StringUtils.isEmpty(device.getRemark())) {
			String macAddress = device.getMacAddress();
			int size = macAddress.length();
			name=device.getProductName()
					+ macAddress.substring(size - 4, size);
		} else {
			name=device.getRemark();
		}
		name=StringUtils.getStrFomat(name, Configs.DEVICE_NAME_KEEP_LENGTH, true);
		holder.deviceName_tv.setText(name);

		if (getChoosedPos() == position) {
			holder.deviceName_tv.setSelected(true);
			holder.device_checked_tv.setVisibility(View.VISIBLE);
			;
		} else {
			holder.deviceName_tv.setSelected(false);
			holder.device_checked_tv.setVisibility(View.INVISIBLE);
			;
		}

		if (device.isOnline())
			holder.deviceName_tv.setTextColor(ctx.getResources().getColor(
					R.color.text_blue));
		else
			holder.deviceName_tv.setTextColor(ctx.getResources().getColor(
					R.color.text_gray));

		return convertView;

	}

	/**
	 * 
	 * ClassName: Class ViewHolder. <br/>
	 * <br/>
	 * date: 2015-1-27 14:44:48 <br/>
	 * 
	 * @author Lien
	 */
	private static class ViewHolder {

		/** The device_checked_tv. */
		ImageView device_checked_tv;

		/** The device name_tv. */
		TextView deviceName_tv;
	}

	@Override
	public int getCount() {
		return devicelist.size();
	}

	@Override
	public XPGWifiDevice getItem(int position) {
		return devicelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}
