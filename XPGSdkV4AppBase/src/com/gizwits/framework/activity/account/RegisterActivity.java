/**
 * Project Name:XPGSdkV4AppBase
 * File Name:RegisterActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:45:08
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

import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.powersocket.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

// TODO: Auto-generated Javadoc

/**
 * ClassName: Class RegisterActivity. <br/>
 * 用户注册，该类用于新用户的注册<br/>
 * 
 * @author Lien
 */

public class RegisterActivity extends BaseActivity implements OnClickListener {

	/**
	 * The tv phone switch.
	 */
	private TextView tvPhoneSwitch;

	/** The tv tips. */
	private TextView tvTips;

	/**
	 * The et name.
	 */
	private EditText etName;

	/**
	 * The et input code.
	 */
	private EditText etInputCode;

	/**
	 * The et input psw.
	 */
	private EditText etInputPsw;

	/**
	 * The btn get code.
	 */
	private Button btnGetCode;

	/**
	 * The btn re get code.
	 */
	private Button btnReGetCode;

	/**
	 * The btn sure.
	 */
	private Button btnSure;

	/**
	 * The ll input code.
	 */
	private LinearLayout llInputCode;

	/**
	 * The ll input psw.
	 */
	private LinearLayout llInputPsw;

	/**
	 * The iv back.
	 */
	private ImageView ivBack;

	/**
	 * The iv step.
	 */
	private ImageView ivStep;

	/**
	 * The tb psw flag.
	 */
	private ToggleButton tbPswFlag;

	/**
	 * 是否邮箱注册标识位
	 */
	private boolean isEmail = false;

	/**
	 * 验证码重发倒计时
	 */
	int secondleft = 60;

	/**
	 * The timer.
	 */
	Timer timer;

	/**
	 * The dialog.
	 */
	ProgressDialog dialog;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/**
		 * 倒计时通知
		 */
		TICK_TIME,

		/**
		 * 注册成功
		 */
		REG_SUCCESS,

		/**
		 * Toast弹出通知
		 */
		TOAST,

	}

	/**
	 * ClassName: Enum ui_statu. <br/>
	 * <br/>
	 * date: 2014-12-3 10:52:52 <br/>
	 * 
	 * @author Lien
	 */
	private enum ui_statue {

		/**
		 * 默认状态
		 */
		DEFAULT,

		/**
		 * 手机注册
		 */
		PHONE,

		/**
		 * email注册
		 */
		EMAIL,
	}

	/**
	 * The handler.
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {

			case TICK_TIME:
				secondleft--;
				if (secondleft <= 0) {
					timer.cancel();
					btnReGetCode.setEnabled(true);
					btnReGetCode.setText("重新获取验证码");
					btnReGetCode
							.setBackgroundResource(R.drawable.button_blue_short);
				} else {
					btnReGetCode.setText(secondleft + "秒后\n重新获取");

				}
				break;

			case REG_SUCCESS:
				ToastUtils.showShort(RegisterActivity.this, (String) msg.obj);
				dialog.cancel();
				IntentUtils.getInstance().startActivity(RegisterActivity.this,
						SearchDeviceActivity.class);
				break;

			case TOAST:
				ToastUtils.showShort(RegisterActivity.this, (String) msg.obj);
				dialog.cancel();
				break;
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.aircondition.activity.BaseActivity#onCreate(android.os.Bundle
	 * )
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
		tvTips = (TextView) findViewById(R.id.tvTips);
		tvPhoneSwitch = (TextView) findViewById(R.id.tvPhoneSwitch);
		etName = (EditText) findViewById(R.id.etName);
		etInputCode = (EditText) findViewById(R.id.etInputCode);
		etInputPsw = (EditText) findViewById(R.id.etInputPsw);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
		btnSure = (Button) findViewById(R.id.btnSure);
		llInputCode = (LinearLayout) findViewById(R.id.llInputCode);
		llInputPsw = (LinearLayout) findViewById(R.id.llInputPsw);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivStep = (ImageView) findViewById(R.id.ivStep);
		tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
		toogleUI(ui_statue.DEFAULT);
		dialog = new ProgressDialog(this);
		dialog.setMessage("处理中，请稍候...");
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnGetCode.setOnClickListener(this);
		btnReGetCode.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		tvPhoneSwitch.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					etInputPsw.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
				} else {
					etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					etInputPsw.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
				}

			}

		});
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
			String phone = etName.getText().toString().trim();
			if (!StringUtils.isEmpty(phone) && phone.length() == 11) {
				toogleUI(ui_statue.PHONE);
				sendVerifyCode(phone);
			} else {
				ToastUtils.showShort(this, "请输入正确的手机号码。");
			}

			break;
		case R.id.btnReGetCode:
			String phone2 = etName.getText().toString().trim();
			if (!StringUtils.isEmpty(phone2) && phone2.length() == 11) {
				toogleUI(ui_statue.PHONE);
				sendVerifyCode(phone2);
			} else {
				ToastUtils.showShort(this, "请输入正确的手机号码。");
			}
			break;
		case R.id.btnSure:
			doRegister();
			break;
		case R.id.tvPhoneSwitch:
			if (isEmail) {
				toogleUI(ui_statue.PHONE);
				isEmail = false;
			} else {
				toogleUI(ui_statue.EMAIL);
				isEmail = true;
			}
			break;
		case R.id.ivBack:
			onBackPressed();
			break;
		}

	}

	/**
	 * Toogle ui.
	 * 
	 * @param statue
	 *            the statu
	 */
	private void toogleUI(ui_statue statue) {
		if (statue == ui_statue.DEFAULT) {
			llInputCode.setVisibility(View.GONE);
			llInputPsw.setVisibility(View.GONE);
			btnSure.setVisibility(View.GONE);
			btnGetCode.setVisibility(View.VISIBLE);
			etName.setHint("手机号");
			etName.setText("");
			tvTips.setVisibility(View.GONE);
		} else if (statue == ui_statue.PHONE) {
			llInputCode.setVisibility(View.VISIBLE);
			llInputPsw.setVisibility(View.VISIBLE);
			btnSure.setVisibility(View.VISIBLE);
			btnGetCode.setVisibility(View.GONE);
			etName.setHint("手机号");
			tvPhoneSwitch.setText("邮箱注册");
			tvTips.setVisibility(View.GONE);
		} else {
			llInputCode.setVisibility(View.GONE);
			btnGetCode.setVisibility(View.GONE);
			llInputPsw.setVisibility(View.VISIBLE);
			btnSure.setVisibility(View.VISIBLE);
			etName.setHint("邮箱");
			etName.setText("");
			tvPhoneSwitch.setText("手机注册");
			tvTips.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 处理注册动作
	 */
	private void doRegister() {
		if (!isEmail) {

			String phone = etName.getText().toString().trim();
			String code = etInputCode.getText().toString().trim();
			String password = etInputPsw.getText().toString();
			if (phone.length() != 11) {
				Toast.makeText(this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
				return;
			}
			if (code.length() == 0) {
				Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.contains(" ")) {
				Toast.makeText(this, "密码不能有空格", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.length() < 6 || password.length() > 16) {
				Toast.makeText(this, "密码长度应为6~16", Toast.LENGTH_SHORT).show();
				return;
			}
			mCenter.cRegisterPhoneUser(phone, code, password);
			Log.e("Register", "phone=" + phone + ";code=" + code + ";password="
					+ password);
			dialog.show();
		} else {
			String mail = etName.getText().toString().trim();
			String password = etInputPsw.getText().toString();
			if (!mail.contains("@")) {
				Toast.makeText(this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.contains(" ")) {
				Toast.makeText(this, "密码不能有空格", Toast.LENGTH_SHORT).show();
				return;
			}
			if (password.length() < 6 || password.length() > 16) {
				Toast.makeText(this, "密码长度应为6~16", Toast.LENGTH_SHORT).show();
				return;
			}
			mCenter.cRegisterMailUser(mail, password);
			Log.e("Register", "mail=" + mail + ";password=" + password);
			dialog.show();
		}

	}

	/**
	 * 处理发送验证码动作
	 * 
	 * @param phone
	 *            the phone
	 */
	private void sendVerifyCode(final String phone) {
		// TODO Auto-generated method stub
		dialog.show();
		btnReGetCode.setEnabled(false);
		btnReGetCode.setBackgroundResource(R.drawable.button_gray_short);
		secondleft = 60;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
			}
		}, 1000, 1000);

		mCenter.cRequestSendVerifyCode(phone);
	}

	/*
	 * 用户注册结果回调接口.
	 */
	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#didRegisterUser(int, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void didRegisterUser(int error, String errorMessage, String uid,
			String token) {
		Log.i("error message uid token", error + " " + errorMessage + " " + uid
				+ " " + token);
		if (!uid.equals("") && !token.equals("")) {// 注册成功
			Message msg = new Message();
			msg.what = handler_key.REG_SUCCESS.ordinal();
			msg.obj = "注册成功";
			handler.sendMessage(msg);
			setmanager.setUid(uid);
			setmanager.setToken(token);

		} else {// 注册失败
			Message msg = new Message();
			msg.what = handler_key.TOAST.ordinal();
			msg.obj = errorMessage;
			handler.sendMessage(msg);
		}
	}

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#didRequestSendVerifyCode(int, java.lang.String)
	 */
	@Override
	protected void didRequestSendVerifyCode(int error, String errorMessage) {
		Log.i("error message ", error + " " + errorMessage);
		if (error == 0) {// 发送成功
			Message msg = new Message();
			msg.what = handler_key.TOAST.ordinal();
			msg.obj = "发送成功";
			handler.sendMessage(msg);
		} else {// 发送失败
			Message msg = new Message();
			msg.what = handler_key.TOAST.ordinal();
			msg.obj = errorMessage;
			handler.sendMessage(msg);
		}
	}

}
