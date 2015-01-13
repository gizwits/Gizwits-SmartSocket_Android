package com.gizwits.aircondition.activity.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.adapter.AlarmListAdapter;
import com.gizwits.aircondition.entity.DeviceAlarm;
import com.gizwits.aircondition.R;

public class AlarmListActicity extends BaseActivity implements OnClickListener {
	private ListView lvList;
	private Button btnCall;
	private ArrayList<DeviceAlarm> alarmList;
	private AlarmListAdapter mAdapter;
	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmlist);
		initViews();
		initEvents();
		initDatas();
	}

	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		lvList = (ListView) findViewById(R.id.lvList);
		btnCall = (Button) findViewById(R.id.btnCall);
	}

	private void initEvents() {
		btnCall.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	private void initDatas() {
		alarmList = new ArrayList<DeviceAlarm>();
		alarmList = (ArrayList<DeviceAlarm>) getIntent().getSerializableExtra(
				"alarm_list");
		mAdapter = new AlarmListAdapter(this, alarmList);
		lvList.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnCall:
			Intent intent = new Intent(Intent.ACTION_CALL,
					Uri.parse("tel:10086"));
			startActivity(intent);
			break;

		}

	}
}
