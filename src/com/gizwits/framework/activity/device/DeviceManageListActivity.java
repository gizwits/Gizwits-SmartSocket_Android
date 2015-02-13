/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DeviceManageListActivity.java
 * Package Name:com.gizwits.framework.activity.device
 * Date:2015-1-27 14:45:30
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

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class DeviceManageListActivity. <br/> 
 * 设备管理列表，用于显示当前账号中已经绑定了的设备列表
 * <br/>
 * date: 2015-1-27 14:45:30 <br/> 
 *
 * @author Lien
 */
public class DeviceManageListActivity extends BaseActivity implements OnClickListener {
    /**
     * The iv TopBar leftBtn.
     */
    private ImageView ivBack;

    /** The tv init date. */
    private ListView lvDevices;

    /** The iv add. */
    private ImageView ivAdd;

    /** The Device device list. */
    private List<XPGWifiDevice> devices;

    /** The m adapter. */
    ManageListAdapter mAdapter;

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_device_list);

        initViews();
        initEvents();
    }

    /**
     * Inits the events.
     */
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

    /**
     * Inits the views.
     */
    private void initViews() {
        ivAdd = (ImageView) findViewById(R.id.ivAdd);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        lvDevices = (ListView) findViewById(R.id.lvDevices);
        mAdapter = new ManageListAdapter(DeviceManageListActivity.this,
                bindlist);
        lvDevices.setAdapter(mAdapter);

    }
    
    

    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
	public void onResume() {
		super.onResume();
		initBindList();
		mAdapter.notifyDataSetChanged();
	}

	@Override
    public void onBackPressed() {
        finish();
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
            case R.id.ivAdd:
                IntentUtils.getInstance().startActivity(DeviceManageListActivity.this,
                        SearchDeviceActivity.class);
                break;
        }
    }
}
