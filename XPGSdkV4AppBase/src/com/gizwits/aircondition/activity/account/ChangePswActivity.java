package com.gizwits.aircondition.activity.account;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.gizwits.aircondition.activity.BaseActivity;
import com.gizwits.aircondition.R;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

//TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class ChangePswActivity. <br/>
 * 修改密码<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class ChangePswActivity extends BaseActivity implements OnClickListener {

	/** The iv TopBar leftBtn. */
	private ImageView ivBack;

	/** The et user name. */
	private EditText etPswOld;

	/** The et Input Password. */
	private EditText etPswNew;

	/** The tb show passwrod. */
	private ToggleButton tbPswFlag;

	/** The btn confirm the word. */
	private Button btnConfirm;

	private String newPsw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		initViews();
		initEvents();
	}

	private void initEvents() {
		ivBack.setOnClickListener(this);
		btnConfirm.setOnClickListener(this);
		tbPswFlag.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					etPswOld.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					etPswNew.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					etPswOld.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
					etPswNew.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}

			}
		});

	}

	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		etPswOld = (EditText) findViewById(R.id.etPswOld);
		etPswNew = (EditText) findViewById(R.id.etPswNew);
		tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
		btnConfirm = (Button) findViewById(R.id.btnConfirm);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnConfirm:
			String oldPsw = etPswOld.getText().toString();
			newPsw = etPswNew.getText().toString();
			if (StringUtils.isEmpty(oldPsw)) {
				ToastUtils.showShort(ChangePswActivity.this, "请输入旧的密码");
				return;
			}
			if (StringUtils.isEmpty(newPsw)) {
				ToastUtils.showShort(ChangePswActivity.this, "请输入旧的密码");
				return;
			}
			if (!oldPsw.equals(setmanager.getPassword())) {
				ToastUtils.showShort(ChangePswActivity.this, "请输入正确的用户密码");
				return;
			}
			changePsw(oldPsw, newPsw);
			break;
		}

	}

	private void changePsw(String oldPsw, String newPsw) {
		mCenter.cChangeUserPassword(setmanager.getToken(), oldPsw, newPsw);
	}

	@Override
	protected void didChangeUserPassword(int error, String errorMessage) {
		if (error == 0) {
			ToastUtils.showShort(ChangePswActivity.this, "修改成功");
			setmanager.setPassword(newPsw);
			finish();
		} else {
			ToastUtils.showShort(ChangePswActivity.this, "修改失败");
		}
	}

}
