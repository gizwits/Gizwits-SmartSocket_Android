package com.gizwits.framework.activity.help;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;

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
	private ImageView ivBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initViews();
	}

	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();

			}
		});
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
