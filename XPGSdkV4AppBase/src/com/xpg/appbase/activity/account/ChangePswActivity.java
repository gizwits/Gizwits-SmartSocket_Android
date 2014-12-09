package com.xpg.appbase.activity.account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

//TODO: Auto-generated Javadoc
/**
* 
* ClassName: Class ChangePswActivity. <br/>
* 修改密码<br/>
* date: 2014-12-09 17:27:10 <br/>
* 
* @author StephenC
*/
public class ChangePswActivity extends BaseActivity {
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The et user name. */
	private EditText etNum;
	
	/** The et Input Password. */
	private EditText etInputPsw;
	
	/** The tb show passwrod. */
	private ToggleButton tbPswFlag;
	
	/** The btn confirm the word. */
	private Button btnConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initUI();
	}

	private void initUI(){
		leftBtn=(ImageView)findViewById(R.id.ivLeftBtn);
		etNum=(EditText)findViewById(R.id.etNum);
		etInputPsw=(EditText)findViewById(R.id.etInputPsw);
		tbPswFlag=(ToggleButton)findViewById(R.id.tbPswFlag);
		btnConfirm=(Button)findViewById(R.id.btnConfirm);
	}
	
}
