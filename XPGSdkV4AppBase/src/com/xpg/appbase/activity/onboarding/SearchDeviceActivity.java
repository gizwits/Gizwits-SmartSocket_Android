/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SearchDeviceActivity.java
 * Package Name:com.xpg.appbase.activity.onboarding
 * Date:2014-12-3 15:41:21
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.xpg.appbase.activity.onboarding;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;
import com.xpg.appbase.adapter.SearchListAdapter;
import com.xpg.appbase.sdk.SettingManager;
import com.xpg.appbase.utils.DialogManager;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.zxing.CaptureActivity;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class SearchDeviceActivity. <br/>
 * <br/>
 * date: 2014-12-3 14:26:53 <br/>
 * 
 * @author Lien
 */
public class SearchDeviceActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	/** The btn add qr. */
	private Button btnAddQR;

	/** The btn add gokit. */
	private Button btnAddGokit;

	/** The lv devices. */
	private ListView lvDevices;

	/** The tv tips. */
	private TextView tvTips;

	/** The device list. */
	private ArrayList<XPGWifiDevice> deviceList;

	/** The dialog connect tip. */
	private Dialog noNetworkDialog;

	private ProgressDialog loadingDialog;

	private SearchListAdapter adapter;

	/** 网络状态广播接受器 */
	ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();

	private boolean isWaitingWifi = false;

	/**
	 * 
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		FOUND_SUCCESS,

		FOUND_FINISH,

		CHANGE_SUCCESS,

	}

	/** The handler. */
	Handler handler = new Handler() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case FOUND_FINISH:
				if (deviceList.size() > 0) {
					lvDevices.setVisibility(View.VISIBLE);
					tvTips.setVisibility(View.GONE);
				} else {
					lvDevices.setVisibility(View.GONE);
					tvTips.setVisibility(View.VISIBLE);
				}
				loadingDialog.cancel();
				break;
			case FOUND_SUCCESS:
				adapter.notifyDataSetChanged();
				break;
			case CHANGE_SUCCESS:
				IntentUtils.getInstance().startActivity(
						SearchDeviceActivity.this, AutoConfigActivity.class);
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpg.appbase.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchdevice);
		initViews();
		initEvents();
	}

	@Override
	public void onResume() {
		loadingDialog.show();
		mCenter.cGetBoundDevices(setmanager.getUid(), setmanager.getToken());
		handler.sendEmptyMessageDelayed(handler_key.FOUND_FINISH.ordinal(),
				5000);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mChangeBroadcast, filter);
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(mChangeBroadcast);

	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		btnAddQR = (Button) findViewById(R.id.btnAddQR);
		btnAddGokit = (Button) findViewById(R.id.btnAddGokit);
		lvDevices = (ListView) findViewById(R.id.lvDevices);
		tvTips = (TextView) findViewById(R.id.tvTips);
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setMessage("加载中，请稍候.");
		loadingDialog.setCancelable(false);
		deviceList = new ArrayList<XPGWifiDevice>();
		adapter = new SearchListAdapter(this, deviceslist);
		lvDevices.setAdapter(adapter);
		noNetworkDialog = DialogManager.getNoNetworkDialog(this);
		noNetworkDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				isWaitingWifi = false;

			}
		});
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnAddQR.setOnClickListener(this);
		btnAddGokit.setOnClickListener(this);
		lvDevices.setOnItemClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddQR:

			if (NetworkUtils.isNetworkConnected(this)) {
				// TODO 跳转到二维码扫描activity
				IntentUtils.getInstance().startActivity(this,
						CaptureActivity.class);
			}
			break;
		case R.id.btnAddGokit:
			if (NetworkUtils.isWifiConnected(this)) {
				// TODO 跳转到添加airlink activity
			} else {
				isWaitingWifi = true;
				DialogManager.showDialog(this, noNetworkDialog);
			}
			break;
		}

	}

	@Override
	protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
		deviceList = new ArrayList<XPGWifiDevice>();
		if (devicesList.size() > 0) {
			deviceList.addAll(devicesList);
			handler.sendEmptyMessage(handler_key.FOUND_SUCCESS.ordinal());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO 跳到获取passcode activity

	}

	/**
	 * 广播监听器，监听wifi连上的广播.
	 */
	public class ConnecteChangeBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			boolean iswifi = NetworkUtils.isWifiConnected(context);
			Log.i("networkchange", "change" + iswifi);
			if (iswifi && isWaitingWifi) {
				if (noNetworkDialog.isShowing()) {
					DialogManager.dismissDialog(SearchDeviceActivity.this,
							noNetworkDialog);
					handler.sendEmptyMessage(handler_key.CHANGE_SUCCESS
							.ordinal());
				}
			}
		}
	}

}
