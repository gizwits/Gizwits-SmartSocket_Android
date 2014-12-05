package com.xpg.appbase.activity.device;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

public class DeviceManageDetailActivity extends BaseActivity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices_info);
		initUI();
	}
	
	private void initUI(){
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
