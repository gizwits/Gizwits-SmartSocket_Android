/**
 * Project Name:XPGSdkV4AppBase
 * File Name:AboutActivity.java
 * Package Name:com.gizwits.framework.activity.help
 * Date:2015-1-27 14:45:36
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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class AboutActivity. <br/>
 * 关于<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class AboutActivity extends BaseActivity {
	
	/** The iv back. */
	private ImageView ivBack;

	/** The iv about. */
	private ImageView ivAbout;

	/** The sv About. */
	private ScrollView svAbout;

	/* (non-Javadoc)
	 * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initViews();
	}

	/**
	 * Inits the views.
	 */
	private void initViews() {
		svAbout = (ScrollView) findViewById(R.id.svAbout);
		ivAbout = (ImageView) findViewById(R.id.ivAbout);
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});
		
		Bitmap mBitmap = BitmapFactory
				.decodeResource(getResources(), R.drawable.about);
		ivAbout.setImageBitmap(mBitmap);
		
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		float width = metric.widthPixels;
		if (width < mBitmap.getWidth()) {
			float mHight = 0;
			float mWidth = 0;
			float scal = 0;
			
			mWidth = width;
			scal = width / mBitmap.getWidth();
			mHight =  scal * mBitmap.getHeight();
			svAbout.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
					(int)mWidth, (int)mHight));
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		finish();
	}

}
