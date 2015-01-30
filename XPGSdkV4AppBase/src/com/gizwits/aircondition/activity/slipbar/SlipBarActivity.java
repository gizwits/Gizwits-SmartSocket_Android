/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SlipBarActivity.java
 * Package Name:com.gizwits.aircondition.activity.slipbar
 * Date:2015-1-27 14:44:46
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
import android.util.Log;
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
import com.gizwits.framework.utils.DensityUtil;
import com.gizwits.framework.utils.StringUtils;
import com.xpg.common.system.IntentUtils;
import com.xpg.ui.utils.ToastUtils;
import com.xtremeprog.xpgconnect.XPGWifiDevice;

// TODO: Auto-generated Javadoc
/**
 * Created by Lien on 14/12/21.
 * 
 * 侧滑菜单界面
 * 
 * @author Lien
 */
public class SlipBarActivity extends BaseActivity implements OnClickListener {

	/** The rl device. */
	private RelativeLayout rlDevice;

	/** The rl account. */
	private RelativeLayout rlAccount;

	/** The rl help. */
	private RelativeLayout rlHelp;

	/** The rl about. */
	private RelativeLayout rlAbout;

	/** The btn device list. */
	private Button btnDeviceList;

	/** The lv device. */
	private ListView lvDevice;

	/** The m adapter. */
	private DeviceAdapter mAdapter;

	/** The m cover. */
	private ImageView mCover;

	/** 动画总时长 */
	private static final int DURATION_MS = 400;

	/** 退出界面动画 */
	private Animation mStopAnimation;

	/** 将快照向右移动的偏移量 */
	private int mShift;

	/** 动画是否完成标志位 */
	private boolean isCanBack = false;

	/** 是否可以开始退出界面动画 */
	private static boolean mIsSelect = false;

	/** 意图 */
	private Intent mIntent;

	/** 选中设备的mac地址 */
	private String chooseMac;

	/** 选中设备的did */
	private String chooseDid;

	/** The progress dialog. */
	private ProgressDialog progressDialog;

	/** 是否超时标志位 */
	private boolean isTimeOut = false;

	/**
	 * ClassName: Enum handler_key. <br/>
	 * <br/>
	 * date: 2014-11-26 17:51:10 <br/>
	 * 
	 * @author Lien
	 */
	private enum handler_key {

		/** The login start. */
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

	/** The handler. */
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			handler_key key = handler_key.values()[msg.what];
			switch (key) {
			case LOGIN_SUCCESS:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				if (isCanBack) {
					backToMain();
				}
				break;
			case LOGIN_FAIL:
				handler.removeMessages(handler_key.LOGIN_TIMEOUT.ordinal());
				progressDialog.cancel();
				ToastUtils.showShort(SlipBarActivity.this, "设备连接失败");
				break;
			case LOGIN_TIMEOUT:
				isTimeOut = true;
				progressDialog.cancel();
				ToastUtils.showShort(SlipBarActivity.this, "设备连接超时");
				break;
			}
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gizwits.framework.activity.BaseActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slibbar);
		initViews();
		initEvents();
		initParam();
	}

	/**
	 * Inits the param.
	 */
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

	/**
	 * Inits the views.
	 */
	private void initViews() {
		rlDevice = (RelativeLayout) findViewById(R.id.rlDevice);
		rlAccount = (RelativeLayout) findViewById(R.id.rlAccount);
		rlHelp = (RelativeLayout) findViewById(R.id.rlHelp);
		rlAbout = (RelativeLayout) findViewById(R.id.rlAbout);
		lvDevice = (ListView) findViewById(R.id.lvDevice);
		btnDeviceList = (Button) findViewById(R.id.btnDeviceList);
		mCover = (ImageView) findViewById(R.id.slidedout_cover);
		mAdapter = new DeviceAdapter(this, bindlist);
		lvDevice.setAdapter(mAdapter);
		for (int i = 0; i < bindlist.size(); i++) {
			if (bindlist.get(i).getDid()
					.equalsIgnoreCase(mXpgWifiDevice.getDid()))
				mAdapter.setChoosedPos(i);
		}
		progressDialog = new ProgressDialog(SlipBarActivity.this);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("设备连接中，请稍候。");
	}

	/**
	 * Inits the events.
	 */
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
				if (!mAdapter.getItem(position).isOnline())
					return;

				if (mAdapter.getItem(position).isConnected())
					return;

				mAdapter.setChoosedPos(position);
				loginDevice(bindlist.get(position));
			}
		});
		Bitmap coverBitmap = MainControlActivity.getView();
		mCover.setOnClickListener(this);
		mCover.setImageBitmap(coverBitmap);
	}

	@Override
	public void onResume() {
		super.onResume();
		initBindList();
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 返回主界面.
	 */
	private void backToMain() {
		if (mXpgWifiDevice.isConnected()) {
			DisconnectOtherDevice();
			mCover.startAnimation(mStopAnimation);
		} else {
			loginDevice(mXpgWifiDevice);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		backToMain();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
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
			mCenter.cDisconnect(mXpgWifiDevice);
			DisconnectOtherDevice();
			IntentUtils.getInstance().startActivity(SlipBarActivity.this,
					DeviceListActivity.class);
			finish();
			break;
		}

	}

	/**
	 * Gets the animation.
	 * 
	 * @param offset
	 *            the offset
	 * @return the animation
	 */
	private Animation getAnimation(int offset) {
		Animation animation = new TranslateAnimation(
				TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE,
				offset, TranslateAnimation.ABSOLUTE, 0,
				TranslateAnimation.ABSOLUTE, 0);
		animation.setDuration(DURATION_MS);
		animation.setFillAfter(true);
		return animation;
	}

	/**
	 * 检查出了选中device，其他device有没有连接上
	 * 
	 * @param mac
	 *            the mac
	 * @param did
	 *            the did
	 * @return the XPG wifi device
	 */
	private void DisconnectOtherDevice() {
		for (XPGWifiDevice theDevice : bindlist) {
			if (theDevice.isConnected()
					&& !theDevice.getDid().equalsIgnoreCase(
							mXpgWifiDevice.getDid()))
				mCenter.cDisconnect(theDevice);
		}
	}

	/**
	 * The listener interface for receiving baseAnimation events. The class that
	 * is interested in processing a baseAnimation event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addBaseAnimationListener<code> method. When
	 * the baseAnimation event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see BaseAnimationEvent
	 */
	private class BaseAnimationListener implements Animation.AnimationListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.animation.Animation.AnimationListener#onAnimationEnd
		 * (android.view.animation.Animation)
		 */
		@Override
		public void onAnimationEnd(Animation animation) {
			isCanBack = true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.animation.Animation.AnimationListener#onAnimationRepeat
		 * (android.view.animation.Animation)
		 */
		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.view.animation.Animation.AnimationListener#onAnimationStart
		 * (android.view.animation.Animation)
		 */
		@Override
		public void onAnimationStart(Animation animation) {
			isCanBack = false;
		}
	}

	/**
	 * The listener interface for receiving startAnimation events. The class
	 * that is interested in processing a startAnimation event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addStartAnimationListener<code> method. When
	 * the startAnimation event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see StartAnimationEvent
	 */
	private class StartAnimationListener extends BaseAnimationListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.gizwits.aircondition.activity.slipbar.SlipBarActivity.
		 * BaseAnimationListener
		 * #onAnimationEnd(android.view.animation.Animation)
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.gizwits.aircondition.activity.slipbar.SlipBarActivity.
		 * BaseAnimationListener
		 * #onAnimationStart(android.view.animation.Animation)
		 */
		@Override
		public void onAnimationStart(Animation animation) {
			isCanBack = false;
		}
	}

	/**
	 * The listener interface for receiving stopAnimation events. The class that
	 * is interested in processing a stopAnimation event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addStopAnimationListener<code> method. When
	 * the stopAnimation event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see StopAnimationEvent
	 */
	private class StopAnimationListener extends BaseAnimationListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.gizwits.aircondition.activity.slipbar.SlipBarActivity.
		 * BaseAnimationListener
		 * #onAnimationEnd(android.view.animation.Animation)
		 */
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.gizwits.aircondition.activity.slipbar.SlipBarActivity.
		 * BaseAnimationListener
		 * #onAnimationStart(android.view.animation.Animation)
		 */
		@Override
		public void onAnimationStart(Animation animation) {
			isCanBack = false;
		}
	}

	/**
	 * 
	 * ClassName: Class DeviceAdapter. <br/>
	 * <br/>
	 * date: 2015-1-27 14:44:48 <br/>
	 * 
	 * @author Lien
	 */
	private class DeviceAdapter extends ArrayAdapter<XPGWifiDevice> {

		/** The inflater. */
		private LayoutInflater inflater;

		/** The choosed pos. */
		private int choosedPos = 0;

		/** The ctx. */
		private Context ctx;

		/**
		 * Gets the choosed pos.
		 * 
		 * @return the choosed pos
		 */
		public int getChoosedPos() {
			return choosedPos;
		}

		/**
		 * Sets the choosed pos.
		 * 
		 * @param choosedPos
		 *            the new choosed pos
		 */
		public void setChoosedPos(int choosedPos) {
			this.choosedPos = choosedPos;
			notifyDataSetChanged();
		}

		/**
		 * Instantiates a new device adapter.
		 * 
		 * @param context
		 *            the context
		 * @param objects
		 *            the objects
		 */
		public DeviceAdapter(Context context, List<XPGWifiDevice> objects) {
			super(context, 0, objects);
			ctx = context;
			inflater = LayoutInflater.from(context);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			int px=DensityUtil.dip2px(ctx, getCount()*50);
			parent.setLayoutParams(new android.widget.LinearLayout.LayoutParams(
					android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
					px));

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

			if (StringUtils.isEmpty(device.getRemark())) {
				String macAddress = device.getMacAddress();
				int size = macAddress.length();
				holder.deviceName_tv.setText(device.getProductName()
						+ macAddress.substring(size - 4, size));
			} else {
				holder.deviceName_tv.setText(device.getRemark());
			}

			if (getChoosedPos() == position) {
				holder.deviceName_tv.setSelected(true);
				holder.device_checked_tv.setSelected(true);
				chooseMac = device.getMacAddress();
				chooseDid = device.getDid();
			} else {
				holder.deviceName_tv.setSelected(false);
				holder.device_checked_tv.setSelected(false);
			}

			if (device.isOnline())
				holder.deviceName_tv.setTextColor(ctx.getResources().getColor(
						R.color.text_blue));
			else
				holder.deviceName_tv.setTextColor(ctx.getResources().getColor(
						R.color.text_gray));

			return convertView;

		}

	}

	/**
	 * 
	 * ClassName: Class ViewHolder. <br/>
	 * <br/>
	 * date: 2015-1-27 14:44:48 <br/>
	 * 
	 * @author Lien
	 */
	private static class ViewHolder {

		/** The device_checked_tv. */
		ImageView device_checked_tv;

		/** The device name_tv. */
		TextView deviceName_tv;
	}

	/**
	 * Login device.
	 * 
	 * @param xpgWifiDevice
	 *            the xpg wifi device
	 */
	private void loginDevice(XPGWifiDevice xpgWifiDevice) {
		mXpgWifiDevice = xpgWifiDevice;
		mXpgWifiDevice.setListener(deviceListener);
		mXpgWifiDevice.login(setmanager.getUid(), setmanager.getToken());
		progressDialog.show();
		isTimeOut = false;
		handler.sendEmptyMessageDelayed(handler_key.LOGIN_TIMEOUT.ordinal(),
				5000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.gizwits.framework.activity.BaseActivity#didLogin(com.xtremeprog.
	 * xpgconnect.XPGWifiDevice, int)
	 */
	@Override
	protected void didLogin(XPGWifiDevice device, int result) {
		if (isTimeOut)
			return;

		if (result == 0) {
			mXpgWifiDevice = device;
			handler.sendEmptyMessage(handler_key.LOGIN_SUCCESS.ordinal());
		} else {
			handler.sendEmptyMessage(handler_key.LOGIN_FAIL.ordinal());
		}

	}

}
