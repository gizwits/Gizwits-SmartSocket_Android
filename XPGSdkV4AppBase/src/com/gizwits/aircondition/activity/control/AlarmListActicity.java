/**
 * Project Name:XPGSdkV4AppBase
 * File Name:AlarmListActicity.java
 * Package Name:com.gizwits.aircondition.activity.control
 * Date:2015-1-27 14:43:57
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
package com.gizwits.aircondition.activity.control;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.adapter.AlarmListAdapter;
import com.gizwits.framework.entity.DeviceAlarm;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class AlarmListActicity. <br/> 
 * 显示警告列表的类
 * <br/>
 * date: 2015-1-27 14:43:57 <br/> 
 *
 * @author Lien
 */
public class AlarmListActicity extends BaseActivity implements OnClickListener {
	
	/** The lv list. */
	private ListView lvList;
	
	/** The btn call. */
	private Button btnCall;
	
	/** The alarm list. */
	private ArrayList<DeviceAlarm> alarmList;
	
	/** The m adapter. */
	private AlarmListAdapter mAdapter;
	
	/** The iv back. */
	private ImageView ivBack;

	
	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarmlist);
		initViews();
		initEvents();
		initDatas();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		lvList = (ListView) findViewById(R.id.lvList);
		btnCall = (Button) findViewById(R.id.btnCall);
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		btnCall.setOnClickListener(this);
		ivBack.setOnClickListener(this);
	}

	/**
	 * Inits the datas.
	 */
	private void initDatas() {
		alarmList = new ArrayList<DeviceAlarm>();
		alarmList = (ArrayList<DeviceAlarm>) getIntent().getSerializableExtra(
				"alarm_list");
		mAdapter = new AlarmListAdapter(this, alarmList);
		lvList.setAdapter(mAdapter);

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
		case R.id.btnCall:
			Intent intent = new Intent(Intent.ACTION_CALL,
					Uri.parse("tel:10086"));
			startActivity(intent);
			break;

		}

	}
}
