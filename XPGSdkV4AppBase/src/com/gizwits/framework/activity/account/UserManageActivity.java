/**
 * Project Name:XPGSdkV4AppBase
 * File Name:UserManageActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:45:13
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.gizwits.powersocket.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class UserManageActivity. <br/>
 * 用户管理，用于用户信息管理<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class UserManageActivity extends BaseActivity implements OnClickListener {

	/** The iv TopBar leftBtn. */
	private ImageView ivBack;

	/** The et User name. */
	private TextView tvName;

	/** The btn to update pass page. */
	private Button btnChange;

	/** The btn logout . */
	private Button btnLogout;

	/** The dialog. */
	private Dialog dialog;

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_manage);
		initViews();
		initEvents();

	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnChange.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		ivBack.setOnClickListener(this);

	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		tvName = (TextView) findViewById(R.id.tvName);
		btnChange = (Button) findViewById(R.id.btnChange);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		tvName.setText(setmanager.getUserName());
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnChange:
			IntentUtils.getInstance().startActivity(UserManageActivity.this,
					ChangePswActivity.class);
			break;
		case R.id.btnLogout:
			if (dialog == null) {
				dialog = DialogManager.getLogoutDialog(UserManageActivity.this,
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								setmanager.setToken("");
								DialogManager.dismissDialog(
										UserManageActivity.this, dialog);
								ToastUtils.showShort(UserManageActivity.this,
										"注销成功");
								IntentUtils.getInstance().startActivity(
										UserManageActivity.this,
										LoginActivity.class);
								finish();
							}
						});
			}

			DialogManager.showDialog(UserManageActivity.this, dialog);
			break;
		}

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		finish();
	}

}
