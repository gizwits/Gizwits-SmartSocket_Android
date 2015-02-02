/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SoftApConfigActivity.java
 * Package Name:com.gizwits.framework.activity.onboarding
 * Date:2015-1-27 14:46:20
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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.Timer;
import java.util.TimerTask;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class SoftApConfigActivity. <br/> 
 * 手动配置设备
 * <br/>
 *
 * @author Lien
 */
public class SoftApConfigActivity extends BaseActivity implements OnClickListener {
    
    /** The ll connect ap. */
    private LinearLayout llConnectAp;
    
    /** The ll insert psw. */
    private LinearLayout llInsertPsw;
    
    /** The ll config. */
    private LinearLayout llConfig;
    
    /** The ll config success. */
    private LinearLayout llConfigSuccess;
    
    /** The ll config failed. */
    private LinearLayout llConfigFailed;
    
    /** The et input psw. */
    private EditText etInputPsw;
    
    /** The btn next. */
    private Button btnNext;
    
    /** The btn ok. */
    private Button btnOK;
    
    /** The btn retry. */
    private Button btnRetry;
    
    /** The tvpsw. */
    private TextView tvpsw;
    
    /**
     * The iv back.
     */
    private ImageView ivBack;
    
    /** The tv ssid. */
    private TextView tvSsid;
    
    /** The tv tick. */
    private TextView tvTick;
    
    /** The tb psw flag. */
    private ToggleButton tbPswFlag;

    /** The str ssid. */
    private String strSsid;
    
    /** The str psw. */
    private String strPsw;

    /** The secondleft. */
    int secondleft = 30;

    /** The timer. */
    private Timer timer;
    
    /** The UI_STATE now. */
    private UI_STATE UiStateNow;


    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        /** The tick time. */
        TICK_TIME,

        /** The change wifi. */
        CHANGE_WIFI,

        /** The config success. */
        CONFIG_SUCCESS,

        /** The config failed. */
        CONFIG_FAILED,

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
                case TICK_TIME:
                    secondleft--;
                    if (secondleft <= 0) {
                        timer.cancel();
                        sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
                    } else {
                        tvTick.setText(secondleft + "");
                    }
                    break;

                case CHANGE_WIFI:
                    showLayout(UI_STATE.Setting);
                    break;
                case CONFIG_SUCCESS:
                    showLayout(UI_STATE.ResultSuccess);
                    break;
                case CONFIG_FAILED:
                    showLayout(UI_STATE.ResultFailed);
                    break;

            }
        }
    };

    /** 网络状态广播接受器. */
    ConnectChangeBroadcast mChangeBroadcast = new ConnectChangeBroadcast();

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softap);
        initViews();
        initEvents();
        initDatas();
    }

    /**
     * Inits the datas.
     */
    private void initDatas() {
        if (getIntent() != null) {
            strSsid = getIntent().getStringExtra("ssid");
            tvSsid.setText("Wi-Fi名称:" + strSsid);
        }
    }

    /**
     * Inits the views.
     */
    private void initViews() {
        llConnectAp = (LinearLayout) findViewById(R.id.llConnectAp);
        llInsertPsw = (LinearLayout) findViewById(R.id.llInsertPsw);
        llConfig = (LinearLayout) findViewById(R.id.llConfiging);
        llConfigSuccess = (LinearLayout) findViewById(R.id.llConfigSuccess);
        llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        ivBack=(ImageView) findViewById(R.id.ivBack);
        etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        tvpsw = (TextView) findViewById(R.id.tvpsw);
        tvSsid = (TextView) findViewById(R.id.tvSsid);
        tvTick = (TextView) findViewById(R.id.tvTick);
        tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        showLayout(UI_STATE.SoftApReady);
    }

    /**
     * Inits the events.
     */
    private void initEvents() {
        btnNext.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
                if (isChecked) {
                    etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

			}
		});
    }

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mChangeBroadcast, filter);
    }

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onPause()
     */
    public void onPause() {
        super.onPause();
        unregisterReceiver(mChangeBroadcast);

    }


    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                startConfig();
                break;
            case R.id.btnOK:
                IntentUtils.getInstance().startActivity(SoftApConfigActivity.this, DeviceListActivity.class);
                finish();
                break;
            case R.id.btnRetry:
            case R.id.ivBack:
            	onBackPressed();
            	break;
        }
    }
    
    enum UI_STATE{
    	SoftApReady,PswInput,Setting,ResultFailed,ResultSuccess;
    }
    
    private void showLayout(UI_STATE ui){
    	UiStateNow=ui;
    	switch(ui){
    	case SoftApReady:
    		llConnectAp.setVisibility(View.VISIBLE);
            llInsertPsw.setVisibility(View.GONE);
            llConfig.setVisibility(View.GONE);
            llConfigSuccess.setVisibility(View.GONE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
    		break;
    	case PswInput:
    		llConnectAp.setVisibility(View.GONE);
            llInsertPsw.setVisibility(View.VISIBLE);
            llConfig.setVisibility(View.GONE);
            llConfigSuccess.setVisibility(View.GONE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
    		break;
    	case Setting:
    		llConnectAp.setVisibility(View.GONE);
            llInsertPsw.setVisibility(View.GONE);
            llConfig.setVisibility(View.VISIBLE);
            llConfigSuccess.setVisibility(View.GONE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.GONE);
    		break;
    	case ResultFailed:
    		llConnectAp.setVisibility(View.GONE);
            llInsertPsw.setVisibility(View.GONE);
            llConfig.setVisibility(View.GONE);
            llConfigSuccess.setVisibility(View.GONE);
            llConfigFailed.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.VISIBLE);
    		break;
    	case ResultSuccess:
    		llConnectAp.setVisibility(View.GONE);
            llInsertPsw.setVisibility(View.GONE);
            llConfig.setVisibility(View.GONE);
            llConfigSuccess.setVisibility(View.VISIBLE);
            llConfigFailed.setVisibility(View.GONE);
            ivBack.setVisibility(View.VISIBLE);
    		break;
    	}
    }

    /**
     * Start config.
     */
    private void startConfig() {
        secondleft = 60;
        showLayout(UI_STATE.Setting);
        strPsw = etInputPsw.getText().toString();
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);
        mCenter.cSetSoftAp(strSsid, strPsw);
    }

    @Override
	public void onBackPressed() {
    	switch(UiStateNow){
    	case SoftApReady:
    		startActivity(new Intent(SoftApConfigActivity.this,SearchDeviceActivity.class));
    		finish();
    		break;
    	case PswInput:
    		showLayout(UiStateNow.SoftApReady);
    		break;
    	case Setting:
    		break;
    	case ResultFailed:
    		startActivity(new Intent(SoftApConfigActivity.this,SearchDeviceActivity.class));
    		finish();
    	case ResultSuccess:
    		finish();
    		break;
    	}
    	
	}

	/**
     * 广播监听器，监听wifi连上的广播.
     *
     * @author Lien
     */
    public class ConnectChangeBroadcast extends BroadcastReceiver {

        /* (non-Javadoc)
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            if (NetworkUtils.isWifiConnected(context) && NetworkUtils.getCurentWifiSSID(SoftApConfigActivity.this).contains("XPG-GAgent")) {
                handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
            }

        }
    }

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#didSetDeviceWifi(int, com.xtremeprog.xpgconnect.XPGWifiDevice)
     */
    @Override
    protected void didSetDeviceWifi(int error, XPGWifiDevice device) {
        if (error == 0) {
            handler.sendEmptyMessage(handler_key.CONFIG_SUCCESS.ordinal());
        } else {
            handler.sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
        }
    }


}
