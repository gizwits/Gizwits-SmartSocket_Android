package com.gizwits.framework.activity.account;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.onboarding.SearchDeviceActivity;
import com.xpg.common.system.IntentUtils;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

//TODO: Auto-generated Javadoc

/**
 * ClassName: Class ForgetPswActivity. <br/>
 * 忘记密码<br/>
 * date: 2014-12-09 17:27:10 <br/>
 *
 * @author StephenC
 */
public class ForgetPswActivity extends BaseActivity implements OnClickListener {
    /**
     * The tv phone switch.
     */
//    private TextView tvPhoneSwitch;

    /**
     * The et name.
     */
    private EditText etName;

    /**
     * The et input code.
     */
    private EditText etInputCode;

    /**
     * The et input psw.
     */
    private EditText etInputPsw;

    /**
     * The btn get code.
     */
    private Button btnGetCode;

    /**
     * The btn re get code.
     */
    private Button btnReGetCode;

    /**
     * The btn sure.
     */
    private Button btnSure;

    /**
     * The ll input code.
     */
    private LinearLayout llInputCode;

    /**
     * The ll input psw.
     */
    private LinearLayout llInputPsw;

    /**
     * The iv back.
     */
    private ImageView ivBack;


    /**
     * The tb psw flag.
     */
    private ToggleButton tbPswFlag;

    /**
     * The is email.
     */
    private boolean isEmail = false;

    /**
     * The secondleft.
     */
    int secondleft = 60;

    /**
     * The timer.
     */
    Timer timer;

    /**
     * The dialog.
     */
    ProgressDialog dialog;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        /**
         * The tick time.
         */
        TICK_TIME,

        /**
         * The reg success.
         */
        CHANGE_SUCCESS,

        /**
         * The toast.
         */
        TOAST,

    }

    /**
     * ClassName: Enum ui_statu. <br/>
     * <br/>
     * date: 2014-12-3 10:52:52 <br/>
     *
     * @author Lien
     */
    private enum ui_statu {

        /**
         * The default.
         */
        DEFAULT,

        /**
         * The phone.
         */
        PHONE,

        /**
         * The email.
         */
        EMAIL,
    }

    /**
     * The handler.
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler_key key = handler_key.values()[msg.what];
            switch (key) {

                case TICK_TIME:
                    secondleft--;
                    if (secondleft <= 0) {
                        timer.cancel();
                        btnReGetCode.setEnabled(true);
                        btnReGetCode.setText("重新获取验证码");
                        btnReGetCode
                                .setBackgroundResource(R.drawable.button_blue_short);
                    } else {
                        btnReGetCode.setText(secondleft + "秒后\n重新获取");

                    }
                    break;

                case CHANGE_SUCCESS:
//                    IntentUtils.getInstance().startActivity(ForgetPswActivity.this,
//                            LoginActivity.class);
                    finish();
                    break;

                case TOAST:
                    ToastUtils.showShort(ForgetPswActivity.this, (String) msg.obj);
                    dialog.cancel();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_reset);
        initViews();
        initEvents();
    }

    /**
     * Inits the views.
     */
    private void initViews() {
//        tvPhoneSwitch = (TextView) findViewById(R.id.tvPhoneSwitch);
        etName = (EditText) findViewById(R.id.etName);
        etInputCode = (EditText) findViewById(R.id.etInputCode);
        etInputPsw = (EditText) findViewById(R.id.etInputPsw);
        btnGetCode = (Button) findViewById(R.id.btnGetCode);
        btnReGetCode = (Button) findViewById(R.id.btnReGetCode);
        btnSure = (Button) findViewById(R.id.btnSure);
        llInputCode = (LinearLayout) findViewById(R.id.llInputCode);
        llInputPsw = (LinearLayout) findViewById(R.id.llInputPsw);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        toogleUI(ui_statu.DEFAULT);
        dialog = new ProgressDialog(this);
        dialog.setMessage("处理中，请稍候...");
    }

    /**
     * Inits the events.
     */
    private void initEvents() {
        btnGetCode.setOnClickListener(this);
        btnReGetCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
//        tvPhoneSwitch.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tbPswFlag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
            	if (isChecked) {
					etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					etInputPsw.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetCode:
                String phone = etName.getText().toString().trim();
                if (!StringUtils.isEmpty(phone) && phone.length() == 11) {
                    toogleUI(ui_statu.PHONE);
                    sendVerifyCode(phone);
                } else {
                    ToastUtils.showShort(this, "请输入正确的手机号码。");
                }

                break;
            case R.id.btnReGetCode:
                String phone2 = etName.getText().toString().trim();
                if (!StringUtils.isEmpty(phone2) && phone2.length() == 11) {
                    toogleUI(ui_statu.PHONE);
                    sendVerifyCode(phone2);
                } else {
                    ToastUtils.showShort(this, "请输入正确的手机号码。");
                }
                break;
            case R.id.btnSure:
                doChangePsw();
                break;
            case R.id.tvPhoneSwitch:
                ToastUtils.showShort(this, "该功能暂未实现，敬请期待。^_^");
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
        }
    }

    /**
     * Toogle ui.
     *
     * @param statu the statu
     */
    private void toogleUI(ui_statu statu) {
        if (statu == ui_statu.DEFAULT) {
            llInputCode.setVisibility(View.GONE);
            llInputPsw.setVisibility(View.GONE);
            btnSure.setVisibility(View.GONE);
            btnGetCode.setVisibility(View.VISIBLE);
        } else if (statu == ui_statu.PHONE) {
            llInputCode.setVisibility(View.VISIBLE);
            llInputPsw.setVisibility(View.VISIBLE);
            btnSure.setVisibility(View.VISIBLE);
            btnGetCode.setVisibility(View.GONE);
        } else {

        }
    }

    private void doChangePsw() {

        String phone = etName.getText().toString().trim();
        String code = etInputCode.getText().toString().trim();
        String password = etInputPsw.getText().toString().trim();
        if (phone.length() != 11) {
            Toast.makeText(this, "电话号码格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (code.length() == 0) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "密码小于6位", Toast.LENGTH_SHORT).show();
            return;
        }
        mCenter.cChangeUserPasswordWithCode(phone, code, password);
        dialog.show();
    }

    /**
     * Send verify code.
     *
     * @param phone the phone
     */
    private void sendVerifyCode(final String phone) {
        dialog.show();
        btnReGetCode.setEnabled(false);
        btnReGetCode.setBackgroundResource(R.drawable.button_gray_short);
        secondleft = 60;
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.sendEmptyMessage(handler_key.TICK_TIME.ordinal());
            }
        }, 1000, 1000);

        mCenter.cRequestSendVerifyCode(phone);
    }

    @Override
    protected void didRequestSendVerifyCode(int error, String errorMessage) {
        Log.i("error message ", error + " " + errorMessage);
        if (error == 0) {// 发送成功
            Message msg = new Message();
            msg.what = handler_key.TOAST.ordinal();
            msg.obj = "发送成功";
            handler.sendMessage(msg);
        } else {// 发送失败
            Message msg = new Message();
            msg.what = handler_key.TOAST.ordinal();
            msg.obj = errorMessage;
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void didChangeUserPassword(int error, String errorMessage) {
        if (error == 0) {// 修改成功
            Message msg = new Message();
            msg.what = handler_key.TOAST.ordinal();
            msg.obj = "修改成功";
            handler.sendMessage(msg);
            handler.sendEmptyMessageDelayed(handler_key.CHANGE_SUCCESS.ordinal(), 2000);

        } else {// 修改失败
            Message msg = new Message();
            msg.what = handler_key.TOAST.ordinal();
            msg.obj = errorMessage;
            handler.sendMessage(msg);
        }
        super.didChangeUserPassword(error, errorMessage);
    }
}
