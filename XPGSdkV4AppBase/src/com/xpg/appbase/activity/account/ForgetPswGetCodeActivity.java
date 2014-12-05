package com.xpg.appbase.activity.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

public class ForgetPswGetCodeActivity extends BaseActivity implements OnClickListener{
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The et PhoneNum or Email. */
	private EditText etNum;
	
	/** The bt GetCode. */
	private Button btnGetCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget);
		initUI();
	}
	
	private void initUI(){
		TextView tvTitle=(TextView)findViewById(R.id.title);
		tvTitle.setText("忘记密码");
		leftBtn=(ImageView)findViewById(R.id.ivLeftBtn);
		leftBtn.setOnClickListener(this);
		etNum=(EditText)findViewById(R.id.etNum);
		btnGetCode=(Button)findViewById(R.id.btnGetCode);
		leftBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
