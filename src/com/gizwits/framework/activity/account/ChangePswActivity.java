/**
 * Project Name:XPGSdkV4AppBase
 * File Name:ChangePswActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:44:53
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

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.framework.widget.MyInputFilter;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc

/**
 * ClassName: Class ChangePswActivity. <br/>
 * 修改密码，该类主要用于用户通过旧密码修改密码。<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class ChangePswActivity extends BaseActivity implements OnClickListener {

	/**
	 * The iv TopBar leftBtn.
	 */
	private ImageView ivBack;

	/**
	 * The et user name.
	 */
	private EditText etPswOld;

	/**
	 * The rl result.
	 */
	private RelativeLayout rlResult;

	/**
	 * The tv result.
	 */
	private TextView tvResult;

	/**
	 * The et Input Password.
	 */
	private EditText etPswNew;

	/**
	 * The tb show passwrod.
	 */
	private ToggleButton tbPswFlag;

	/**
	 * The btn confirm the word.
	 */
	private Button btnConfirm;

	/**
	 * The dialog mDialog.
	 */
	private Dialog mDialog;

	/** 用户输入的新密码 */
	private String newPsw;

	/** 用户输入的旧密码 */
	private String oldPsw;
	
	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2015-3-23 14:00:00 <br/>
	 * 
	 * @author Sunny
	 */
	private enum handler_key {

		/**
		 * 关闭
		 */
		CLOSE,

		/**
		 * 修改成功
		 */
		CHANGE_SUCCESS,

		/**
		 * 修改失败
		 */
		CHANGE_FAIL,

	}
	
	/**
	 * The handler.
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {

			case CLOSE:
				ChangePswActivity.this.finish();
				break;

			case CHANGE_SUCCESS:
				setmanager.setPassword(newPsw);
				tvResult.setText(R.string.change_success);
				rlResult.setVisibility(View.VISIBLE);
				handler.sendEmptyMessageDelayed(handler_key.CLOSE.ordinal(), 1000);
				break;

			case CHANGE_FAIL:
				tvResult.setText(R.string.change_fail);
				rlResult.setVisibility(View.VISIBLE);
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
		setContentView(R.layout.activity_change_pwd);
		initViews();
		initEvents();
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ivBack.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		rlResult.setOnClickListener(this);
		tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					etPswOld.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					etPswNew.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					etPswOld.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					etPswNew.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}

			}
		});

	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		etPswOld = (EditText) findViewById(R.id.etPswOld);
		etPswNew = (EditText) findViewById(R.id.etPswNew);
		tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
		tvResult = (TextView) findViewById(R.id.tvResult);
		rlResult = (RelativeLayout) findViewById(R.id.rlResult);

		MyInputFilter filter= new MyInputFilter();
		etPswOld.setFilters(new InputFilter[] { filter });
		etPswNew.setFilters(new InputFilter[] { filter });
		
		mDialog = DialogManager.getPswChangeDialog(this, new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!NetworkUtils.isNetworkConnected(ChangePswActivity.this)) {
					ToastUtils.showShort(ChangePswActivity.this, "网络未连接");return;
				}
				changePsw(oldPsw, newPsw);
				DialogManager.dismissDialog(ChangePswActivity.this, mDialog);
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
		if (v.getId() != R.id.ivBack
				&& rlResult.getVisibility() == View.VISIBLE) {
			rlResult.setVisibility(View.GONE);
			return;
		}

		switch (v.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnConfirm:
			if (!NetworkUtils.isNetworkConnected(this)) {
				ToastUtils.showShort(this, "网络未连接");return;
			}
			oldPsw = etPswOld.getText().toString();
			newPsw = etPswNew.getText().toString();
			if (StringUtils.isEmpty(oldPsw)) {
				ToastUtils.showShort(ChangePswActivity.this, "请输入旧的密码");
				return;
			}
			if (StringUtils.isEmpty(newPsw)) {
				ToastUtils.showShort(ChangePswActivity.this, "请输入新的密码");
				return;
			}
			if (newPsw.length() < 6 || newPsw.length() > 16) {
				Toast.makeText(this, "密码长度应为6~16", Toast.LENGTH_SHORT).show();
				return;
			}
//			if (!oldPsw.equals(setmanager.getPassword())) {
//				ToastUtils.showShort(ChangePswActivity.this, "请输入正确的用户密码");
//				return;
//			}

			mDialog.show();
			break;
		}

	}

	/**
	 * 处理修改密码动作
	 * 
	 * @param oldPsw
	 *            the old psw
	 * @param newPsw
	 *            the new psw
	 */
	private void changePsw(String oldPsw, String newPsw) {
		mCenter.cChangeUserPassword(setmanager.getToken(), oldPsw, newPsw);
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
		if (error == 0) {
			handler.sendEmptyMessage(handler_key.CHANGE_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.CHANGE_FAIL.ordinal());
		}
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
