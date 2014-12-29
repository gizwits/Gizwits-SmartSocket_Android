package com.xpg.appbase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xpg.appbase.sdk.SettingManager;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends BaseAdapter {

	private static final int VIEW_TYPE_COUNT = 5;
	public static final int VIEW_TYPE_LAN = 0;
	public static final int VIEW_TYPE_WAN = 1;
	public static final int VIEW_TYPE_OFFLINE = 2;
	public static final int VIEW_TYPE_UNBIND = 3;
	public static final int VIEW_TYPE_HEADER = 4;
	private SettingManager setManager;
	List<XPGWifiDevice> onlineDevices;
	List<XPGWifiDevice> offlineDevices;
	List<XPGWifiDevice> unBindDevices;

	private LayoutInflater mInflater;
	private List<XPGWifiDevice> currentDevices;
	private List<TypeItem> items;
	private Context context;

	public DeviceListAdapter(Context context, List<XPGWifiDevice> devices) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		setManager = new SettingManager(context);
		onlineDevices = new ArrayList<XPGWifiDevice>();
		offlineDevices = new ArrayList<XPGWifiDevice>();
		unBindDevices = new ArrayList<XPGWifiDevice>();
		changeDatas(devices);
	}

	class TypeItem {
		int itemType;

		private TypeItem(int itemType) {
			this.itemType = itemType;
		}
	}

	class DeviceTypeItem extends TypeItem {
		XPGWifiDevice xpgWifiDevice;

		public DeviceTypeItem(int type, XPGWifiDevice xpgWifiDevice) {
			super(type);
			this.xpgWifiDevice = xpgWifiDevice;
		}
	}

	class HeaderTypeItem extends TypeItem {
		String label;

		public HeaderTypeItem(String label) {
			super(VIEW_TYPE_HEADER);
			this.label = label;
		}
	}

	/**
	 * ViewHolder基类，itemView用于查找子view
	 */
	class ViewHolder {
		View itemView;

		public ViewHolder(View itemView) {
			if (itemView == null) {
				throw new IllegalArgumentException("itemView can not be null!");
			}
			this.itemView = itemView;
		}
	}

	/**
	 * 设备列表ViewHolder
	 */
	class DeviceViewHolder extends ViewHolder {
		ImageView icon;
		TextView name;
		TextView statue;
		ImageView arrow;

		public DeviceViewHolder(View view) {
			super(view);
			icon = (ImageView) view.findViewById(R.id.icon);
			arrow = (ImageView) view.findViewById(R.id.arrow);
			name = (TextView) view.findViewById(R.id.name);
			statue = (TextView) view.findViewById(R.id.statue);
		}
	}

	/**
	 * 头部ViewHolder
	 */
	class HeaderViewHolder extends ViewHolder {
		TextView label;

		public HeaderViewHolder(View view) {
			super(view);
			label = (TextView) view.findViewById(R.id.label);
		}
	}

	private List<TypeItem> generateItems(List<XPGWifiDevice> devices) {
		List<TypeItem> items = new ArrayList<TypeItem>();
		int size = devices == null ? 0 : devices.size();
		String currLabel;
		String preLabel = "{";
		XPGWifiDevice device;
		for (int i = 0; i < size; i++) {
			device = devices.get(i);
			if (device.isBind(setManager.getUid()) && device.isLAN()) {
				currLabel = "在线设备";
				if (i == 0 || !currLabel.equals(preLabel)) {
					items.add(new HeaderTypeItem("在线设备"));
					preLabel = currLabel;
				}
				items.add(new DeviceTypeItem(VIEW_TYPE_LAN, device));
			} else if (device.isBind(setManager.getUid()) && device.isOnline()) {
				currLabel = "在线设备";
				if (i == 0 || !currLabel.equals(preLabel)) {
					items.add(new HeaderTypeItem("在线设备"));
					preLabel = currLabel;
				}
				items.add(new DeviceTypeItem(VIEW_TYPE_WAN, device));
			} else if (device.isBind(setManager.getUid()) && !device.isOnline()) {
				currLabel = "离线设备";
				if (i == 0 || !currLabel.equals(preLabel)) {
					items.add(new HeaderTypeItem("离线设备"));
					preLabel = currLabel;
				}
				items.add(new DeviceTypeItem(VIEW_TYPE_OFFLINE, device));
			} else {
				currLabel = "未绑定设备";
				if (i == 0 || !currLabel.equals(preLabel)) {
					items.add(new HeaderTypeItem("未绑定设备"));
					preLabel = currLabel;
				}
				items.add(new DeviceTypeItem(VIEW_TYPE_UNBIND, device));
			}
		}
		return items;
	}

	public void changeDatas(List<XPGWifiDevice> devices) {
		onlineDevices.clear();
		offlineDevices.clear();
		unBindDevices.clear();
		if (currentDevices != null && currentDevices.size() > 0) {
			currentDevices.clear();
		} else {
			currentDevices = new ArrayList<XPGWifiDevice>();
		}
		for (XPGWifiDevice device : devices) {

			if (device.isLAN()) {
				if (device.isBind(setManager.getUid())) {
					onlineDevices.add(device);
				} else {
					unBindDevices.add(device);
				}
			} else {
				if (!device.isOnline()) {
					offlineDevices.add(device);
				} else {
					onlineDevices.add(device);
				}
			}
		}
		currentDevices.addAll(onlineDevices);
		currentDevices.addAll(offlineDevices);
		currentDevices.addAll(unBindDevices);
		this.items = generateItems(currentDevices);
		notifyDataSetChanged();
	}

	@Override
	public int getItemViewType(int position) {
		if (items.get(position) != null) {
			return items.get(position).itemType;
		}
		return super.getItemViewType(position);
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public int getCount() {
		return items != null ? items.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		if (items != null && position > 0 && position < items.size()) {
			return items.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public XPGWifiDevice getDeviceByPosition(int position) {
		if (items.get(position).itemType == VIEW_TYPE_HEADER) {
			return null;
		} else {
			DeviceTypeItem deviceTypeItem = (DeviceTypeItem) items
					.get(position);
			return deviceTypeItem.xpgWifiDevice;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TypeItem item = items.get(position);
		ViewHolder viewHolder;
		if (convertView == null) {
			// 根据不同的viewType，初始化不同的布局
			switch (getItemViewType(position)) {
			case VIEW_TYPE_HEADER:
				viewHolder = new HeaderViewHolder(mInflater.inflate(
						R.layout.device_list_item_header, null));
				break;

			case VIEW_TYPE_LAN:
			case VIEW_TYPE_WAN:
			case VIEW_TYPE_OFFLINE:
			case VIEW_TYPE_UNBIND:
				viewHolder = new DeviceViewHolder(mInflater.inflate(
						R.layout.device_list_item, null));
				break;

			default:
				throw new IllegalArgumentException("invalid view type : "
						+ getItemViewType(position));
			}

			// 缓存header与item视图
			convertView = viewHolder.itemView;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// 根据初始化的不同布局，绑定数据
		if (viewHolder instanceof HeaderViewHolder) {
			((HeaderViewHolder) viewHolder).label.setText(String
					.valueOf(((HeaderTypeItem) item).label));
		} else if (viewHolder instanceof DeviceViewHolder) {
			onBindDeviceItem((DeviceViewHolder) viewHolder,
					((DeviceTypeItem) item).xpgWifiDevice);
		}
		return convertView;
	}

	private void onBindDeviceItem(DeviceViewHolder viewHolder,
			XPGWifiDevice device) {
		if (device.isLAN()) {
			if (device.isBind(setManager.getUid())) {
				viewHolder.icon.setImageResource(R.drawable.device_icon_blue);
				viewHolder.name.setText(device.getProductName());
				viewHolder.name.setTextColor(context.getResources().getColor(
						R.color.text_blue));
				viewHolder.statue.setText("局域网在线");
				viewHolder.arrow.setVisibility(View.VISIBLE);
				viewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
			} else {
				viewHolder.icon.setImageResource(R.drawable.device_icon_gray);
				viewHolder.name.setText(device.getProductName());
				viewHolder.name.setTextColor(context.getResources().getColor(
						R.color.text_gray));
				viewHolder.statue.setText("未绑定");
				viewHolder.arrow.setVisibility(View.VISIBLE);
				viewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);
			}
		} else {
			if (!device.isOnline()) {
				viewHolder.icon.setImageResource(R.drawable.device_icon_gray);
				viewHolder.name.setText(device.getProductName());
				viewHolder.name.setTextColor(context.getResources().getColor(
						R.color.text_gray));
				viewHolder.statue.setText("离线");
				viewHolder.arrow.setVisibility(View.GONE);
				viewHolder.arrow.setImageResource(R.drawable.arrow_right_gray);
			} else {
				viewHolder.icon.setImageResource(R.drawable.device_icon_blue);
				viewHolder.name.setText(device.getProductName());
				viewHolder.name.setTextColor(context.getResources().getColor(
						R.color.text_blue));
				viewHolder.statue.setText("远程在线");
				viewHolder.arrow.setVisibility(View.VISIBLE);
				viewHolder.arrow.setImageResource(R.drawable.arrow_right_blue);
			}
		}
	}

}
