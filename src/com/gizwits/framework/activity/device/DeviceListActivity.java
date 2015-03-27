/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DeviceListActivity.java
 * Package Name:com.gizwits.framework.activity.device
 * Date:2015-1-27 14:45:18
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
package com.gizwits.framework.activity.device;

import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
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
import android.widget.ImageView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.onboarding.BindingDeviceActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.framework.adapter.DeviceListAdapter;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.RefreshableListView;
import com.gizwits.framework.widget.RefreshableListView.OnRefreshListener;
import com.gizwits.powersocket.R;
import com.gizwits.powersocket.activity.control.MainControlActivity;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc

/**
 * ClassName: Class DeviceListActivity. <br/>
 * 设备列表，用于显示当前账号环境下的所有设备<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class DeviceListActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	/** The Constant TAG. */
	private static final String TAG = "DeviceListActivity";

	/**
	 * The iv TopBar leftBtn.
	 */
	private ImageView ivLogout;

	/** The iv add. */
	private ImageView ivAdd;

	/** The tv init date. */
	private RefreshableListView lvDevices;

	/** The device list adapter. */
	private DeviceListAdapter deviceListAdapter;

	/** The progress dialog. */
	private ProgressDialog progressDialog;

	/** The dialog. */
	private Dialog dialog;
	
	/** 登陆设备超时时间 */
	private int LoginDeviceTimeOut = 60000;

	/** 网络状态广播接受器. */
	private ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();

	/**
	 * The boolean isExit.
	 */
	private boolean isExit = false;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** The login start. */
		LOGIN_START,

		/**
		 * The login success.
		 */
		LOGIN_SUCCESS,

		/**
		 * The login fail.
		 */
		LOGIN_FAIL,

		/**
		 * The login timeout.
		 */
		LOGIN_TIMEOUT,

		/** The found. */
		FOUND,

		/**
		 * Exit the app.
		 */
		EXIT,

	}

	/** The handler. */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case FOUND:
				lvDevices.completeRefreshing();
				break;

			case LOGIN_SUCCESS:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				IntentUtils.getInstance().startActivity(
						DeviceListActivity.this, MainControlActivity.class);
				break;

			case LOGIN_FAIL:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				ToastUtils.showShort(DeviceListActivity.this, "连接失败");
				break;
			case LOGIN_TIMEOUT:
				DialogManager.dismissDialog(DeviceListActivity.this, progressDialog);
				ToastUtils.showShort(DeviceListActivity.this, "连接失败");
				break;
			case EXIT:
				isExit = false;
				break;
			}
			deviceListAdapter.changeDatas(deviceslist);
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devicelist);
		initViews();
		initEvents();
		if (getIntent() != null) {
			if (getIntent().getBooleanExtra("autoLogin", false)) {
				mCenter.cLogin(setmanager.getUserName(),
						setmanager.getPassword());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		if (getIntent().getBooleanExtra("isbind", false)) {

			mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(),
					getIntent().getStringExtra("did"), getIntent()
							.getStringExtra("passcode"), "");
		} else {
			getList();
		}

		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mChangeBroadcast, filter);

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mChangeBroadcast);
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		ivLogout = (ImageView) findViewById(R.id.ivLogout);
		ivAdd = (ImageView) findViewById(R.id.ivAdd);
		lvDevices = (RefreshableListView) findViewById(R.id.lvDevices);

		// deviceList = new ArrayList<XPGWifiDevice>();
		deviceListAdapter = new DeviceListAdapter(this, deviceslist);
		lvDevices.setAdapter(deviceListAdapter);
		lvDevices.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(RefreshableListView listView) {
				getList();

			}
		});
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("连接中，请稍候。");
		progressDialog.setCancelable(false);
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ivLogout.setOnClickListener(this);
		ivAdd.setOnClickListener(this);
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
		case R.id.ivAdd:
			IntentUtils.getInstance().startActivity(DeviceListActivity.this,
					SearchDeviceActivity.class);
			break;
		case R.id.ivLogout:
			if (dialog == null) {
				dialog = DialogManager.getLogoutDialog(DeviceListActivity.this,
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								setmanager.setToken("");
								setmanager.setUserName("");
								setmanager.setPassword("");
								setmanager.setUid("");
								
								DialogManager.dismissDialog(
										DeviceListActivity.this, dialog);
								ToastUtils.showShort(DeviceListActivity.this,
										"注销成功");
								IntentUtils.getInstance().startActivity(
										DeviceListActivity.this,
										LoginActivity.class);
								finish();
							}
						});
			}

			DialogManager.showDialog(DeviceListActivity.this, dialog);
			break;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget
	 * .AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		XPGWifiDevice tempDevice = deviceListAdapter
				.getDeviceByPosition(position);
		if (tempDevice == null) {
			return;
		}
		if (tempDevice.isLAN()) {
			if (tempDevice.isBind(setmanager.getUid())) {
				// TODO 登陆设备
				Log.i(TAG,
						"本地登陆设备:mac=" + tempDevice.getPasscode() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
				loginDevice(tempDevice);
				DialogManager.showDialog(DeviceListActivity.this, progressDialog);
			} else {
				// TODO 未设备
				Log.i(TAG,
						"绑定设备:mac=" + tempDevice.getMacAddress() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
				Intent intent = new Intent(DeviceListActivity.this,
						BindingDeviceActivity.class);
				intent.putExtra("mac", tempDevice.getMacAddress());
				intent.putExtra("did", tempDevice.getDid());
				startActivity(intent);
			}
		} else {
			if (!tempDevice.isOnline()) {
				// TODO 离线
				Log.i(TAG,
						"离线设备:mac=" + tempDevice.getPasscode() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
			} else {
				// TODO 登陆设备
				Log.i(TAG,
						"远程登陆设备:mac=" + tempDevice.getPasscode() + ";ip="
								+ tempDevice.getIPAddress() + ";did="
								+ tempDevice.getDid() + ";passcode="
								+ tempDevice.getPasscode());
				loginDevice(tempDevice);
				DialogManager.showDialog(DeviceListActivity.this, progressDialog);
			}
		}

	}

	/**
	 * 登陆设备
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(), LoginDeviceTimeOut);
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
		if (result == 0) {
			mXpgWifiDevice = device;
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}

	/**
	 * 处理获取设备列表动作
	 * 
	 * @return the list
	 */
	private void getList() {
		String uid = setmanager.getUid();
		String token = setmanager.getToken();
		mCenter.cGetBoundDevices(uid, token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didDiscovered(int,
	 * java.util.List)
	 */
	@Override
	protected void didDiscovered(int error, List<XPGWifiDevice> deviceList) {
		deviceslist = deviceList;
		handler.sendEmptyMessage(handler_key.FOUND.ordinal());

	}

	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		if (mXpgWifiDevice.getDid().equals(device.getDid())) {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didUserLogin(int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void didUserLogin(int error, String errorMessage, String uid,
			String token) {
		if (!uid.isEmpty() && !token.isEmpty()) {// 登陆成功
			setmanager.setUid(uid);
			setmanager.setToken(token);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		exit();
	}

	/**
	 * 广播监听器，监听wifi连上的广播.
	 * 
	 * @author Lien
	 */
	public class ConnecteChangeBroadcast extends BroadcastReceiver {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.content.BroadcastReceiver#onReceive(android.content.Context,
		 * android.content.Intent)
		 */
		@Override
		public void onReceive(Context context, Intent intent) {
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent
					.getAction())) {
				getList();
			}
		}
	}

}
