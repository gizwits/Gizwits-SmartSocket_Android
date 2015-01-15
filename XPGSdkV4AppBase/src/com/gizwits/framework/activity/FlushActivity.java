/**
 * Project Name:XPGSdkV4AppBase
 * File Name:FlushActivity.java
 * Package Name:com.gizwits.aircondition.activity
 * Date:2015-1-8 11:18:46
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
package com.gizwits.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.account.LoginActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.utils.StringUtils;
import com.xpg.common.system.IntentUtils;

// TODO: Auto-generated Javadoc
/**
 * Created by Lien on 14/12/16.
 * 
 * @author Lien
 */
public class FlushActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flush);
		new Handler().postDelayed(new Runnable() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {

				if (StringUtils.isEmpty(setmanager.getToken())) {
					IntentUtils.getInstance().startActivity(FlushActivity.this,
							LoginActivity.class);
				} else {
					Intent intent = new Intent(FlushActivity.this,
							DeviceListActivity.class);
					intent.putExtra("autoLogin", true);
					startActivity(intent);
				}

				finish();
			}
		}, 1000);
	}

}