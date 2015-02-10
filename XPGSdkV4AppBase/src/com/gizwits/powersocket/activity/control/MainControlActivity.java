/**
 * Project Name:XPGSdkV4AppBase
 * File Name:MainControlActivity.java
 * Package Name:com.gizwits.aircondition.activity.control
 * Date:2015-1-27 14:44:17
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
package com.gizwits.powersocket.activity.control;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.DeviceAlarm;
import com.xpg.common.useful.StringUtils;
import com.gizwits.powersocket.R;
import com.gizwits.powersocket.activity.slipbar.SlipBarActivity;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc
/**
 * Created by Lien on 14/12/21.
 * 
 * 设备主控界面
 * 
 * @author Lien
 */
public class MainControlActivity extends BaseActivity implements
		OnClickListener {

	/** The tag. */
	private final String TAG = "MainControlActivity";
	// private XPGWifiDevice device;
	/** The seek bar. */
	// private CircularSeekBar seekBar;
	
	/** The scl content. */
	private ScrollView sclContent;

	/** The m view. */
	private static View mView;

	/** The ll bottom. */
	private LinearLayout llBottom;

	/** The iv menu. */
	private ImageView ivMenu;

	/** The tv title. */
	private TextView tvTitle;

	/** The iv power. */
	private ImageView ivPower;

	/** The iv back. */
	private ImageView ivBack;

	/** The is show. */
	private boolean isShow;

	/** The mode pos. */
	private int modePos;

	/** The height. */
	private int height;

	/** The alarm list. */
	private ArrayList<DeviceAlarm> alarmList;

	/** The alarm list has shown. */
	private ArrayList<String> alarmShowList;

	/** The timing off. */
	private int timingOn, timingOff;

	/** The m fault dialog. */
	private Dialog mFaultDialog;

	/** The m PowerOff dialog. */
	private Dialog mPowerOffDialog;
	// ==================================================================
	private TextView tvConsumption;
	private TextView tvTiming;
	private TextView tvDelay;
	private Button btnPower;
	private Button btnAppoinment;
	private LinearLayout llTiming;
	private LinearLayout llDelay;

	// ==================================================================
	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** 更新UI界面 */
		UPDATE_UI,

		/** 显示警告 */
		ALARM,

		/** 设备断开连接 */
		DISCONNECTED,

		/** 接收到设备的数据 */
		RECEIVED,

		/** 获取设备状态 */
		GET_STATUE,
	}

	/**
	 * The handler.
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case RECEIVED:
				for (String myKey : deviceDataMap.keySet()) {
					Log.e("Map",
							"key=" + myKey + ",value="
									+ deviceDataMap.get(myKey));
				}
				try {
					if (deviceDataMap.get("data") != null) {
						Log.i("info", (String) deviceDataMap.get("data"));
						inputDataToMaps(statuMap,
								(String) deviceDataMap.get("data"));
					}
					// alarmList.clear();
					// if (deviceDataMap.get("alters") != null) {
					// Log.i("info", (String) deviceDataMap.get("alters"));
					// // 返回主线程处理报警数据刷新
					// inputAlarmToList((String) deviceDataMap.get("alters"));
					// }
					// if (deviceDataMap.get("faults") != null) {
					// Log.i("info", (String) deviceDataMap.get("faults"));
					// // 返回主线程处理错误数据刷新
					// inputAlarmToList((String) deviceDataMap.get("faults"));
					// }
					// 返回主线程处理P0数据刷新
					handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
					handler.sendEmptyMessage(handler_key.ALARM.ordinal());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case UPDATE_UI:
				if (statuMap != null && statuMap.size() > 0) {
					//开关更新
					updatePower((Boolean) statuMap.get(JsonKeys.ON_OFF));
					//能耗更新
					String consumption = (String) statuMap
							.get(JsonKeys.POWER_CONSUMPTION);
					if (!StringUtils.isEmpty(consumption))
						setConsumption(Integer.parseInt(consumption));
					//定时更新
					boolean isTurnOn = (Boolean) statuMap
							.get(JsonKeys.TIME_ON_OFF);
					String minOn = (String) statuMap
							.get(JsonKeys.TIME_ON_MINUTE);
					String minOff = (String) statuMap
							.get(JsonKeys.TIME_OFF_MINUTE);
					if (!StringUtils.isEmpty(minOn)
							&& !StringUtils.isEmpty(minOff))
						setTiming(isTurnOn, Integer.parseInt(minOn),
								Integer.parseInt(minOff));
					//延时更新
					isTurnOn = (Boolean) statuMap
							.get(JsonKeys.COUNT_DOWN_ON_OFF);
					String min = (String) statuMap
							.get(JsonKeys.COUNT_DOWN_MINUTE);
					if (!StringUtils.isEmpty(min))
						setDelay(isTurnOn, Integer.parseInt(min));
				}
				break;
			case ALARM:
				// // 是否需要弹dialog判断
				// boolean isNeedDialog = false;
				// for (DeviceAlarm alarm : alarmList) {
				// if (!alarmShowList.contains((String) alarm.getDesc())) {
				// alarmShowList.add(alarm.getDesc());
				// isNeedDialog = true;
				// }
				// }
				//
				// alarmShowList.clear();
				//
				// for (DeviceAlarm alarm : alarmList) {
				// alarmShowList.add(alarm.getDesc());
				// }
				//
				// if (alarmList != null && alarmList.size() > 0) {
				// if (isNeedDialog) {
				// if (mFaultDialog == null) {
				// mFaultDialog = DialogManager.getDeviceErrirDialog(
				// MainControlActivity.this, "设备故障",
				// new OnClickListener() {
				//
				// @Override
				// public void onClick(View v) {
				// Intent intent = new Intent(
				// Intent.ACTION_CALL, Uri
				// .parse("tel:10086"));
				// startActivity(intent);
				// mFaultDialog.dismiss();
				// mFaultDialog = null;
				// }
				// });
				//
				// }
				// mFaultDialog.show();
				// }
				// setTipsLayoutVisiblity(true, alarmList.size());
				// } else {
				// setTipsLayoutVisiblity(false, 0);
				// }
				break;
			case DISCONNECTED:
				mCenter.cDisconnect(mXpgWifiDevice);
				break;
			case GET_STATUE:
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			}
		}
	};

	// 0.制冷, 1.送风, 2.除湿, 3.自动,4.制热
	/** The mode images. */
	private int[] modeImages = { R.drawable.icon_model_cool,
			R.drawable.icon_model_wind, R.drawable.icon_model_water,
			R.drawable.icon_model_auto, R.drawable.icon_model_hot };

	// 0.制冷, 1.送风, 2.除湿, 3.自动,4.制热
	/** The mode req. */
	private short[] modeReq = { 0, 1, 2, 3, 4 };

	/** The mode strs. */
	private String[] modeStrs = { "制冷", "送风", "除湿", "自动", "制热" };

	/** 设定温度 */
	short temperatureC, temperatureF;

	/** 当前温度 */
	short innerTemperatureC, innerTemperatureF;

	/** 摄氏度标志位 */
	private boolean isCentigrade = true;

	/** 防抖标志位 */
	private boolean isClick;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.aircondition.activity.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_control);
		initViews();
		initEvents();
		initParams();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.aircondition.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		mXpgWifiDevice.setListener(deviceListener);
		// isCentigrade = setmanager.getUnit();
		// alarmShowList.clear();
		handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}

	/**
	 * Inits the params.
	 */
	private void initParams() {
		statuMap = new ConcurrentHashMap<String, Object>();
		// alarmList = new ArrayList<DeviceAlarm>();
		// alarmShowList = new ArrayList<String>();
		// height = llBottom.getHeight();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		mView = findViewById(R.id.main_layout);

		tvConsumption = (TextView) findViewById(R.id.tvConsumption);
		tvTiming = (TextView) findViewById(R.id.tvTiming);
		tvDelay = (TextView) findViewById(R.id.tvDelay);
		ivMenu=(ImageView) findViewById(R.id.ivMenu);
		btnPower = (Button) findViewById(R.id.btnPower);
		btnAppoinment=(Button) findViewById(R.id.btnAppoinment);
		llTiming = (LinearLayout) findViewById(R.id.llTiming);
		llDelay = (LinearLayout) findViewById(R.id.llDelay);
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnPower.setOnClickListener(this);
		ivMenu.setOnClickListener(this);
		btnAppoinment.setOnClickListener(this);
	}
	
	/**
	 * 电源开关切换.
	 * 
	 * @param isOn
	 *            the is on
	 */
	private void updatePower(boolean isPower) {
		if (isPower) {
			btnPower.setSelected(true);
		} else {
			btnPower.setSelected(false);
		}
	}

	/**
	 * 能耗显示.
	 * 
	 * @param num
	 *            the power consumption
	 */
	private void setConsumption(int num) {
		tvConsumption.setText(num + "度");
	}

	/**
	 * 设置预约栏显示与隐藏,预约时间的设置.
	 * 
	 * @param isTurnOn
	 *            is turn on the appoinment
	 * @param on
	 *            the time start
	 * @param off
	 *            the time end
	 * @true 显示
	 * @false 隐藏
	 */
	private void setTiming(boolean isTurnOn, int on, int off) {
		if (isTurnOn) {
			llTiming.setVisibility(View.VISIBLE);
		} else {
			llTiming.setVisibility(View.INVISIBLE);
		}
		int minOn = on % 60;
		int hourOn = on / 60;
		int minOff = off % 60;
		int hourOff = off / 60;
		tvTiming.setText(String.format("%02d:%02d-%02d:%02d", hourOn, minOn,
				hourOff, minOff));
	}

	/**
	 * 设置延时栏显示与隐藏,延时时间的设置.
	 * 
	 * @param isTurnOn
	 *            is turn on the appoinment
	 * @param on
	 *            the time delay
	 * @true 显示
	 * @false 隐藏
	 */
	private void setDelay(boolean isTurnOn, int on) {
		if (isTurnOn) {
			llDelay.setVisibility(View.VISIBLE);
		} else {
			llDelay.setVisibility(View.INVISIBLE);
		}
		int min = on % 60;
		int hour = on / 60;
		tvDelay.setText(String.format("%02d:%02d", hour, min));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPower:
			mCenter.cPowerOn(mXpgWifiDevice, !btnPower.isSelected());
			updatePower(!btnPower.isSelected());
			break;
		case R.id.ivMenu:
			if (!isClick) {
				isClick = true;
				startActivityForResult(new Intent(MainControlActivity.this,
						SlipBarActivity.class), Activity.RESULT_FIRST_USER);
				overridePendingTransition(0, 0);
			}
			break;
		case R.id.rlAlarmTips:
		case R.id.tvTitle:
			if (alarmList != null && alarmList.size() > 0) {
				Intent intent = new Intent(MainControlActivity.this,
						AlarmListActicity.class);
				intent.putExtra("alarm_list", alarmList);
				startActivity(intent);
			}
			break;
		case R.id.btnAppoinment:
			startActivity(new Intent(MainControlActivity.this,AppointmentActivity.class));
			break;
		// case R.id.tvUnit:
		// isCentigrade = !isCentigrade;
		// updateTemperatureUnit(isCentigrade);
		// llBottom.setVisibility(View.GONE);
		// isShow = false;
		// break;
		// case R.id.tvCurve:
		// IntentUtils.getInstance().startActivity(MainControlActivity.this,
		// CurveActivity.class);
		// break;
		}
	}

	/**
	 * 发送命令.
	 * 
	 * @param pos
	 *            the pos
	 */
	private void sendModeReq(int pos) {
		// mCenter.cMode(mXpgWifiDevice, modeReq[pos]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.aircondition.activity.BaseActivity#didReceiveData(com.xtremeprog
	 * .xpgconnect.XPGWifiDevice, java.util.concurrent.ConcurrentHashMap, int)
	 */
	@Override
	protected void didReceiveData(XPGWifiDevice device,
			ConcurrentHashMap<String, Object> dataMap, int result) {
		Log.e(TAG, "didReceiveData");
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
			mCenter.cDisconnect(mXpgWifiDevice);
			mXpgWifiDevice = null;
		}
		finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.aircondition.activity.BaseActivity#didDisconnected(com.xtremeprog
	 * .xpgconnect.XPGWifiDevice)
	 */
	@Override
	protected void didDisconnected(XPGWifiDevice device) {
		super.didDisconnected(device);
	}

	/**
	 * 获取当前界面的截图
	 * 
	 * @return the view
	 */
	public static Bitmap getView() {
		// 用指定大小生成一张透明的32位位图，并用它构建一张canvas画布
		Bitmap mBitmap = Bitmap.createBitmap(mView.getWidth(),
				mView.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		// 将指定的view包括其子view渲染到这种画布上，在这就是上一个activity布局的一个快照，现在这个bitmap上就是上一个activity的快照
		mView.draw(canvas);
		return mBitmap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		isClick = false;
	}

}
