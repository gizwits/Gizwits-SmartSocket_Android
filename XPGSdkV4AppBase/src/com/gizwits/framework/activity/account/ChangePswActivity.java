/**
 * Project Name:XPGSdkV4AppBase
 * File Name:ChangePswActivity.java
 * Package Name:com.gizwits.framework.activity.account
 * Date:2015-1-27 14:44:53
 * Copyright (c) 2014~2015 Xtreme Programming Group, Inc.
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.gizwits.framework.activity.account;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.xpg.common.useful.StringUtils;
import com.xpg.ui.utils.ToastUtils;

// TODO: Auto-generated Javadoc
//TODO: Auto-generated Javadoc

/**
 * ClassName: Class ChangePswActivity. <br/>
 * 修改密码，该类主要用于用户通过旧密码修改密码。<br/>
 * date: 2014-12-09 17:27:10 <br/>
 *
 * @author StephenC
 */
public class ChangePswActivity extends BaseActivity implements OnClickListener {

    /**
     * The iv TopBar leftBtn.
     */
    private ImageView ivBack;


    /**
     * The et user name.
     */
    private EditText etPswOld;

    /**
     * The et Input Password.
     */
    private EditText etPswNew;

    /**
     * The tb show passwrod.
     */
    private ToggleButton tbPswFlag;

    /**
     * The btn confirm the word.
     */
    private Button btnConfirm;

    /** 用户输入的新密码 */
    private String newPsw;

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initViews();
        initEvents();
    }

    /**
     * Inits the events.
     */
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
                    etPswOld.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
                    etPswNew.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
                } else {
                    etPswOld.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPswNew.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPswOld.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
                    etPswNew.setKeyListener(DigitsKeyListener
							.getInstance(getResources().getString(
									R.string.register_name_digits)));
                }

            }
        });

    }

    /**
     * Inits the views.
     */
    private void initViews() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        etPswOld = (EditText) findViewById(R.id.etPswOld);
        etPswNew = (EditText) findViewById(R.id.etPswNew);
        tbPswFlag = (ToggleButton) findViewById(R.id.tbPswFlag);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
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
                    ToastUtils.showShort(ChangePswActivity.this, "请输入新的密码");
                    return;
                }
                if (newPsw.length() < 6 || newPsw.length() > 16) {
    				Toast.makeText(this, "密码长度应为6~16", Toast.LENGTH_SHORT).show();
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

    /**
     * 处理修改密码动作
     *
     * @param oldPsw the old psw
     * @param newPsw the new psw
     */
    private void changePsw(String oldPsw, String newPsw) {
        mCenter.cChangeUserPassword(setmanager.getToken(), oldPsw, newPsw);
    }

    /* (non-Javadoc)
     * @see com.gizwits.framework.activity.BaseActivity#didChangeUserPassword(int, java.lang.String)
     */
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
