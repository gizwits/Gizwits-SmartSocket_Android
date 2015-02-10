package com.gizwits.powersocket.activity.control;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.adapter.WeekRepeatAdapter;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.utils.DialogManager.On2TimingChosenListener;
import com.gizwits.framework.utils.DialogManager.OnTimingChosenListener;
import com.gizwits.powersocket.R;
import com.xpg.common.useful.ByteUtils;
import com.xpg.common.useful.StringUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AppointmentActivity extends BaseActivity implements
		OnClickListener, OnCheckedChangeListener {

	// /** The device data map. */
	// private ConcurrentHashMap<String, Object> deviceDataMap;
	//
	// /** The statu map. */
	// private ConcurrentHashMap<String, Object> statuMap;

	private TextView tvTiming;
	private TextView tvDelay;
	private TextView tvTimingTime;
	private TextView tvDelayTime;
	private TextView tvTimingStart;
	private TextView tvTimingEnd;
	private TextView tvTitle;
	private ToggleButton tbTiming;
	private ToggleButton tbDelay;
	private LinearLayout llAppointmentMenu;
	private LinearLayout llSetAppointment;
	private RelativeLayout rlStartTimeSetting;
	private RelativeLayout rlEndTimeSetting;
	private ImageView ivBack;
	private GridView gvDateSelect;
	private WeekRepeatAdapter mWeekRepeatAdapter;
	private ArrayList<Boolean> mSelectList;
	private UI_STATE uiNow;
	private int hourStart;
	private int minStart;
	private int hourEnd;
	private int minEnd;
	private int hourDelay;
	private boolean isLock = false;
	private int Lock_Time = 2000;

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

		/** 解锁 */
		UNLOCK,

		/** 接收到设备的数据 */
		RECEIVED,
	}

	private enum UI_STATE {
		MENU, SET_APPOINTMENT;
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
						inputDataToMaps(statuMap,
								(String) deviceDataMap.get("data"));
						handler.sendEmptyMessage(handler_key.UPDATE_UI
								.ordinal());
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			case UPDATE_UI:
				if (isLock)
					break;

				if (statuMap != null && statuMap.size() > 0) {
					// 定时更新
					boolean isTurnOn = (Boolean) statuMap
							.get(JsonKeys.TIME_ON_OFF);
					String minOn = (String) statuMap
							.get(JsonKeys.TIME_ON_MINUTE);
					String minOff = (String) statuMap
							.get(JsonKeys.TIME_OFF_MINUTE);
					if (!StringUtils.isEmpty(minOn)
							&& !StringUtils.isEmpty(minOff))
						setTimingTime(isTurnOn, Integer.parseInt(minOn),
								Integer.parseInt(minOff));
					// 延时更新
					isTurnOn = (Boolean) statuMap
							.get(JsonKeys.COUNT_DOWN_ON_OFF);
					String min = (String) statuMap
							.get(JsonKeys.COUNT_DOWN_MINUTE);
					if (!StringUtils.isEmpty(min))
						setDelayTime(isTurnOn, Integer.parseInt(min));

					// 每周重复
					String repeat = (String) statuMap.get(JsonKeys.WEEK_REPEAT);
					int mRepeat = Integer.parseInt(repeat);
					for (int i = 0; i < 8; i++) {
						mSelectList.set(i,
								ByteUtils.getBitFromShort(mRepeat, i));
					}
					mWeekRepeatAdapter.notifyDataSetChanged();
				}
				break;
			case UNLOCK:
				isLock = false;
				handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointment);
		initView();
		initEvent();
	}

	private void initView() {
		gvDateSelect = (GridView) findViewById(R.id.gvDateSelect);
		tvTiming = (TextView) findViewById(R.id.tvTiming);
		tvDelay = (TextView) findViewById(R.id.tvDelay);
		tvTimingTime = (TextView) findViewById(R.id.tvTimingTime);
		tvDelayTime = (TextView) findViewById(R.id.tvDelayTime);
		tbTiming = (ToggleButton) findViewById(R.id.tbTimingFlag);
		tbDelay = (ToggleButton) findViewById(R.id.tbDelayFlag);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		llAppointmentMenu = (LinearLayout) findViewById(R.id.llAppointmentMenu);
		llSetAppointment = (LinearLayout) findViewById(R.id.llSetAppointment);
		rlStartTimeSetting = (RelativeLayout) findViewById(R.id.rlStartTimeSetting);
		rlEndTimeSetting = (RelativeLayout) findViewById(R.id.rlEndTimeSetting);
		tvTimingStart = (TextView) findViewById(R.id.tvTimingStart);
		tvTimingEnd = (TextView) findViewById(R.id.tvTimingEnd);
		tvTitle = (TextView) findViewById(R.id.tvTitle);

		mSelectList = new ArrayList<Boolean>();
		for (int i = 0; i < 8; i++) {
			mSelectList.add(false);
		}

		mWeekRepeatAdapter = new WeekRepeatAdapter(this, mSelectList);
		gvDateSelect.setAdapter(mWeekRepeatAdapter);

		gvDateSelect
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						isLock=true;
						handler.removeMessages(handler_key.UNLOCK.ordinal());
						boolean isSelected = mSelectList.get(position);
						mSelectList.set(position, !isSelected);
						mWeekRepeatAdapter.notifyDataSetInvalidated();
						mCenter.cWeekRepeat(mXpgWifiDevice,
								bytes2Integer(mSelectList));
						handler.sendEmptyMessageDelayed(handler_key.UNLOCK.ordinal(), Lock_Time);
					}
				});
		showUiState(UI_STATE.MENU);
	}

	private void initEvent() {
		tvTiming.setOnClickListener(this);
		tvDelay.setOnClickListener(this);
		tvTimingTime.setOnClickListener(this);
		tvDelayTime.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		rlStartTimeSetting.setOnClickListener(this);
		rlEndTimeSetting.setOnClickListener(this);

		tbTiming.setOnCheckedChangeListener(this);
		tbDelay.setOnCheckedChangeListener(this);
	}

	private void showUiState(UI_STATE ui) {
		uiNow = ui;
		switch (ui) {
		case MENU:
			tvTitle.setText(R.string.appoinment);
			llAppointmentMenu.setVisibility(View.VISIBLE);
			llSetAppointment.setVisibility(View.GONE);
			break;
		case SET_APPOINTMENT:
			tvTitle.setText(R.string.timing_appointment);
			llAppointmentMenu.setVisibility(View.GONE);
			llSetAppointment.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		switch (uiNow) {
		case MENU:
			finish();
			break;
		case SET_APPOINTMENT:
			showUiState(UI_STATE.MENU);
			break;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		mXpgWifiDevice.setListener(deviceListener);
		handler.sendEmptyMessage(handler_key.UPDATE_UI.ordinal());
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.tvTiming:
		case R.id.tvTimingTime:
			showUiState(UI_STATE.SET_APPOINTMENT);
			break;
		case R.id.tvDelay:
		case R.id.tvDelayTime:
			DialogManager.getWheelTimingDialog(this,
					new onDelayTimingChosenListener(),
					getResources().getString(R.string.delay_appointment),
					hourDelay).show();
			break;
		case R.id.rlStartTimeSetting:
			DialogManager.get2WheelTimingDialog(this,
					new onStartTimingChosenListener(),
					getResources().getString(R.string.start_time), hourStart,
					minStart).show();
			break;
		case R.id.rlEndTimeSetting:
			DialogManager.get2WheelTimingDialog(this,
					new onEndTimingChosenListener(),
					getResources().getString(R.string.end_time), hourEnd,
					minEnd).show();
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean isCheck) {
		switch (arg0.getId()) {
		case R.id.tbDelayFlag:
			tvDelay.setSelected(isCheck);
			mCenter.cDelayOn(mXpgWifiDevice, isCheck);
			break;
		case R.id.tbTimingFlag:
			tvTiming.setSelected(isCheck);
			mCenter.cTimingOn(mXpgWifiDevice, isCheck);
			break;
		}

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
	private void setTimingTime(boolean isTurnOn, int startTime, int endTime) {
		if (isTurnOn) {
			tvTimingTime.setVisibility(View.VISIBLE);
			tvTiming.setSelected(true);
			tbTiming.setChecked(true);
		} else {
			tvTimingTime.setVisibility(View.GONE);
			tvTiming.setSelected(false);
			tbTiming.setChecked(false);
		}
		int minOn = 0;
		int minOff = 0;
		int hourOff = 0;
		int hourOn = 0;

		if (startTime != -1) {
			minOn = startTime % 60;
			hourOn = startTime / 60;
			hourStart = hourOn;
			minStart = minOn;
			tvTimingStart.setText(String.format("%d:%02d", hourOn, minOn));
		}

		if (endTime != -1) {
			minOff = endTime % 60;
			hourOff = endTime / 60;
			hourEnd = hourOff;
			minEnd = minOff;
			tvTimingEnd.setText(String.format("%d:%02d", hourOff, minOff));
		}

		if (startTime != -1 && endTime != -1)
			tvTimingTime.setText(String.format("%02d:%02d至%02d:%02d", hourOn,
					minOn, hourOff, minOff));
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
	private void setDelayTime(boolean isTurnOn, int delayTime) {
		if (isTurnOn) {
			tvDelayTime.setVisibility(View.VISIBLE);
			tvDelay.setSelected(true);
			tbDelay.setChecked(true);
		} else {
			tvDelayTime.setVisibility(View.GONE);
			tvDelay.setSelected(false);
			tbDelay.setChecked(false);
		}
		// int min = delayTime % 60;
		int hour = delayTime / 60;
		hourDelay = hour;
		tvDelayTime.setText(String.format("%dh后", hour));
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
		deviceDataMap = dataMap;
		handler.sendEmptyMessage(handler_key.RECEIVED.ordinal());
	}

	private int bytes2Integer(ArrayList<Boolean> mList) {
		int result = 0;
		for (int i = 0; i < 8; i++) {
			if (mList.get(i)) {
				result += (Math.pow(2, i));
			}
		}

		return result;
	}

	private class onStartTimingChosenListener implements
			On2TimingChosenListener {

		@Override
		public void timingChosen(int HourTime, int MinTime) {
			isLock=true;
			handler.removeMessages(handler_key.UNLOCK.ordinal());
			mCenter.cTimingStart(mXpgWifiDevice, HourTime, MinTime);
			setTimingTime(true, HourTime*60+MinTime,-1);
			handler.sendEmptyMessageDelayed(handler_key.UNLOCK.ordinal(), Lock_Time);
		}

	}

	private class onEndTimingChosenListener implements On2TimingChosenListener {

		@Override
		public void timingChosen(int HourTime, int MinTime) {
			isLock=true;
			handler.removeMessages(handler_key.UNLOCK.ordinal());
			mCenter.cTimingEnd(mXpgWifiDevice, HourTime, MinTime);
			setTimingTime(true,-1,HourTime*60+MinTime);
			handler.sendEmptyMessageDelayed(handler_key.UNLOCK.ordinal(), Lock_Time);
		}

	}

	private class onDelayTimingChosenListener implements OnTimingChosenListener {

		@Override
		public void timingChosen(int time) {
			isLock=true;
			handler.removeMessages(handler_key.UNLOCK.ordinal());
			mCenter.cDelayTime(mXpgWifiDevice, time, 0);
			setDelayTime(true, time*60);
			handler.sendEmptyMessageDelayed(handler_key.UNLOCK.ordinal(), Lock_Time);
			
		}

	}
}
