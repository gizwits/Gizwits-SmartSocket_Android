package com.gizwits.aircondition.activity.device;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.activity.account.LoginActivity;
import com.gizwits.aircondition.activity.account.UserManageActivity;
import com.gizwits.aircondition.activity.control.MainControlActivity;
import com.gizwits.aircondition.activity.onboarding.SearchDeviceActivity;
import com.gizwits.aircondition.adapter.DeviceListAdapter;
import com.gizwits.aircondition.utils.DialogManager;
import com.gizwits.aircondition.widget.RefreshableListView;
import com.gizwits.aircondition.widget.RefreshableListView.OnRefreshListener;
import com.gizwits.aircondition.R;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

//TODO: Auto-generated Javadoc

/**
 * ClassName: Class DeviceListActivity. <br/>
 * 设备列表<br/>
 * date: 2014-12-09 17:27:10 <br/>
 *
 * @author StephenC
 */
public class DeviceListActivity extends BaseActivity implements
        OnClickListener, OnItemClickListener {
    private static final String TAG = "DeviceListActivity";

    /**
     * The iv TopBar leftBtn.
     */
    private ImageView ivLogout;
    private ImageView ivAdd;

    /**
     * The tv init date
     */
    private RefreshableListView lvDevices;
    private List<XPGWifiDevice> deviceList;
    private DeviceListAdapter deviceListAdapter;
    private ProgressDialog progressDialog;

    private Dialog dialog;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        LOGIN_START,

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

        FOUND,

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler_key key = handler_key.values()[msg.what];
            switch (key) {
                case FOUND:
                    lvDevices.completeRefreshing();
                    break;

                case LOGIN_SUCCESS:
                    progressDialog.cancel();
                    IntentUtils.getInstance().startActivity(
                            DeviceListActivity.this, MainControlActivity.class);
                    break;

                case LOGIN_FAIL:

                    break;
                case LOGIN_TIMEOUT:

                    break;
            }
            deviceListAdapter.changeDatas(deviceList);
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);
        initViews();
        initEvents();
    }

    @Override
    public void onResume() {
        super.onResume();
        getList();
    }

    private void initViews() {
        ivLogout = (ImageView) findViewById(R.id.ivLogout);
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        lvDevices = (RefreshableListView) findViewById(R.id.lvDevices);

        deviceList = new ArrayList<XPGWifiDevice>();
        deviceListAdapter = new DeviceListAdapter(this, deviceList);
        lvDevices.setAdapter(deviceListAdapter);
        lvDevices.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(RefreshableListView listView) {
                getList();

            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("连接中，请稍候。");
    }

    private void initEvents() {
        ivLogout.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        lvDevices.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAdd:
                IntentUtils.getInstance().startActivity(DeviceListActivity.this,
                        SearchDeviceActivity.class);
                break;
            case R.id.ivLogout:
                if (dialog == null) {
                    dialog = DialogManager.getLogoutDialog(DeviceListActivity.this,
                            new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    mCenter.cLogout();
                                }
                            });
                }

                DialogManager.showDialog(DeviceListActivity.this, dialog);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        XPGWifiDevice tempDevice = deviceListAdapter
                .getDeviceByPosition(position);
        if (tempDevice.isLAN()) {
            if (tempDevice.isBind(setmanager.getUid())) {
                // TODO 登陆设备
                Log.i(TAG,
                        "本地登陆设备:mac=" + tempDevice.getPasscode() + ";ip="
                                + tempDevice.getIPAddress() + ";did="
                                + tempDevice.getDid() + ";passcode="
                                + tempDevice.getPasscode());
            } else {
                // TODO 绑定设备
                Log.i(TAG,
                        "绑定设备:mac=" + tempDevice.getPasscode() + ";ip="
                                + tempDevice.getIPAddress() + ";did="
                                + tempDevice.getDid() + ";passcode="
                                + tempDevice.getPasscode());
            }
        } else {
            if (!tempDevice.isOnline()) {
                // TODO 离线
                Log.i(TAG,
                        "离线设备:mac=" + tempDevice.getPasscode() + ";ip="
                                + tempDevice.getIPAddress() + ";did="
                                + tempDevice.getDid() + ";passcode="
                                + tempDevice.getPasscode());
            } else {
                // TODO 登陆设备
                Log.i(TAG,
                        "远程登陆设备:mac=" + tempDevice.getPasscode() + ";ip="
                                + tempDevice.getIPAddress() + ";did="
                                + tempDevice.getDid() + ";passcode="
                                + tempDevice.getPasscode());
                loginDevice(tempDevice);
                progressDialog.show();
            }
        }

    }

    private void loginDevice(XPGWifiDevice xpgWifiDevice) {
        mXpgWifiDevice = xpgWifiDevice;
        mXpgWifiDevice.setListener(deviceListener);
        mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
    }

    @Override
    protected void didLogin(XPGWifiDevice device, int result) {
        if (result == 0) {
            mXpgWifiDevice = device;
            handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
        } else {
            handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
        }

    }

    private void getList() {
        String uid = setmanager.getUid();
        String token = setmanager.getToken();
        mCenter.cGetBoundDevices(uid, token);
    }

    protected void didDiscovered(int error, List<XPGWifiDevice> devicesList) {
        Log.d("onDiscovered", "Device count:" + devicesList.size());
        this.deviceList = devicesList;
        handler.sendEmptyMessage(handler_key.FOUND.ordinal());

    }

    @Override
    protected void didUserLogout(int error, String errorMessage) {
        if (error == 1) {
            DialogManager.dismissDialog(DeviceListActivity.this, dialog);
            ToastUtils.showShort(DeviceListActivity.this, "注销成功");
            IntentUtils.getInstance().startActivity(DeviceListActivity.this,
                    LoginActivity.class);
            finish();
        } else {
            ToastUtils.showShort(DeviceListActivity.this, "注销失败");
        }
    }

    @Override
    public void onBackPressed() {
        if (dialog == null) {
            dialog = DialogManager.getLogoutDialog(DeviceListActivity.this,
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            mCenter.cLogout();
                        }
                    });
        }

        DialogManager.showDialog(DeviceListActivity.this, dialog);
    }
}
