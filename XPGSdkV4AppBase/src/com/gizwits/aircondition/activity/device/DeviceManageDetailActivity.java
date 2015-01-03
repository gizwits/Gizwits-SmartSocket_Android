package com.gizwits.aircondition.activity.device;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.R;

//TODO: Auto-generated Javadoc
/**
* 
* ClassName: Class DeviceManageDetailActivity. <br/>
* 设备详细信息<br/>
* date: 2014-12-09 17:27:10 <br/>
* 
* @author StephenC
*/
public class DeviceManageDetailActivity extends BaseActivity implements OnClickListener {
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The tv init date */
	private TextView initDate;
	
	/** The tv init place */
	private TextView initPlace;;
	
	/** The tv device type */
	private TextView DeviceType;
	
	/** The tv device code */
	private TextView DeviceCode;
	
	/** The et device name. */
	private EditText etName;
	
	/** The btn delDevice. */
	private Button btnDelDevice;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_devices_info);
		initUI();
	}
	
	private void initUI(){
		leftBtn=(ImageView)findViewById(R.id.ivLeftBtn);
		initDate=(TextView)findViewById(R.id.initDate);
		initPlace=(TextView)findViewById(R.id.initPlace);
		DeviceType=(TextView)findViewById(R.id.DeviceType);
		DeviceCode=(TextView)findViewById(R.id.DeviceCode);
		etName=(EditText)findViewById(R.id.etName);
		btnDelDevice=(Button)findViewById(R.id.btnDelDevice);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
