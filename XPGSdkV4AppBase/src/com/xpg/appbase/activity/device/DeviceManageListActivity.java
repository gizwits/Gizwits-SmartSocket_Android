package com.xpg.appbase.activity.device;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;
import com.xpg.appbase.adapter.SearchListAdapter;
import com.xpg.appbase.entity.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceManageListActivity extends BaseActivity{
    /**
     * The iv TopBar leftBtn.
     */
    private ImageView leftBtn;

    /**
     * The tv init date
     */
    private ListView lvDevices;

    /**
     * The Device device list
     */
    private List<Device> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicelist);

        initUI();
    }

    private void initUI() {

        leftBtn = (ImageView) findViewById(R.id.ivLeftBtn);
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        devices = new ArrayList<Device>();
        Device device = new Device();
        device.setName("111");
        devices.add(device);
        device.setName("222");
        devices.add(device);
        device.setName("333");
        devices.add(device);
        device.setName("444");
        devices.add(device);
        lvDevices.setAdapter(new SearchListAdapter(this, devices, null));
    }

}
