package com.gizwits.aircondition.activity.onboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.activity.device.DeviceListActivity;
import com.gizwits.aircondition.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

import java.util.Timer;
import java.util.TimerTask;

public class SoftApConfigActivity extends BaseActivity implements OnClickListener {
    private LinearLayout llConnectAp;
    private LinearLayout llInsertPsw;
    private LinearLayout llConfig;
    private LinearLayout llConfigSuccess;
    private LinearLayout llConfigFailed;
    private EditText etInputPsw;
    private Button btnNext;
    private Button btnOK;
    private Button btnRetry;
    private TextView tvpsw;
    private TextView tvSsid;
    private TextView tvTick;

    private String strSsid;
    private String strPsw;

    int secondleft = 30;

    private Timer timer;


    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        TICK_TIME,

        CHANGE_WIFI,

        CONFIG_SUCCESS,

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
                    llConnectAp.setVisibility(View.GONE);
                    llInsertPsw.setVisibility(View.VISIBLE);
                    llConfig.setVisibility(View.GONE);
                    llConfigSuccess.setVisibility(View.GONE);
                    llConfigFailed.setVisibility(View.GONE);
                    break;

                case CONFIG_SUCCESS:
                    llConnectAp.setVisibility(View.GONE);
                    llInsertPsw.setVisibility(View.GONE);
                    llConfig.setVisibility(View.GONE);
                    llConfigSuccess.setVisibility(View.VISIBLE);
                    llConfigFailed.setVisibility(View.GONE);

                    break;

                case CONFIG_FAILED:
                    llConnectAp.setVisibility(View.GONE);
                    llInsertPsw.setVisibility(View.GONE);
                    llConfig.setVisibility(View.GONE);
                    llConfigSuccess.setVisibility(View.GONE);
                    llConfigFailed.setVisibility(View.VISIBLE);
                    break;

            }
        }
    };

    /**
     * 网络状态广播接受器
     */
    ConnectChangeBroadcast mChangeBroadcast = new ConnectChangeBroadcast();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softap);
        initViews();
        initEvents();
        initDatas();
    }

    private void initDatas() {
        if (getIntent() != null) {
            strSsid = getIntent().getStringExtra("ssid");
            tvSsid.setText("Wi-Fi名称:" + strSsid);
        }
    }

    private void initViews() {
        llConnectAp = (LinearLayout) findViewById(R.id.llConnectAp);
        llInsertPsw = (LinearLayout) findViewById(R.id.llInsertPsw);
        llConfig = (LinearLayout) findViewById(R.id.llConfiging);
        llConfigSuccess = (LinearLayout) findViewById(R.id.llConfigSuccess);
        llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnOK = (Button) findViewById(R.id.btnOK);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        tvpsw = (TextView) findViewById(R.id.tvpsw);
        tvSsid = (TextView) findViewById(R.id.tvSsid);
        tvTick = (TextView) findViewById(R.id.tvTick);
        llConnectAp.setVisibility(View.VISIBLE);
        llInsertPsw.setVisibility(View.GONE);
        llConfig.setVisibility(View.GONE);
        llConfigSuccess.setVisibility(View.GONE);
        llConfigFailed.setVisibility(View.GONE);
    }

    private void initEvents() {
        btnNext.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mChangeBroadcast, filter);
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(mChangeBroadcast);

    }


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
                IntentUtils.getInstance().startActivity(SoftApConfigActivity.this, SearchDeviceActivity.class);
                finish();
                break;
        }
    }

    private void startConfig() {
        secondleft = 60;
        llConnectAp.setVisibility(View.GONE);
        llInsertPsw.setVisibility(View.GONE);
        llConfig.setVisibility(View.VISIBLE);
        llConfigSuccess.setVisibility(View.GONE);
        llConfigFailed.setVisibility(View.GONE);
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


    /**
     * 广播监听器，监听wifi连上的广播.
     */
    public class ConnectChangeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (NetworkUtils.isWifiConnected(context) && NetworkUtils.getCurentWifiSSID(SoftApConfigActivity.this).contains("XPG-GAgent")) {
                handler.sendEmptyMessage(handler_key.CHANGE_WIFI.ordinal());
            }

        }
    }

    @Override
    protected void didSetDeviceWifi(int error, XPGWifiDevice device) {
        if (error == 0) {
            handler.sendEmptyMessage(handler_key.CONFIG_SUCCESS.ordinal());
        } else {
            handler.sendEmptyMessage(handler_key.CONFIG_FAILED.ordinal());
        }
    }


}
