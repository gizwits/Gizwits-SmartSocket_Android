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
package com.gizwits.aircondition.activity.control;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceManageListActivity;
import com.gizwits.framework.activity.help.AboutActivity;
import com.gizwits.framework.activity.help.HelpActivity;
import com.gizwits.framework.adapter.MenuDeviceAdapter;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.entity.DeviceAlarm;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.framework.utils.DialogManager.OnTimingChosenListener;
import com.gizwits.framework.widget.CircularSeekBar;
import com.gizwits.framework.widget.SlidingMenu;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
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
		OnClickListener, OnCheckedChangeListener,
		CompoundButton.OnCheckedChangeListener {

	/** The tag. */
	private final String TAG = "MainControlActivity";
	// private XPGWifiDevice device;
	/** The seek bar. */
	private CircularSeekBar seekBar;

	/** The scl content. */
	private ScrollView sclContent;

	/** The m view. */
	private SlidingMenu mView;

	// /** The rl control main page. */
	private RelativeLayout rlControlMainPage;
	//
	// /** The rl header. */
	// private RelativeLayout rlHeader;

	/** The rl alarm tips. */
	private RelativeLayout rlAlarmTips;

	/** The rl power off. */
	private RelativeLayout rlPowerOff;

	// /** The ll footer. */
	// private LinearLayout llFooter;

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

	/** The tv alarm tips count. */
	private TextView tvAlarmTipsCount;

	/** The tv time off. */
	private TextView tvTimeOff;

	/** The tv advanture. */
	private TextView tvAdvanture;

	/** The tv curve. */
	private TextView tvCurve;

	/** The ctv unit. */
	private CheckedTextView ctvUnit;

	/** The tv mode. */
	private TextView tvMode;

	/** The tv inner temperature. */
	private TextView tvInnerTemperature;

	/** The tv inner unit. */
	private TextView tvInnerUnit;

	/** The tv setting temerature. */
	private TextView tvSettingTemerature;

	/** The tv setting unit. */
	private TextView tvSettingUnit;

	/** The m adapter. */
	private MenuDeviceAdapter mAdapter;

	/** The lv device. */
	private ListView lvDevice;

	// /** The tv title off. */
	// private TextView tvTitleOff;

	/** The tv power on. */
	private TextView tvPowerOn;

	/** The tv power on str. */
	private TextView tvPowerOnStr;

	/** The ib left arrow. */
	private ImageButton ibLeftArrow;

	/** The ib right arrow. */
	private ImageButton ibRightArrow;

	/** The rg wing. */
	private RadioGroup rgWing;

	/** The rb wind low. */
	private RadioButton rbWindLow;

	/** The rb wind min. */
	private RadioButton rbWindMin;

	/** The rb wind high. */
	private RadioButton rbWindHigh;

	/** The cb wind shake. */
	private CheckBox cbWindShake;

	/** The is show. */
	private boolean isShow;

	/** The mode pos. */
	private int modePos;

	/** The height. */
	private int height;

	/** The device data map. */
	private ConcurrentHashMap<String, Object> deviceDataMap;

	/** The statu map. */
	private ConcurrentHashMap<String, Object> statuMap;

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

	/** The progress dialog. */
	private ProgressDialog progressDialog;

	/** 是否超时标志位 */
	private boolean isTimeOut = false;

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
				try {
					if (deviceDataMap.get("data") != null) {
						Log.i("info", (String) deviceDataMap.get("data"));
						inputDataToMaps(statuMap,
								(String) deviceDataMap.get("data"));

					}
					alarmList.clear();
					if (deviceDataMap.get("alters") != null) {
						Log.i("info", (String) deviceDataMap.get("alters"));
						// 返回主线程处理报警数据刷新
						inputAlarmToList((String) deviceDataMap.get("alters"));
					}
					if (deviceDataMap.get("faults") != null) {
						Log.i("info", (String) deviceDataMap.get("faults"));
						// 返回主线程处理错误数据刷新
						inputAlarmToList((String) deviceDataMap.get("faults"));
					}
					// 返回主线程处理P0数据刷新
					handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
					handler.sendEmptyMessage(handler_key.ALARM.ordinal());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			case UPDATE_UI:
				if (statuMap != null && statuMap.size() > 0) {
					setListenNull(true);
					updateTemperatureUnit(isCentigrade);
					updatePowerSwitch((Boolean) statuMap.get(JsonKeys.ON_OFF));
					updateModeState((String) statuMap.get(JsonKeys.MODE));
					String setTemp = (String) statuMap.get(JsonKeys.SET_TEMP);
					if (!StringUtils.isEmpty(setTemp)) {
						ubdateSeekBar(Short.parseShort(setTemp));
					}
					String roomTemp = (String) statuMap.get(JsonKeys.ROOM_TEMP);
					if (!StringUtils.isEmpty(roomTemp)) {
						updateInnerTemp(Short.parseShort(roomTemp));
					}

					String timeOn = (String) statuMap.get(JsonKeys.TIME_ON);
					if (!StringUtils.isEmpty(timeOn)) {
						updateOnTime(Integer.parseInt(timeOn));
					}
					String timeOff = (String) statuMap.get(JsonKeys.TIME_OFF);
					if (!StringUtils.isEmpty(timeOff)) {
						updateOffTime(Integer.parseInt(timeOff));
					}
					updateFanSpeed((String) statuMap.get(JsonKeys.FAN_SPEED));
					updateShakeSwitch((Boolean) statuMap
							.get(JsonKeys.FAN_SHAKE));
					setListenNull(false);
				}
				break;
			case ALARM:
				// 是否需要弹dialog判断
				boolean isNeedDialog = false;
				for (DeviceAlarm alarm : alarmList) {
					if (!alarmShowList.contains((String) alarm.getDesc())) {
						alarmShowList.add(alarm.getDesc());
						isNeedDialog = true;
					}
				}

				alarmShowList.clear();

				for (DeviceAlarm alarm : alarmList) {
					alarmShowList.add(alarm.getDesc());
				}

				if (alarmList != null && alarmList.size() > 0) {
					if (isNeedDialog) {
						if (mFaultDialog == null) {
							mFaultDialog = DialogManager.getDeviceErrirDialog(
									MainControlActivity.this, "设备故障",
									new OnClickListener() {

										@Override
										public void onClick(View v) {
											Intent intent = new Intent(
													Intent.ACTION_CALL, Uri
															.parse("tel:10086"));
											startActivity(intent);
											mFaultDialog.dismiss();
											mFaultDialog = null;
										}
									});

						}
						mFaultDialog.show();
					}
					setTipsLayoutVisiblity(true, alarmList.size());
				} else {
					setTipsLayoutVisiblity(false, 0);
				}
				break;
			case DISCONNECTED:
				mCenter.cDisconnect(mXpgWifiDevice);
				break;
			case GET_STATUE:
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			case LOGIN_SUCCESS:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				if (mView.isOpen()){
					mView.toggle();
				}
				mCenter.cGetStatus(mXpgWifiDevice);
				break;
			case LOGIN_FAIL:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				ToastUtils.showShort(MainControlActivity.this, "设备连接失败");
				break;
			case LOGIN_TIMEOUT:
				isTimeOut = true;
				progressDialog.cancel();
				ToastUtils.showShort(MainControlActivity.this, "设备连接超时");
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
		initBindList();
		mAdapter.setChoosedPos(-1);
		for (int i = 0; i < bindlist.size(); i++) {
			if (bindlist.get(i).getDid()
					.equalsIgnoreCase(mXpgWifiDevice.getDid()))
				mAdapter.setChoosedPos(i);
		}
		mAdapter.notifyDataSetChanged();
		
		mXpgWifiDevice.setListener(deviceListener);
		isCentigrade = setmanager.getUnit();
		alarmShowList.clear();
		handler.sendEmptyMessage(handler_key.GET_STATUE.ordinal());
	}

	/**
	 * Inits the params.
	 */
	private void initParams() {
		statuMap = new ConcurrentHashMap<String, Object>();
		alarmList = new ArrayList<DeviceAlarm>();
		alarmShowList = new ArrayList<String>();
		height = llBottom.getHeight();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		mView = (SlidingMenu) findViewById(R.id.main_layout);
		rlControlMainPage = (RelativeLayout) findViewById(R.id.rlControlMainPage);
		// rlHeader = (RelativeLayout) findViewById(R.id.rlHeader);
		rlAlarmTips = (RelativeLayout) findViewById(R.id.rlAlarmTips);
		rlPowerOff = (RelativeLayout) findViewById(R.id.rlPowerOff);
		// llFooter = (LinearLayout) findViewById(R.id.llFooter);
		llBottom = (LinearLayout) findViewById(R.id.llBottom);
		ivMenu = (ImageView) findViewById(R.id.ivMenu);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivPower = (ImageView) findViewById(R.id.ivPower);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		tvAlarmTipsCount = (TextView) findViewById(R.id.tvAlarmTipsCount);
		tvTimeOff = (TextView) findViewById(R.id.tvTimeOff);
		tvAdvanture = (TextView) findViewById(R.id.tvAdvanture);
		tvCurve = (TextView) findViewById(R.id.tvCurve);
		ctvUnit = (CheckedTextView) findViewById(R.id.tvUnit);
		tvMode = (TextView) findViewById(R.id.tvMode);
		tvInnerTemperature = (TextView) findViewById(R.id.tvInnerTemperature);
		tvInnerUnit = (TextView) findViewById(R.id.tvInnerUnit);
		tvSettingTemerature = (TextView) findViewById(R.id.tvSettingTemerature);
		tvSettingUnit = (TextView) findViewById(R.id.tvSettingUnit);
		tvPowerOn = (TextView) findViewById(R.id.tvPowerOn);
		tvPowerOnStr = (TextView) findViewById(R.id.tvPowerOnStr);
		ibLeftArrow = (ImageButton) findViewById(R.id.ibLeftArrow);
		ibRightArrow = (ImageButton) findViewById(R.id.ibRightArrow);
		rgWing = (RadioGroup) findViewById(R.id.rgWing);
		rbWindLow = (RadioButton) findViewById(R.id.rbWindLow);
		rbWindMin = (RadioButton) findViewById(R.id.rbWindMin);
		rbWindHigh = (RadioButton) findViewById(R.id.rbWindHigh);
		cbWindShake = (CheckBox) findViewById(R.id.cbWindShake);
		sclContent = (ScrollView) findViewById(R.id.sclContent);
		seekBar = (CircularSeekBar) findViewById(R.id.csbSeekbar);
		seekBar.postInvalidateDelayed(2000);
		seekBar.setMaxProgress(100);
		seekBar.setProgress(30);
		seekBar.setMProgress(0);
		seekBar.setScrollViewInParent(sclContent);
		seekBar.postInvalidateDelayed(100);
		seekBar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {
			@Override
			public void onProgressChange(CircularSeekBar view, int newProgress) {
				mCenter.cSetTemp(mXpgWifiDevice, temperatureC);
			}
		});
		seekBar.setSeekContinueChangeListener(new CircularSeekBar.OnSeekContinueChangeListener() {
			@Override
			public void onProgressContinueChange(CircularSeekBar view,
					int newProgress) {
				temperatureC = (short) (newProgress * 14 / 100.00 + 16);
				temperatureF = (short) getCelToFah(temperatureC);
				tvSettingTemerature.setText((isCentigrade ? temperatureC
						: temperatureF) + "");
				tvSettingUnit.setText(isCentigrade ? "℃" : "℉");
			}
		});
		mPowerOffDialog = DialogManager.getPowerOffDialog(this,
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						mCenter.cSwitchOn(mXpgWifiDevice, false);
						DialogManager.dismissDialog(MainControlActivity.this,
								mPowerOffDialog);
					}
				});

		mAdapter = new MenuDeviceAdapter(this, bindlist);
		lvDevice = (ListView) findViewById(R.id.lvDevice);
		lvDevice.setAdapter(mAdapter);

		progressDialog = new ProgressDialog(MainControlActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("设备连接中，请稍候。");
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ibLeftArrow.setOnClickListener(this);
		ibRightArrow.setOnClickListener(this);

		tvPowerOn.setOnClickListener(this);
		ivPower.setOnClickListener(this);
		sclContent.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				llBottom.setVisibility(View.GONE);
				isShow = false;
				return false;
			}
		});
		tvTimeOff.setOnClickListener(this);
		tvAdvanture.setOnClickListener(this);
		tvPowerOnStr.setOnClickListener(this);
		ivMenu.setOnClickListener(this);
		rlAlarmTips.setOnClickListener(this);
		tvTitle.setOnClickListener(this);
		ctvUnit.setOnClickListener(this);
		cbWindShake.setOnCheckedChangeListener(this);
		rgWing.setOnCheckedChangeListener(this);
		tvCurve.setOnClickListener(this);
		ivBack.setOnClickListener(this);

		lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mAdapter.getItem(position).isOnline())
					return;

				if (mAdapter.getChoosedPos() == position) {
					mView.toggle();
					return;
				}

				mAdapter.setChoosedPos(position);
				mXpgWifiDevice = bindlist.get(position);
				loginDevice(mXpgWifiDevice);
			}
		});
	}

	/**
	 * 防止循环调用.
	 * 
	 * @author Administrator
	 * @param on
	 *            the new listen null
	 * @return void
	 * @Title: setListenNull
	 * @Description: TODO
	 */
	private void setListenNull(boolean on) {
		cbWindShake.setOnCheckedChangeListener(on ? null : this);
		rgWing.setOnCheckedChangeListener(on ? null : this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android
	 * .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		// 设置低风
		case R.id.rbWindLow:
			mCenter.cFanSpeed(mXpgWifiDevice, 0);
			break;
		// 设置中风
		case R.id.rbWindMin:
			mCenter.cFanSpeed(mXpgWifiDevice, 1);
			break;
		// 设置高风
		case R.id.rbWindHigh:
			mCenter.cFanSpeed(mXpgWifiDevice, 2);
			break;

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.cbWindShake:// 摆风
			mCenter.cSetShake(mXpgWifiDevice, cbWindShake.isChecked());
			break;
		default:
			break;
		}
	}

	/**
	 * 更新温度单位.
	 * 
	 * @param centigrade
	 *            the centigrade
	 */
	private void updateTemperatureUnit(boolean centigrade) {
		setmanager.setUnit(centigrade);
		tvInnerTemperature.setText((centigrade ? innerTemperatureC
				: innerTemperatureF) + "");
		tvSettingTemerature.setText((centigrade ? temperatureC : temperatureF)
				+ "");
		ctvUnit.setText(centigrade ? "摄氏" : "华氏");
		tvInnerUnit.setText(centigrade ? "℃" : "℉");
		tvSettingUnit.setText(centigrade ? "℃" : "℉");
		ctvUnit.setChecked(centigrade);
	}

	/**
	 * 更新环状进度.
	 * 
	 * @param temperature
	 *            the temperature
	 */
	private void ubdateSeekBar(short temperature) {
		if (temperatureC == temperature) {
			return;
		}
		temperatureC = temperature;
		int progress = (int) ((temperatureC - 16) * 100.00 / 14);
		// seekBar.setSeekContinueChangeListener(null);
		if (seekBar != null) {
			seekBar.setMProgress(progress);
			seekBar.postInvalidateDelayed(1000);
		}
		temperatureF = (short) getCelToFah(temperatureC);
		tvSettingTemerature
				.setText((isCentigrade ? temperatureC : temperatureF) + "");

	}

	/**
	 * 更新当前温度
	 * 
	 * @param temperature
	 *            the temperature
	 */
	private void updateInnerTemp(short temperature) {
		if (innerTemperatureC == temperature) {
			return;
		}
		innerTemperatureC = temperature;
		innerTemperatureF = (short) getCelToFah(innerTemperatureC);
		tvInnerTemperature.setText((isCentigrade ? innerTemperatureC
				: innerTemperatureF) + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
		case R.id.ivMenu:
			mView.toggle();
			break;
		case R.id.tvPowerOn:
			mCenter.cSwitchOn(mXpgWifiDevice, true);
			break;
		case R.id.ivPower:
			mPowerOffDialog.show();
			break;
		case R.id.ibLeftArrow:
			modePos--;
			if (modePos < 0)
				modePos = 4;
			sendModeReq(modePos);
			updateModeState(modePos + "");
			break;
		case R.id.ibRightArrow:
			modePos++;
			if (modePos > 4)
				modePos = 0;
			sendModeReq(modePos);
			updateModeState(modePos + "");
			break;
		case R.id.tvAdvanture:
			llBottom.setVisibility(isShow ? View.GONE : View.VISIBLE);
			sclContent.scrollBy(0, isShow ? -height : height);
			isShow = !isShow;
			break;
		case R.id.tvTimeOff:
			DialogManager.getWheelTimingDialog(this,
					new OnTimingChosenListener() {

						@Override
						public void timingChosen(int time) {
							// 设置定时开机时间
							mCenter.cTimeOff(mXpgWifiDevice, time);
							timingOff = time;
							tvTimeOff.setText(timingOff > 0 ? timingOff
									+ "小时后关机" : "定时关机");

						}
					}, " 定时关机", timingOff == 0 ? 24 : timingOff - 1).show();
			break;
		case R.id.tvPowerOnStr:
			DialogManager.getWheelTimingDialog(this,
					new OnTimingChosenListener() {

						@Override
						public void timingChosen(int time) {
							// 设置定时开机时间
							mCenter.cTimeOn(mXpgWifiDevice, time);
							timingOn = time;
							tvPowerOnStr.setText(timingOn > 0 ? timingOn
									+ "小时后开机" : "定时开机");

						}
					}, " 定时开机", timingOn == 0 ? 24 : timingOn - 1).show();
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
		case R.id.tvUnit:
			isCentigrade = !isCentigrade;
			updateTemperatureUnit(isCentigrade);
			llBottom.setVisibility(View.GONE);
			isShow = false;
			break;
		case R.id.tvCurve:
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					CurveActivity.class);
			break;
		}
	}

	public void onClickSlipBar(View view) {
		switch (view.getId()) {
		case R.id.rlDevice:
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					DeviceManageListActivity.class);
			break;
		case R.id.rlAbout:
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					AboutActivity.class);
			break;
		case R.id.rlAccount:
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					UserManageActivity.class);
			break;
		case R.id.rlHelp:
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					HelpActivity.class);
			break;
		case R.id.btnDeviceList:
			mCenter.cDisconnect(mXpgWifiDevice);
			DisconnectOtherDevice();
			IntentUtils.getInstance().startActivity(MainControlActivity.this,
					DeviceListActivity.class);
			finish();
			break;
		}
	}

	/**
	 * Login device.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		DisconnectOtherDevice();
		mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
		progressDialog.show();
		isTimeOut = false;
		handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(),
				5000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		if (isTimeOut)
			return;

		if (result == 0) {
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}

	/**
	 * 检查出了选中device，其他device有没有连接上
	 * 
	 * @param mac
	 *            the mac
	 * @param did
	 *            the did
	 * @return the XPG wifi device
	 */
	private void DisconnectOtherDevice() {
		for (XPGWifiDevice theDevice : bindlist) {
			if (theDevice.isConnected()
					&& !theDevice.getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
				mCenter.cDisconnect(theDevice);
		}
	}

	/**
	 * 转换模式更新UI.
	 * 
	 * @param pos
	 *            the pos
	 * @when pos = 0 制冷
	 * @when pos = 1送风
	 * @when pos = 2 除湿
	 * @when pos = 3 自动
	 */
	private void updateModeState(String pos) {
		modePos = Integer.parseInt(pos);
		tvMode.setCompoundDrawablesWithIntrinsicBounds(0, modeImages[modePos],
				0, 0);
		tvMode.setText(modeStrs[modePos]);
	}

	/**
	 * 更新风速状态
	 * 
	 * @param speedStr
	 *            the speed str
	 */
	private void updateFanSpeed(String speedStr) {
		int speed = Integer.parseInt(speedStr);
		switch (speed) {
		case 0:
			rbWindLow.setChecked(true);
			break;
		case 1:
			rbWindMin.setChecked(true);
			break;
		case 2:
			rbWindHigh.setChecked(true);
			break;
		}
	}

	/**
	 * 更新定时关机信息
	 * 
	 * @param timingOff
	 *            the timing off
	 */
	private void updateOffTime(int timingOff) {
		tvTimeOff.setText(timingOff > 0 ? timingOff + "小时后关机" : "定时关机");
	}

	/**
	 * 更新定时开机信息
	 * 
	 * @param timingOn
	 *            the timing on
	 */
	private void updateOnTime(int timingOn) {
		tvPowerOnStr.setText(timingOn > 0 ? timingOn + "小时后开机" : "定时开机");
	}

	/**
	 * 发送命令.
	 * 
	 * @param pos
	 *            the pos
	 */
	private void sendModeReq(int pos) {
		mCenter.cMode(mXpgWifiDevice, modeReq[pos]);
	}

	/**
	 * 设置提示框显示与隐藏,设置故障数量.
	 * 
	 * @param isShow
	 *            the is show
	 * @param count
	 *            the count
	 * @true 显示
	 * @false 隐藏
	 */
	private void setTipsLayoutVisiblity(boolean isShow, int count) {
		rlAlarmTips.setVisibility(isShow ? View.VISIBLE : View.GONE);
		tvAlarmTipsCount.setText(count + "");
	}

	/**
	 * 开关切换.
	 * 
	 * @param isOn
	 *            the is on
	 */
	public void updatePowerSwitch(boolean isOn) {// 开机页面和主页面切换
		if (!isOn) {
			llBottom.setVisibility(View.GONE);
			isShow = false;
		}
		rlPowerOff.setVisibility(isOn ? View.GONE : View.VISIBLE);
		rlControlMainPage.setVisibility(isOn ? View.VISIBLE : View.GONE);
	}

	/**
	 * 更新摆风状态信息
	 * 
	 * @param isOn
	 *            the is on
	 */
	public void updateShakeSwitch(boolean isOn) {
		cbWindShake.setChecked(isOn);
	}

	/**
	 * 摄氏转华氏温度.
	 * 
	 * @author Administrator
	 * @param cel
	 *            the cel
	 * @return int
	 * @Title: getCelToFah
	 * @Description: TODO
	 */
	public static int getCelToFah(int cel) {

		return (int) (cel * 9 / 5.0 + 32);
	}

	/**
	 * 华氏转摄氏温度.
	 * 
	 * @author Administrator
	 * @param fah
	 *            the fah
	 * @return int
	 * @Title: getFahToCel
	 * @Description: TODO
	 */
	public static int getFahToCel(int fah) {
		return (int) ((5 / 9.0) * (fah - 32));
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
		this.deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (mView.isOpen()) {
			mView.toggle();
		} else {
			if (mXpgWifiDevice != null && mXpgWifiDevice.isConnected()) {
				mCenter.cDisconnect(mXpgWifiDevice);
				DisconnectOtherDevice();
			}
			finish();
		}
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
	 * 把警告信息存入列表
	 * 
	 * @param json
	 *            the json
	 * @throws JSONException
	 *             the JSON exception
	 */
	private void inputAlarmToList(String json) throws JSONException {
		Log.i("revjson", json);
		JSONObject receive = new JSONObject(json);
		Iterator actions = receive.keys();
		while (actions.hasNext()) {

			String action = actions.next().toString();
			Log.i("revjson", "action=" + action);
			DeviceAlarm alarm = new DeviceAlarm(getDateCN(new Date()), action);
			alarmList.add(alarm);
		}
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}

	/**
	 * 把状态信息存入表
	 * 
	 * @param map
	 *            the map
	 * @param json
	 *            the json
	 * @throws JSONException
	 *             the JSON exception
	 */
	private void inputDataToMaps(ConcurrentHashMap<String, Object> map,
			String json) throws JSONException {
		Log.i("revjson", json);
		JSONObject receive = new JSONObject(json);
		Iterator actions = receive.keys();
		while (actions.hasNext()) {

			String action = actions.next().toString();
			Log.i("revjson", "action=" + action);
			// 忽略特殊部分
			if (action.equals("cmd") || action.equals("qos")
					|| action.equals("seq") || action.equals("version")) {
				continue;
			}
			JSONObject params = receive.getJSONObject(action);
			Log.i("revjson", "params=" + params);
			Iterator it_params = params.keys();
			while (it_params.hasNext()) {
				String param = it_params.next().toString();
				Object value = params.get(param);
				map.put(param, value);
				Log.i(TAG, "Key:" + param + ";value" + value);
			}
		}
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}

	/**
	 * 获取格式：2014年6月24日 17:23.
	 * 
	 * @param date
	 *            the date
	 * @return the date cn
	 */
	public static String getDateCN(Date date) {
		int y = date.getYear();
		int m = date.getMonth() + 1;
		int d = date.getDate();

		int h = date.getHours();
		int mt = date.getMinutes();

		return (y + 1900) + "年" + m + "月" + d + "日  " + h + ":" + mt;

	}

}
