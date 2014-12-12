/**
 * Project Name:XPGSdkV4AppBase
 * File Name:AirlinkActivity.java
 * Package Name:com.xpg.appbase.activity.onboarding
 * Date:2014-12-10 14:59:04
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

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

// TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class AirlinkActivity. <br/>
 * <br/>
 * date: 2014-12-10 14:26:03 <br/>
 * 
 * @author Lien
 */
public class AirlinkActivity extends BaseActivity implements OnClickListener {

	/** The btn config. */
	private Button btnConfig;
	
	/** The btn retry. */
	private Button btnRetry;
	
	/** The btn softap. */
	private Button btnSoftap;

	/** The ll start config. */
	private LinearLayout llStartConfig;

	/** The ll configing. */
	private LinearLayout llConfiging;
	
	/** The ll config failed. */
	private LinearLayout llConfigFailed;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpg.appbase.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_airlink);
		initViews();
		initEvents();
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnConfig.setOnClickListener(this);
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		btnConfig = (Button) findViewById(R.id.btnConfig);
		btnRetry = (Button) findViewById(R.id.btnRetry);
		btnSoftap = (Button) findViewById(R.id.btnSoftap);
		llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);
		llConfiging = (LinearLayout) findViewById(R.id.llConfiging);
		llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
		llStartConfig.setVisibility(View.VISIBLE);
		llConfiging.setVisibility(View.GONE);
		llConfigFailed.setVisibility(View.GONE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnConfig:
			// TODO airlink配置
			break;
		case R.id.btnRetry:
			// TODO 重试
			break;
		case R.id.btnSoftap:
			// TODO spftap配置
			break;

		}

	}

	/**
	 * Start airlink.
	 */
	private void startAirlink() {

	}

	/**
	 * Start bind.
	 */
	private void startBind() {

	}

}
