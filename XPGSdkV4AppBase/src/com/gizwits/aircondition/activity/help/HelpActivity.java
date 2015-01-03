package com.gizwits.aircondition.activity.help;

import android.os.Bundle;
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
public class HelpActivity extends BaseActivity {
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The btn AppHelp. */
	private Button AppHelp;
	
	/** The btn DeviceHelp. */
	private Button DeviceHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initUI();
	}
	
	private void initUI(){
		leftBtn=(ImageView)findViewById(R.id.ivLeftBtn);
		AppHelp=(Button)findViewById(R.id.AppHelp);
		DeviceHelp=(Button)findViewById(R.id.DeviceHelp);
	}
	
}
