/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SearchDeviceActivity.java
 * Package Name:com.gizwits.framework.activity.onboarding
 * Date:2015-1-27 14:46:15
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

import java.util.ArrayList;
import java.util.List;

import zxing.CaptureActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.adapter.SearchListAdapter;
import com.gizwits.framework.utils.DialogManager;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.NetworkUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc

/**
 * ClassName: Class SearchDeviceActivity. <br/>
 * 搜索设备
 * <br/>
 * date: 2014-12-3 14:26:53 <br/>
 *
 * @author Lien
 */
public class SearchDeviceActivity extends BaseActivity implements
        OnClickListener, OnItemClickListener {

    /**
     * The btn add qr.
     */
    private Button btnAddQR;

    /**
     * The btn add gokit.
     */
    private Button btnAddGokit;

    /**
     * The lv devices.
     */
    private ListView lvDevices;

    /**
     * The tv tips.
     */
    private TextView tvTips;
    
    /**
     * The iv back.
     */
    private ImageView ivBack;

    /**
     * The device list.
     */
    private List<XPGWifiDevice> deviceList;
    
    /** The all device list. */
    private List<XPGWifiDevice> allDeviceList;

    /**
     * The dialog connect tip.
     */
    private Dialog noNetworkDialog;

    /** The loading dialog. */
    private ProgressDialog loadingDialog;

    /** The adapter. */
    private SearchListAdapter adapter;

    /** 网络状态广播接受器. */
    ConnecteChangeBroadcast mChangeBroadcast = new ConnecteChangeBroadcast();

    /** The is waiting wifi. */
    private boolean isWaitingWifi = false;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        /** The found success. */
        FOUND_SUCCESS,

        /** The found finish. */
        FOUND_FINISH,

        /** The change success. */
        CHANGE_SUCCESS,

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
                case FOUND_FINISH:
                	if(deviceList.size()>0)
                		deviceList.clear();
                	
                    if (allDeviceList.size() > 0) {
                        for (XPGWifiDevice device : allDeviceList) {
                            if (device.isLAN() && !device.isBind(setmanager.getUid())) {
                                deviceList.add(device);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        lvDevices.setVisibility(View.VISIBLE);
                        tvTips.setVisibility(View.GONE);
                    } else {
                        lvDevices.setVisibility(View.GONE);
                        tvTips.setVisibility(View.VISIBLE);
                    }
                    loadingDialog.cancel();
                    break;
                case FOUND_SUCCESS:
                    adapter.notifyDataSetChanged();
                    break;
                case CHANGE_SUCCESS:
                    IntentUtils.getInstance().startActivity(
                            SearchDeviceActivity.this, AutoConfigActivity.class);
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
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdevice);
        initViews();
        initEvents();
    }


    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        loadingDialog.show();
        mCenter.cGetBoundDevices(setmanager.getUid(), setmanager.getToken());
        handler.sendEmptyMessageDelayed(handler_key.FOUND_FINISH.ordinal(),
                5000);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mChangeBroadcast, filter);
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPause()
     */
    public void onPause() {
        super.onPause();
        unregisterReceiver(mChangeBroadcast);

    }

    /**
     * Inits the views.
     */
    private void initViews() {
        btnAddQR = (Button) findViewById(R.id.btnAddQR);
        btnAddGokit = (Button) findViewById(R.id.btnAddGokit);
        ivBack=(ImageView) findViewById(R.id.ivBack);
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        tvTips = (TextView) findViewById(R.id.tvTips);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("加载中，请稍候.");
        loadingDialog.setCancelable(false);
        deviceList = new ArrayList<XPGWifiDevice>();
        adapter = new SearchListAdapter(this, deviceList);
        lvDevices.setAdapter(adapter);
        noNetworkDialog = DialogManager.getNoNetworkDialog(this);
        noNetworkDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                isWaitingWifi = false;

            }
        });
        allDeviceList = new ArrayList<XPGWifiDevice>();
    }

    /**
     * Inits the events.
     */
    private void initEvents() {
        btnAddQR.setOnClickListener(this);
        btnAddGokit.setOnClickListener(this);
        lvDevices.setOnItemClickListener(this);
        ivBack.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddQR:

                if (NetworkUtils.isNetworkConnected(this)) {
                    // 跳转到二维码扫描activity
                    IntentUtils.getInstance().startActivity(this,
                            CaptureActivity.class);
                }
                break;
            case R.id.btnAddGokit:
                if (NetworkUtils.isWifiConnected(this)) {
                    // 跳转到添加airlink activity
                    IntentUtils.getInstance().startActivity(SearchDeviceActivity.this, AutoConfigActivity.class);
                } else {
                    isWaitingWifi = true;
                    DialogManager.showDialog(this, noNetworkDialog);
                }
                break;
            case R.id.ivBack:
            	onBackPressed();
            	break;
        }

    }

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#didDiscovered(int, java.util.List)
     */
    @Override
    protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
//        Log.e("SearchDevice", devicesList.get(0).getPasscode());
        if (devicesList.size() > 0) {
            allDeviceList=devicesList;
        }
    }

    /* (non-Javadoc)
     * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // TODO 跳到获取passcode activity
    	XPGWifiDevice device =(XPGWifiDevice) adapter.getItem(position);
//        Device device = new Device(deviceList.get(position));
        Intent intent = new Intent(SearchDeviceActivity.this, BindingDeviceActivity.class);
        intent.putExtra("mac", device.getMacAddress());
        intent.putExtra("did", device.getDid());
        startActivity(intent);
        finish();
    }

    /**
     * 广播监听器，监听wifi连上的广播.
     *
     * @author Lien
     */
    public class ConnecteChangeBroadcast extends BroadcastReceiver {

        /* (non-Javadoc)
         * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            boolean iswifi = NetworkUtils.isWifiConnected(context);
            Log.i("networkchange", "change" + iswifi);
            if (iswifi && isWaitingWifi) {
                if (noNetworkDialog.isShowing()) {
                    DialogManager.dismissDialog(SearchDeviceActivity.this,
                            noNetworkDialog);
                    handler.sendEmptyMessage(handler_key.CHANGE_SUCCESS
                            .ordinal());
                }
            }
        }
    }

	@Override
	public void onBackPressed() {
		finish();
	}

    
}
