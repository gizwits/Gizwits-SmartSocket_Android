package com.gizwits.aircondition.activity.help;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.R;

//TODO: Auto-generated Javadoc
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initViews();
		initEvents();
	}

	private void initEvents() {
		ivBack.setOnClickListener(this);
		AppHelp.setOnClickListener(this);
		DeviceHelp.setOnClickListener(this);

	}

	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		AppHelp = (Button) findViewById(R.id.AppHelp);
		DeviceHelp = (Button) findViewById(R.id.DeviceHelp);

	}

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
	
	@Override
	public void onBackPressed() {
		finish();
	}

}
