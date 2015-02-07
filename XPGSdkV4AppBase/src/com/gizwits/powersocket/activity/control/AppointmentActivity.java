package com.gizwits.powersocket.activity.control;

import java.util.concurrent.ConcurrentHashMap;



import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.config.JsonKeys;
import com.gizwits.framework.utils.StringUtils;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AppointmentActivity extends BaseActivity implements OnClickListener,
		OnCheckedChangeListener {

	private TextView tvTiming;
	private TextView tvDelay;
	private TextView tvTimingTime;
	private TextView tvDelayTime;
	private ToggleButton tbTiming;
	private ToggleButton tbDelay;
	private ImageView ivBack;
	private GridView gvDateSelect;

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
				break;
			case UPDATE_UI:
				if (statuMap != null && statuMap.size() > 0) {
					//定时更新
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
					//延时更新
					isTurnOn = (Boolean) statuMap
							.get(JsonKeys.COUNT_DOWN_ON_OFF);
					String min = (String) statuMap
							.get(JsonKeys.COUNT_DOWN_MINUTE);
					if (!StringUtils.isEmpty(min))
						setDelayTime(isTurnOn, Integer.parseInt(min));
				}
				break;
			case ALARM:
				break;
			case DISCONNECTED:
				break;
			case GET_STATUE:
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
		gvDateSelect=(GridView) findViewById(R.id.gvDateSelect);
		tvTiming = (TextView) findViewById(R.id.tvTiming);
		tvDelay = (TextView) findViewById(R.id.tvDelay);
		tvTimingTime = (TextView) findViewById(R.id.tvTimingTime);
		tvDelayTime = (TextView) findViewById(R.id.tvDelayTime);
		tbTiming = (ToggleButton) findViewById(R.id.tbTimingFlag);
		tbDelay = (ToggleButton) findViewById(R.id.tbDelayFlag);
		ivBack = (ImageView) findViewById(R.id.ivBack);
	}

	private void initEvent() {
		tvTiming.setOnClickListener(this);
		tvDelay.setOnClickListener(this);
		tvTimingTime.setOnClickListener(this);
		tvDelayTime.setOnClickListener(this);
		ivBack.setOnClickListener(this);

		tbTiming.setOnCheckedChangeListener(this);
		tbDelay.setOnCheckedChangeListener(this);
	}

	@Override
	public void onBackPressed() {
		finish();
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
			break;
		case R.id.tvDelay:
		case R.id.tvDelayTime:
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
	private void setTimingTime(boolean isTurnOn,int startTime,int endTime){
		if(isTurnOn){
			tvTimingTime.setVisibility(View.VISIBLE);
			tvTiming.setSelected(true);
			tbTiming.setChecked(true);
		}else{
			tvTimingTime.setVisibility(View.GONE);
			tvTiming.setSelected(false);
			tbTiming.setChecked(false);
		}
		int minOn = startTime % 60;
		int hourOn = startTime / 60;
		int minOff = endTime % 60;
		int hourOff = endTime / 60;
		tvTimingTime.setText(String.format("%02d:%02d至%02d:%02d", hourOn, minOn,
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
	private void setDelayTime(boolean isTurnOn, int delayTime){
		if(isTurnOn){
			tvDelayTime.setVisibility(View.VISIBLE);
			tvDelay.setSelected(true);
			tbDelay.setChecked(true);
		}else{
			tvDelayTime.setVisibility(View.GONE);
			tvDelay.setSelected(false);
			tbDelay.setChecked(false);
		}
//		int min = delayTime % 60;
		int hour = delayTime / 60;
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
		handler.sendEmptyMessageDelayed(handler_key.UPDATE_UI.ordinal(),1000);
	}

}
