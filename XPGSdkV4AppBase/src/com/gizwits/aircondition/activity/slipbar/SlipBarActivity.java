package com.gizwits.aircondition.activity.slipbar;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.gizwits.aircondition.activity.control.MainControlActivity;
import com.gizwits.aircondition.R;
import com.gizwits.framework.activity.BaseActivity;
import com.gizwits.framework.activity.account.UserManageActivity;
import com.gizwits.framework.activity.device.DeviceListActivity;
import com.gizwits.framework.activity.device.DeviceManageListActivity;
import com.gizwits.framework.activity.help.AboutActivity;
import com.gizwits.framework.activity.help.HelpActivity;
import com.xpg.common.system.IntentUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

/**
 * Created by Lien on 14/12/21.
 */
public class SlipBarActivity extends BaseActivity implements OnClickListener {
    private RelativeLayout rlDevice;
    private RelativeLayout rlAccount;
    private RelativeLayout rlHelp;
    private RelativeLayout rlAbout;
    private Button btnDeviceList;
    private ListView lvDevice;
    private DeviceAdapter mAdapter;
    private ImageView mCover;
    private static final int DURATION_MS = 400;
    private Animation mStopAnimation;
    private int mShift;
    private boolean isCanBack = false;
    private static boolean mIsSelect = false;
    private Intent mIntent;
    private String chooseMac;
    private String chooseDid;
    private ProgressDialog progressDialog;

    /**
     * ClassName: Enum handler_key. <br/>
     * <br/>
     * date: 2014-11-26 17:51:10 <br/>
     *
     * @author Lien
     */
    private enum handler_key {

        LOGIN_START,

        /**
         * The login success.
         */
        LOGIN_SUCCESS,

        /**
         * The login fail.
         */
        LOGIN_FAIL,

        /**
         * The login timeout.
         */
        LOGIN_TIMEOUT,

    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler_key key = handler_key.values()[msg.what];
            switch (key) {

                case LOGIN_SUCCESS:
                    progressDialog.cancel();
                    if (isCanBack) {
                        backToMain();
                    }
                    break;

                case LOGIN_FAIL:

                    break;
                case LOGIN_TIMEOUT:

                    break;
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slibbar);
        initViews();
        initEvents();
        initParam();
    }

    private void initParam() {
        final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
                AbsoluteLayout.LayoutParams.FILL_PARENT,
                AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0);
        findViewById(R.id.slideout_placeholder).setLayoutParams(lp);
        // 屏幕的宽度
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int displayWidth = displayMetrics.widthPixels;

        // 右边的位移量，60dp转换成px
        int sWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 60, getResources()
                        .getDisplayMetrics());

        // 将快照向右移动的偏移量
        mShift = displayWidth - sWidth;

        // 向右移动的位移动画向右移动shift距离，y方向不变
        Animation startAnimation = getAnimation(mShift);

        // 回退时的位移动画
        mStopAnimation = getAnimation(-mShift);

        startAnimation.setAnimationListener(new StartAnimationListener());

        mStopAnimation.setAnimationListener(new StopAnimationListener());
        mCover.startAnimation(startAnimation);
        mIntent = getIntent();
    }

    private void initViews() {
        rlDevice = (RelativeLayout) findViewById(R.id.rlDevice);
        rlAccount = (RelativeLayout) findViewById(R.id.rlAccount);
        rlHelp = (RelativeLayout) findViewById(R.id.rlHelp);
        rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
        lvDevice = (ListView) findViewById(R.id.lvDevice);
        btnDeviceList = (Button) findViewById(R.id.btnDeviceList);
        mCover = (ImageView) findViewById(R.id.slidedout_cover);
        initBindList();
        mAdapter = new DeviceAdapter(this, bindlist);
        lvDevice.setAdapter(mAdapter);
        progressDialog = new ProgressDialog(SlipBarActivity.this);
        progressDialog.setMessage("设备连接中，请稍候。");
    }

    private void initEvents() {
        rlDevice.setOnClickListener(this);
        rlAccount.setOnClickListener(this);
        rlHelp.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        btnDeviceList.setOnClickListener(this);
        lvDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                mAdapter.setChoosedPos(position);
                loginDevice(bindlist.get(position));
                progressDialog.show();
            }
        });
        Bitmap coverBitmap = MainControlActivity.getView();
        mCover.setOnClickListener(this);
        mCover.setImageBitmap(coverBitmap);
    }

    /**
     * 返回主界面
     */
    private void backToMain() {
        mCover.startAnimation(mStopAnimation);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        backToMain();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlDevice:
                IntentUtils.getInstance().startActivity(SlipBarActivity.this,
                        DeviceManageListActivity.class);
                break;
            case R.id.rlAbout:
                IntentUtils.getInstance().startActivity(SlipBarActivity.this,
                        AboutActivity.class);
                break;
            case R.id.rlAccount:
                IntentUtils.getInstance().startActivity(SlipBarActivity.this,
                        UserManageActivity.class);
                break;
            case R.id.rlHelp:
                IntentUtils.getInstance().startActivity(SlipBarActivity.this,
                        HelpActivity.class);
                break;
            case R.id.slidedout_cover:
                if (isCanBack) {
                    backToMain();
                }
                break;
            case R.id.btnDeviceList:
                IntentUtils.getInstance().startActivity(SlipBarActivity.this,
                        DeviceListActivity.class);
                mCenter.cDisconnect(mXpgWifiDevice);
                break;
        }

    }

    private Animation getAnimation(int offset) {
        Animation animation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE,
                offset, TranslateAnimation.ABSOLUTE, 0,
                TranslateAnimation.ABSOLUTE, 0);
        animation.setDuration(DURATION_MS);
        animation.setFillAfter(true);
        return animation;
    }

    private class BaseAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            isCanBack = true;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
            isCanBack = false;
        }
    }

    private class StartAnimationListener extends BaseAnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            isCanBack = true;
            // 动画结束时回调
            // 将imageview固定在位移后的位置
            mCover.setAnimation(null);
            @SuppressWarnings("deprecation")
            final android.widget.AbsoluteLayout.LayoutParams lp = new android.widget.AbsoluteLayout.LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, mShift,
                    0);
            mCover.setLayoutParams(lp);
        }

        @Override
        public void onAnimationStart(Animation animation) {
            isCanBack = false;
        }
    }

    private class StopAnimationListener extends BaseAnimationListener {
        @Override
        public void onAnimationEnd(Animation animation) {
            isCanBack = true;
            if (mIsSelect) {
                // MainCtrlActivity.getButton().setText(mHeaterList.get(mPos));
                mIsSelect = false;
            }
            mIntent.putExtra("mac", chooseMac);
            mIntent.putExtra("did", chooseDid);
            setResult(Activity.RESULT_OK, mIntent);
            finish();
            overridePendingTransition(0, 0);
        }

        @Override
        public void onAnimationStart(Animation animation) {
            isCanBack = false;
        }
    }

    private class DeviceAdapter extends ArrayAdapter<XPGWifiDevice> {
        private LayoutInflater inflater;

        private int choosedPos = 0;
        private Context ctx;

        public int getChoosedPos() {
            return choosedPos;
        }

        public void setChoosedPos(int choosedPos) {
            this.choosedPos = choosedPos;
        }

        public DeviceAdapter(Context context, List<XPGWifiDevice> objects) {
            super(context, 0, objects);
            ctx=context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder = null;
            XPGWifiDevice device = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.slibbar_item, null);
                holder = new ViewHolder();
                holder.device_checked_tv = (ImageView) convertView
                        .findViewById(R.id.device_checked_tv);
                holder.deviceName_tv = (TextView) convertView
                        .findViewById(R.id.deviceName_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.deviceName_tv.setText(device.getProductName());
            if (getChoosedPos() == position) {
                holder.deviceName_tv.setSelected(true);
                holder.device_checked_tv.setSelected(true);
                chooseMac = device.getMacAddress();
                chooseDid = device.getDid();
            } else {
                holder.deviceName_tv.setSelected(false);
                holder.device_checked_tv.setSelected(false);
            }
            
            if(device.isOnline())
            	holder.deviceName_tv.setTextColor(ctx.getResources().getColor(R.color.text_blue));
            else
            	holder.deviceName_tv.setTextColor(ctx.getResources().getColor(R.color.text_gray));

            return convertView;

        }

    }

    private static class ViewHolder {
        ImageView device_checked_tv;
        TextView deviceName_tv;
    }

    private void loginDevice(XPGWifiDevice xpgWifiDevice) {
        mXpgWifiDevice = xpgWifiDevice;
        mXpgWifiDevice.setListener(deviceListener);
        mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
    }

    @Override
    protected void didLogin(XPGWifiDevice device, int result) {
        if (result == 0) {
            mXpgWifiDevice = device;
            handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
        } else {
            handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
        }

    }

}
