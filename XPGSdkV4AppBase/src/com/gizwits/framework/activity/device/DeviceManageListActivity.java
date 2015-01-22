package com.gizwits.framework.activity.device;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.gizwits.framework.adapter.ManageListAdapter;
import com.xpg.common.system.IntentUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

public class DeviceManageListActivity extends BaseActivity implements OnClickListener {
    /**
     * The iv TopBar leftBtn.
     */
    private ImageView ivBack;

    /**
     * The tv init date
     */
    private ListView lvDevices;

    private ImageView ivAdd;

    /**
     * The Device device list
     */
    private List<XPGWifiDevice> devices;

    ManageListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_device_list);

        initViews();
        initEvents();
    }

    private void initEvents() {
        lvDevices.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                XPGWifiDevice device = bindlist.get(position);
                if (device.isLAN() || device.isOnline()) {
                    Intent intent = new Intent(DeviceManageListActivity.this,
                            DeviceManageDetailActivity.class);
                    intent.putExtra("mac", device.getMacAddress());
                    intent.putExtra("did", device.getDid());
                    startActivity(intent);
                }
            }
        });
        ivBack.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
    }

    private void initViews() {
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        mAdapter = new ManageListAdapter(DeviceManageListActivity.this,
                bindlist);
        lvDevices.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.ivAdd:
                IntentUtils.getInstance().startActivity(DeviceManageListActivity.this,
                        SearchDeviceActivity.class);
                break;
        }
    }
}
