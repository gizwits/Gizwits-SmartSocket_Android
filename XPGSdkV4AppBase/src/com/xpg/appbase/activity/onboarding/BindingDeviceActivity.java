package com.xpg.appbase.activity.onboarding;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;
import com.xpg.appbase.activity.device.DeviceListActivity;
import com.xpg.appbase.entity.Device;
import com.xpg.common.system.IntentUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class BindingDeviceActivity extends BaseActivity implements OnClickListener {
    private LinearLayout llStartConfig;
    private LinearLayout llConfigFailed;
    private TextView tvPress;
    private Button btnRetry;

    private Device device;

    private enum handler_key {

        BIND_SUCCESS,

        BIND_FAILED,

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
                case BIND_SUCCESS:
                    IntentUtils.getInstance().startActivity(BindingDeviceActivity.this, DeviceListActivity.class);
                    finish();
                    break;
                case BIND_FAILED:
                    llStartConfig.setVisibility(View.GONE);
                    llConfigFailed.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        initViews();
        initEvents();
        initDatas();
        bindDevice();
    }


    private void initDatas() {
        if (getIntent() != null)
            device = (Device) getIntent().getSerializableExtra("device");
    }

    private void initEvents() {
        tvPress.setOnClickListener(this);
        btnRetry.setOnClickListener(this);
    }

    private void initViews() {
        llConfigFailed = (LinearLayout) findViewById(R.id.llConfigFailed);
        llStartConfig = (LinearLayout) findViewById(R.id.llStartConfig);
        tvPress = (TextView) findViewById(R.id.tvPress);
        btnRetry = (Button) findViewById(R.id.btnRetry);
        llStartConfig.setVisibility(View.VISIBLE);
        llConfigFailed.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPress:
                handler.sendEmptyMessage(handler_key.BIND_FAILED.ordinal());
                break;
            case R.id.btnRetry:
                IntentUtils.getInstance().startActivity(BindingDeviceActivity.this, SearchDeviceActivity.class);
                finish();
                break;
        }
    }

    private void bindDevice() {
        mCenter.cBindDevice(setmanager.getUid(), setmanager.getToken(), device.getDid(), device.getPasscode(), null);
    }

    @Override
    protected void didBindDevice(int error, String errorMessage, String did) {
        if (error == 0) {
            handler.sendEmptyMessage(handler_key.BIND_SUCCESS.ordinal());
        }
    }
}
