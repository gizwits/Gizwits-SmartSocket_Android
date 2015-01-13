package com.gizwits.aircondition.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.adapter.ManageListAdapter;
import com.gizwits.aircondition.adapter.SearchListAdapter;
import com.gizwits.aircondition.entity.Device;
import com.gizwits.aircondition.R;
import com.xpg.common.system.IntentUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.ArrayList;
import java.util.List;

public class DeviceManageListActivity extends BaseActivity {
	/**
	 * The iv TopBar leftBtn.
	 */
	private ImageView ivBack;

	/**
	 * The tv init date
	 */
	private ListView lvDevices;

	/**
	 * The Device device list
	 */
	private List<XPGWifiDevice> devices;

	ManageListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_device_list);

		initViews();
		initEvents();
	}

	private void initEvents() {
		lvDevices.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				XPGWifiDevice device = bindlist.get(position);
				if (device.isLAN() || device.isOnline()) {
					Intent intent = new Intent(DeviceManageListActivity.this,
							DeviceManageDetailActivity.class);
					intent.putExtra("mac", device.getMacAddress());
					intent.putExtra("did", device.getDid());
					startActivity(intent);
				}
			}
		});
		ivBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
				
			}
		});
	}

	private void initViews() {

		ivBack = (ImageView) findViewById(R.id.ivBack);
		lvDevices = (ListView) findViewById(R.id.lvDevices);
		mAdapter = new ManageListAdapter(DeviceManageListActivity.this,
				bindlist);
		lvDevices.setAdapter(mAdapter);
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
