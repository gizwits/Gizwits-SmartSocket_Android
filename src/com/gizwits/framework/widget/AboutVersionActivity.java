package com.gizwits.framework.widget;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.powersocket.R;
import com.xtremeprog.xpgconnect.XPGWifiSDK;

public class AboutVersionActivity extends BaseActivity implements OnClickListener {


	/** The btn_back. */
	ImageView iv_back;
	TextView tv_VersionCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_version);
		//actionBar.setDisplayHomeAsUpEnabled(true);
		// setManager = new SettingManager(this);
		initView();
		initEvents();

	}

	/**
	 * Inits the view.
	 */
	private void initView() {
		iv_back = (ImageView) findViewById(R.id.ivBack);
		tv_VersionCode = (TextView) findViewById(R.id.versionCode);
	}

	/**
	 * Inits the events.
	 */
	private void initEvents() {
		iv_back.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		tv_VersionCode.setText("V" + XPGWifiSDK.sharedInstance().getVersion().toString());
	}



	@Override
	public void onClick(View v) {
		if (v == iv_back) {
			finish();
		}

	}
}
