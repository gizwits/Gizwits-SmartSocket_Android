/**
 * Project Name:XPGSdkV4AppBase
 * File Name:HelpActivity.java
 * Package Name:com.gizwits.framework.activity.help
 * Date:2015-1-27 14:45:39
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
package com.gizwits.framework.activity.help;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;

/**
 * 
 * ClassName: Class HelpActivity. <br/>
 * 帮助<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class HelpActivity extends BaseActivity implements OnClickListener {

	/** The iv TopBar leftBtn. */
	private ImageView ivBack;

	/** The btn AppHelp. */
	private Button AppHelp;

	/** The btn DeviceHelp. */
	private Button DeviceHelp;

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initViews();
		initEvents();
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		ivBack.setOnClickListener(this);
		AppHelp.setOnClickListener(this);
		DeviceHelp.setOnClickListener(this);

	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		AppHelp = (Button) findViewById(R.id.AppHelp);
		DeviceHelp = (Button) findViewById(R.id.DeviceHelp);

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

		default:
			break;
		}

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		finish();
	}

}
