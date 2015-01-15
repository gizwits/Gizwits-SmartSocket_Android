package com.gizwits.framework.activity.account;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.utils.DialogManager;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.DialogUtils;
import com.xpg.ui.utils.ToastUtils;

//TODO: Auto-generated Javadoc
/**
 * 
 * ClassName: Class UserManageActivity. <br/>
 * 用户管理<br/>
 * date: 2014-12-09 17:27:10 <br/>
 * 
 * @author StephenC
 */
public class UserManageActivity extends BaseActivity implements OnClickListener {

	/** The iv TopBar leftBtn. */
	private ImageView ivBack;

	/** The et User name. */
	private TextView tvName;

	/** The btn to update pass page. */
	private Button btnChange;

	/** The btn logout . */
	private Button btnLogout;

	private Dialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_manage);
		initViews();
		initEvents();

	}

	private void initEvents() {
		btnChange.setOnClickListener(this);
		btnLogout.setOnClickListener(this);
		ivBack.setOnClickListener(this);

	}

	private void initViews() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		tvName = (TextView) findViewById(R.id.tvName);
		btnChange = (Button) findViewById(R.id.btnChange);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		tvName.setText(setmanager.getUserName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			onBackPressed();
			break;
		case R.id.btnChange:
			IntentUtils.getInstance().startActivity(UserManageActivity.this,
					ChangePswActivity.class);
			break;
		case R.id.btnLogout:
			if (dialog == null) {
				dialog = DialogManager.getLogoutDialog(UserManageActivity.this,
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								setmanager.setToken("");
								DialogManager.dismissDialog(
										UserManageActivity.this, dialog);
								ToastUtils.showShort(UserManageActivity.this,
										"注销成功");
								IntentUtils.getInstance().startActivity(
										UserManageActivity.this,
										LoginActivity.class);
								finish();
							}
						});
			}

			DialogManager.showDialog(UserManageActivity.this, dialog);
			break;
		}

	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
