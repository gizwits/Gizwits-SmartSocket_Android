/**
 * Project Name:XPGSdkV4AppBase
 * File Name:LoginActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:45:03
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
package com.gizwits.framework.activity.account;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

// TODO: Auto-generated Javadoc

/**
 * ClassName: Class LoginActivity. <br/>
 * 用户登陆，该类用于账号登陆<br/>
 * 
 * @author Lien
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	/**
	 * The et name.
	 */
	private EditText etName;

	/**
	 * The et psw.
	 */
	private EditText etPsw;

	/**
	 * The tv forgot.
	 */
	private TextView tvForgot;

	/**
	 * The btn login.
	 */
	private Button btnLogin;

	/**
	 * The btn register.
	 */
	private Button btnRegister;

	/**
	 * The dialog.
	 */
	private ProgressDialog dialog;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** 登陆成功. */
		LOGIN_SUCCESS,

		/** 登陆失败. */
		LOGIN_FAIL,

		/** 登录超时. */
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
			// 登陆成功
			case LOGIN_SUCCESS:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
						.show();
				dialog.cancel();
				IntentUtils.getInstance().startActivity(LoginActivity.this,
						DeviceListActivity.class);
				finish();
				break;
			// 登陆失败
			case LOGIN_FAIL:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				Toast.makeText(LoginActivity.this, msg.obj + "",
						Toast.LENGTH_SHORT).show();
				dialog.cancel();
				break;
			// 登录超时
			case LOGIN_TIMEOUT:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				Toast.makeText(LoginActivity.this, "登陆超时", Toast.LENGTH_SHORT)
						.show();
				dialog.cancel();
				break;

			}
		}
	};

	/*
	 * @see
	 * com.gizwits.aircondition.activity.BaseActivity#onCreate(android.os.Bundle
	 * )
	 */
	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
		initEvents();
	}

	/**
	 * 初始化交互监听器.
	 */
	private void initEvents() {

		tvForgot.setOnClickListener(this);
		btnLogin.setOnClickListener(this);
		btnRegister.setOnClickListener(this);

	}

	/**
	 * 初始化空间.
	 */
	private void initViews() {
		etName = (EditText) findViewById(R.id.etName);
		etPsw = (EditText) findViewById(R.id.etPsw);
		tvForgot = (TextView) findViewById(R.id.tvForgot);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		tvForgot.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("登录中，请稍候...");
		if (setmanager.getUserName() != null) {
			etName.setText(setmanager.getUserName());
		}

	}

	/*
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvForgot:
			// 打开忘记密码Activity
			IntentUtils.getInstance().startActivity(this,
					ForgetPswActivity.class);
			break;
		case R.id.btnLogin:
			if (!NetworkUtils.isWifiConnected(this)) {
				ToastUtils.showShort(this, "网络未连接");return;
			}
			// 登陆
			if (StringUtils.isEmpty(etName.getText().toString())) {
				Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
				return;
			}
			if (StringUtils.isEmpty(etPsw.getText().toString())) {
				Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
				return;
			}
			dialog.show();
			final String psw = etPsw.getText().toString().trim();
			final String name = etName.getText().toString().trim();
			// 调用登陆方法
			mCenter.cLogin(name, psw);
			// 15秒后登陆超时
			handler.sendEmptyMessageDelayed(
					handler_key.LOGIN_TIMEOUT.ordinal(), 15000);
			break;
		case R.id.btnRegister:
			if (NetworkUtils.isNetworkConnected(this)) {
				// 打开注册Activity
				IntentUtils.getInstance().startActivity(this,
						RegisterActivity.class);
			}
			break;
		}

	}

	/*
	 * @see com.gizwits.framework.activity.BaseActivity#didUserLogin(int,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#didUserLogin(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void didUserLogin(int error, String errorMessage, String uid,
			String token) {
		if (!uid.isEmpty() && !token.isEmpty()) {// 登陆成功
			setmanager.setUserName(etName.getText().toString().trim());
			setmanager.setPassword(etPsw.getText().toString().trim());
			setmanager.setUid(uid);
			setmanager.setToken(token);
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {// 登陆失败
			Message msg = new Message();
			msg.what = handler_key.LOGIN_FAIL.ordinal();
			msg.obj = errorMessage;
			handler.sendMessage(msg);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		exit();
	}

}
