/**
 * Project Name:XPGSdkV4AppBase
 * File Name:RegisterActivity.java
 * Package Name:com.xpg.appbase.activity.account
 * Date:2014-11-27 12:16:44
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
package com.xpg.appbase.activity.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

// TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class RegisterActivity. <br/>
 * 用户注册<br/>
 * date: 2014-11-27 12:08:00 <br/>
 * 
 * @author Lien
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {

	/** The tv phone switch. */
	private TextView tvPhoneSwitch;

	/** The et name. */
	private EditText etName;

	/** The et input code. */
	private EditText etInputCode;

	/** The et input psw. */
	private EditText etInputPsw;

	/** The btn get code. */
	private Button btnGetCode;

	/** The btn re get code. */
	private Button btnReGetCode;

	/** The btn sure. */
	private Button btnSure;

	/** The ll input code. */
	private LinearLayout llInputCode;

	/** The ll input psw. */
	private LinearLayout llInputPsw;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.xpg.appbase.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initViews();
		initEvents();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		tvPhoneSwitch = (TextView) findViewById(R.id.tvPhoneSwitch);
		etName = (EditText) findViewById(R.id.etName);
		etInputCode = (EditText) findViewById(R.id.etInputCode);
		etInputPsw = (EditText) findViewById(R.id.etInputPsw);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
		btnSure = (Button) findViewById(R.id.btnSure);
		llInputCode = (LinearLayout) findViewById(R.id.llInputCode);
		llInputPsw = (LinearLayout) findViewById(R.id.llInputPsw);

	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnGetCode.setOnClickListener(this);
		btnReGetCode.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		tvPhoneSwitch.setOnClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnGetCode:
			
			break;
		case R.id.btnReGetCode:
			
			break;
		case R.id.btnSure:
			
			break;
		case R.id.tvPhoneSwitch:
			
			break;
		}

	}
}
