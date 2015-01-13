/**
 * Project Name:XPGSdkV4AppBase
 * File Name:LoginActivity.java
 * Package Name:com.gizwits.aircondition.activity.account
 * Date:2014-11-27 11:02:01
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
package com.gizwits.aircondition.activity.account;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.activity.device.DeviceListActivity;
import com.gizwits.aircondition.utils.Historys;
import com.gizwits.aircondition.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xpg.common.useful.StringUtils;

// TODO: Auto-generated Javadoc

/**
 * ClassName: Class LoginActivity. <br/>
 * 用户登陆<br/>
 * date: 2014-11-26 17:51:10 <br/>
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
     * The boolean isExit.
     */
    private boolean isExit =false;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        /**
         * The login success.
         */
        LOGIN_SUCCESS,

        /**
         * The login fail.
         */
        LOGIN_FAIL,

        /**
         * The login timeout.
         */
        LOGIN_TIMEOUT,
        
        /**
         *  Exit the app.
         */
        EXIT,
    }

    /**
     * The handler.
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler_key key = handler_key.values()[msg.what];
            switch (key) {
                case LOGIN_SUCCESS:
                    handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
                            .show();
                    dialog.cancel();
                    IntentUtils.getInstance().startActivity(LoginActivity.this, DeviceListActivity.class);
                    finish();
                    break;
                case LOGIN_FAIL:
                    handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, msg.obj + "",
                            Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    break;
                case LOGIN_TIMEOUT:
                    handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
                    Toast.makeText(LoginActivity.this, "登陆超时", Toast.LENGTH_SHORT)
                            .show();
                    dialog.cancel();
                    break;
                case EXIT:
                	isExit=false;
                	break;

            }
        }
    };

    /*
     * (non-Javadoc)
     *
     * @see com.gizwits.aircondition.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initEvents();
    }

    /**
     * Inits the events.
     */
    private void initEvents() {


        tvForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    /**
     * Inits the views.
     */
    private void initViews() {
        etName = (EditText) findViewById(R.id.etName);
        etPsw = (EditText) findViewById(R.id.etPsw);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中，请稍候...");
        if(setmanager.getUserName()!=null){
        	etName.setText(setmanager.getUserName());
        }

    }

    /*
     * (non-Javadoc)
     *
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
                mCenter.cLogin(name, psw);
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

	@Override
	public void onBackPressed() {
		exit();
	}

	public void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(),
					getString(R.string.tip_exit), Toast.LENGTH_SHORT).show();
			handler.sendEmptyMessageDelayed(handler_key.EXIT.ordinal(), 2000);
		} else {

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(intent);
			Historys.exit();
		}
	}
    
}
