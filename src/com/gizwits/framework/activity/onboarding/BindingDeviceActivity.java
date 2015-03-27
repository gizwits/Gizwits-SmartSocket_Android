/**
 * Project Name:XPGSdkV4AppBase
 * File Name:BindingDeviceActivity.java
 * Package Name:com.gizwits.framework.activity.onboarding
 * Date:2015-1-27 14:45:59
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
package com.gizwits.framework.activity.onboarding;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.xpg.common.system.IntentUtils;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class BindingDeviceActivity. <br/> 
 * 绑定设备
 * <br/>
 * date: 2015-1-27 14:45:59 <br/> 
 *
 * @author Lien
 */
public class BindingDeviceActivity extends BaseActivity implements
		OnClickListener {
	
	/** The ll start config. */
	private LinearLayout llStartConfig;
	
	/** The ll config failed. */
	private LinearLayout llConfigFailed;
	
	/** The tv press. */
	private TextView tvPress;
	
	/** The btn retry. */
	private Button btnRetry;

	/** The device. */
	private String did="";

	/**
	 *  
	 * ClassName: Enum handler_key. <br/> 
	 * <br/>
	 * date: 2015-1-27 14:45:59 <br/> 
	 *
	 * @author Lien
	 */
	private enum handler_key {

		/** The bind success. */
		BIND_SUCCESS,

		/** The bind failed. */
		BIND_FAILED,

	}

	/**
	 * The handler.
	 */
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
			case BIND_SUCCESS:
				IntentUtils.getInstance().startActivity(BindingDeviceActivity.this,
						DeviceListActivity.class);
				finish();
				break;
			case BIND_FAILED:
				llStartConfig.setVisibility(View.GONE);
				llConfigFailed.setVisibility(View.VISIBLE);
				break;
			}
		}
	};

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_binding);
		initViews();
		initEvents();
		initDatas();
		bindDevice();
	}

	/**
	 * Inits the datas.
	 */
	private void initDatas() {
		if (getIntent() != null) {
			did = getIntent().getStringExtra("did");
		}
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		tvPress.setOnClickListener(this);
		btnRetry.setOnClickListener(this);
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
		llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);
		tvPress = (TextView) findViewById(R.id.tvPress);
		btnRetry = (Button) findViewById(R.id.btnRetry);
		llStartConfig.setVisibility(View.VISIBLE);
		llConfigFailed.setVisibility(View.GONE);
		
		tvPress.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvPress:
			handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
			break;
		case R.id.btnRetry:
			goBack();
			break;
		}
	}

	/**
	 * Bind device.
	 */
	private void bindDevice() {
		mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(),
				did, null, "");
	}

	private void goBack(){
		IntentUtils.getInstance().startActivity(BindingDeviceActivity.this,
				SearchDeviceActivity.class);
		finish();
	}
	
	
	@Override
	public void onBackPressed() {
	}

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#didBindDevice(int, java.lang.String, java.lang.String)
	 */
	@Override
	protected void didBindDevice(int error, String errorMessage, String did) {
		if (error == 0) {
			handler.sendEmptyMessage(handler_key.BIND_SUCCESS.ordinal());
		}else{
			handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
		}
	}
}
