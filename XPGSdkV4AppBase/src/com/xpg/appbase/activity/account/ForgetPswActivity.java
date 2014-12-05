package com.xpg.appbase.activity.account;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

public class ForgetPswActivity extends BaseActivity implements OnClickListener {
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The et phoneNUm Email. */
	private EditText etNum;
	
	/** The et CheckCode. */
	private EditText etInputCode;
	
	/** The btn ReTryGetCode. */
	private Button btnReGetCode;
	
	/** The et Input Password. */
	private EditText etInputPsw;
	
	/** The tb show passwrod. */
	private ToggleButton tbPswFlag;
	
	/** The btn confirm the word. */
	private Button btnConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_reset);
		initUI();
	}
	
	private void initUI(){
		leftBtn=(ImageView) findViewById(R.id.ivLeftBtn);
		etNum=(EditText) findViewById(R.id.etNum);
		etInputCode=(EditText) findViewById(R.id.etInputCode);
		btnReGetCode=(Button) findViewById(R.id.btnReGetCode);
		btnReGetCode.setOnClickListener(this);
		etInputPsw=(EditText) findViewById(R.id.etInputPsw);
		tbPswFlag=(ToggleButton) findViewById(R.id.tbPswFlag);
		tbPswFlag.setOnClickListener(this);
		btnConfirm=(Button) findViewById(R.id.btnConfirm);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
