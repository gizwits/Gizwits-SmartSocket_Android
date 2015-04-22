/**
 * Project Name:XPGSdkV4AppBase
 * File Name:ForgetPswActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:44:57
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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.widget.MyInputFilter;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

/**
 * ClassName: Class ForgetPswActivity. <br/>
 * 忘记密码，该类主要包含了两个修改密码的方法，手机号注册的用户通过获取验证码修改密码，邮箱注册的用户需要通过注册邮箱的重置邮件进行重置。<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class ForgetPswActivity extends BaseActivity implements OnClickListener {
	/**
	 * The tv dialog.
	 */
	 private TextView tvDialog;
	
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
	 * The et input email.
	 */
	private EditText etInputEmail;

	/**
	 * The btn get code.
	 */
	private Button btnGetCode;

	/**
	 * The btn re get code.
	 */
	private Button btnReGetCode;

	/**
	 * The btn sureEmail.
	 */
	private Button btnSureEmail;

	/**
	 * The btn phoneReset.
	 */
	private Button btnPhoneReset;

	/**
	 * The btn emailReset.
	 */
	private Button btnEmailReset;

	/**
	 * The ll input menu.
	 */
	private LinearLayout llInputMenu;

	/**
	 * The ll input phone.
	 */
	private LinearLayout llInputPhone;

	/**
	 * The rl input email.
	 */
	private RelativeLayout rlInputEmail;
	
	/**
	 * The rl dialog.
	 */
	private RelativeLayout rlDialog;

	/**
	 * The btn sure.
	 */
	private Button btnSure;

	/**
	 * The iv back.
	 */
	private ImageView ivBack;

	/**
	 * The tb psw flag.
	 */
	private ToggleButton tbPswFlag;

	/**
	 * The ui_statu statuNow.
	 */
	private ui_statu statuNow;

	/**
	 * The secondleft.
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
		 * 修改成功
		 */
		CHANGE_SUCCESS,

		/**
		 * Toast弹出通知
		 */
		TOAST,

	}

	/**
	 * ClassName: Enum ui_statu. <br/>
	 * UI状态枚举类<br/>
	 * date: 2014-12-3 10:52:52 <br/>
	 * 
	 * @author Lien
	 */
	private enum ui_statu {

		/**
		 * 主菜单
		 */
		DEFAULT,

		/**
		 * 通过手机重置密码
		 */
		PHONE,

		/**
		 * 通过邮箱重置密码
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
					btnReGetCode.setText(R.string.forget_password_get_verifycode2);
					btnReGetCode
							.setBackgroundResource(R.drawable.button_blue_short);
				} else {
					btnReGetCode.setText(secondleft +""+getResources().getText(R.string.forget_password_get_verifycode3));

				}
				break;

			case CHANGE_SUCCESS:
				finish();
				break;

			case TOAST:
				tvDialog.setText((String) msg.obj);
				rlDialog.setVisibility(View.VISIBLE);
				dialog.cancel();
				break;
			}
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
		setContentView(R.layout.activity_forget_reset);
		initViews();
		initEvents();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		tvDialog=(TextView) findViewById(R.id.tvDialog);
		etName = (EditText) findViewById(R.id.etName);
		etInputCode = (EditText) findViewById(R.id.etInputCode);
		etInputPsw = (EditText) findViewById(R.id.etInputPsw);
		etInputEmail = (EditText) findViewById(R.id.etInputEmail);
		btnGetCode = (Button) findViewById(R.id.btnGetCode);
		btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
		btnSure = (Button) findViewById(R.id.btnSure);
		btnSureEmail = (Button) findViewById(R.id.btnSureEmail);
		btnPhoneReset = (Button) findViewById(R.id.btnPhoneReset);
		btnEmailReset = (Button) findViewById(R.id.btnEmailReset);
		llInputMenu = (LinearLayout) findViewById(R.id.llInputMenu);
		llInputPhone = (LinearLayout) findViewById(R.id.llInputPhone);
		rlInputEmail = (RelativeLayout) findViewById(R.id.rlInputEmail);
		rlDialog=(RelativeLayout) findViewById(R.id.rlDialog);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
		toogleUI(ui_statu.DEFAULT);
		dialog = new ProgressDialog(this);
		dialog.setMessage("处理中，请稍候...");
		
		MyInputFilter filter= new MyInputFilter();
		etInputPsw.setFilters(new InputFilter[] { filter });
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		rlDialog.setOnClickListener(this);
		btnGetCode.setOnClickListener(this);
		btnReGetCode.setOnClickListener(this);
		btnSureEmail.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnPhoneReset.setOnClickListener(this);
		btnEmailReset.setOnClickListener(this);
		// tvPhoneSwitch.setOnClickListener(this);
		ivBack.setOnClickListener(this);
		tbPswFlag
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							etInputPsw
									.setInputType(InputType.TYPE_CLASS_TEXT
											| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
						} else {
							etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
		// case R.id.btnGetCode:
		// String name = etName.getText().toString().trim();
		// if(StringUtils.isEmpty(name)||name.length() != 11)
		// ToastUtils.showShort(this, "请输入正确的账号。");
		//
		// sendVerifyCode(name);
		// break;
		case R.id.btnReGetCode:
			if (!NetworkUtils.isWifiConnected(this)) {
				ToastUtils.showShort(this, "网络未连接");return;
			}
			String phone2 = etName.getText().toString().trim();
			if (StringUtils.isEmpty(phone2) || phone2.length() != 11){
				ToastUtils.showShort(this, "请输入正确的手机号码。");
				return;
			}

			sendVerifyCode(phone2);
			break;
		case R.id.btnSure:
			if (!NetworkUtils.isWifiConnected(this)) {
				ToastUtils.showShort(this, "网络未连接");return;
			}
			doChangePsw();
			break;
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnSureEmail:
			if (!NetworkUtils.isWifiConnected(this)) {
				ToastUtils.showShort(this, "网络未连接");return;
			}
			String email = etInputEmail.getText().toString().trim();
			if (StringUtils.isEmpty(email) || !email.contains("@")){
				ToastUtils.showShort(this, "请输入正确的账号。");
				return;
			}

			getEmail(email);
			break;
		case R.id.btnPhoneReset:
			toogleUI(ui_statu.PHONE);
			break;
		case R.id.btnEmailReset:
			toogleUI(ui_statu.EMAIL);
			break;
		case R.id.rlDialog:
			rlDialog.setVisibility(View.GONE);
			break;
		}
	}

	@Override
	public void onBackPressed() {
		if(rlDialog.getVisibility()==View.VISIBLE){
			rlDialog.setVisibility(View.GONE);
			return;
		}
		switch (statuNow) {
		case DEFAULT:
			finish();
			break;
		case PHONE:
		case EMAIL:
			toogleUI(ui_statu.DEFAULT);
			break;
		}
	}

	/**
	 * Toogle ui.
	 * 
	 * @param statu
	 *            the statu
	 */
	private void toogleUI(ui_statu statu) {
		statuNow = statu;
		switch (statu) {
		case DEFAULT:
			llInputMenu.setVisibility(View.VISIBLE);
			llInputPhone.setVisibility(View.GONE);
			rlInputEmail.setVisibility(View.GONE);
			etInputCode.setText("");
			etInputEmail.setText("");
			etInputPsw.setText("");
			etName.setText("");
			break;
		case PHONE:
			llInputMenu.setVisibility(View.GONE);
			llInputPhone.setVisibility(View.VISIBLE);
			rlInputEmail.setVisibility(View.GONE);
			break;
		case EMAIL:
			llInputMenu.setVisibility(View.GONE);
			llInputPhone.setVisibility(View.GONE);
			rlInputEmail.setVisibility(View.VISIBLE);
			break;
		}
	}

	/**
	 * Gets the email.
	 * 
	 * @param email
	 *            the email
	 * @return the email
	 */
	private void getEmail(String email) {
		mCenter.cChangePassworfByEmail(email);
		dialog.show();
	}

	/**
	 * 执行手机号重置密码操作
	 */
	private void doChangePsw() {

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
		mCenter.cChangeUserPasswordWithCode(phone, code, password);
		dialog.show();
	}

	/**
	 * 发送验证码
	 * 
	 * @param phone
	 *            the phone
	 */
	private void sendVerifyCode(final String phone) {
		dialog.show();
		btnReGetCode.setEnabled(false);
		btnReGetCode.setBackgroundResource(R.drawable.button_gray_short);
		secondleft = 60;
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				// 倒计时通知
				handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
			}
		}, 1000, 1000);
		// 发送请求验证码指令
		mCenter.cRequestSendVerifyCode(phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.framework.activity.BaseActivity#didRequestSendVerifyCode(int,
	 * java.lang.String)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.framework.activity.BaseActivity#didChangeUserPassword(int,
	 * java.lang.String)
	 */
	@Override
	protected void didChangeUserPassword(int error, String errorMessage) {
		if (error == 0) {// 修改成功
			Message msg = new Message();
			msg.what = handler_key.TOAST.ordinal();
			if (statuNow == ui_statu.EMAIL) {
				Drawable img = getResources().getDrawable(R.drawable.slib_tick);
				img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
				tvDialog.setCompoundDrawables(img, null, null, null);
				msg.obj = "已发送至您的邮箱，\n请登录邮箱查收！";
			} else {
				msg.obj = "设置成功";
			}
			handler.sendMessage(msg);
			handler.sendEmptyMessageDelayed(
					handler_key.CHANGE_SUCCESS.ordinal(), 2000);

		} else {// 修改失败
			Message msg = new Message();
			msg.what = handler_key.TOAST.ordinal();
			msg.obj = errorMessage;
			handler.sendMessage(msg);
		}
		super.didChangeUserPassword(error, errorMessage);
	}
}
