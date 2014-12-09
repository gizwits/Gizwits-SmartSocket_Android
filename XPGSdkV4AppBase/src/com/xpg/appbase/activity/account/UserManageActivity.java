package com.xpg.appbase.activity.account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xpg.appbase.R;
import com.xpg.appbase.activity.BaseActivity;

//TODO: Auto-generated Javadoc
/**
* 
* ClassName: Class UserManageActivity. <br/>
* 用户管理<br/>
* date: 2014-12-09 17:27:10 <br/>
* 
* @author StephenC
*/
public class UserManageActivity extends BaseActivity{
	
	/** The iv TopBar leftBtn. */
	private ImageView leftBtn;
	
	/** The et User name. */
	private EditText username_tv;
	
	/** The btn to update pass page. */
	private Button update_pass_bt;
	
	/** The btn logout . */
	private Button login_out_bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user);
		initUI();
	}
	
	private void initUI(){
		leftBtn=(ImageView)findViewById(R.id.ivLeftBtn);
		username_tv=(EditText)findViewById(R.id.username_tv);
		update_pass_bt=(Button)findViewById(R.id.update_pass_bt);
		login_out_bt=(Button)findViewById(R.id.login_out_bt);
	}
}
