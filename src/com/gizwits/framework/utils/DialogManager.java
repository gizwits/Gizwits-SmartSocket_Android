/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DialogManager.java
 * Package Name:com.gizwits.framework.utils
 * Date:2015-1-27 14:47:29
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
package com.gizwits.framework.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.gizwits.framework.widget.NumericWheelAdapter;
import com.gizwits.framework.widget.WheelView;
import com.gizwits.powersocket.R;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class DialogManager. <br/> 
 * 对话框管理
 * <br/>
 * date: 2015-1-27 14:47:29 <br/> 
 *
 * @author Lien
 */
public class DialogManager {

	/**
	 * Gets the no network dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @return the no network dialog
	 */
	public static Dialog getNoNetworkDialog(Context ctx) {
		Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog);
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View contentView = layoutInflater.inflate(R.layout.dialog_no_network,
				null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(contentView);
		return dialog;
	}
	

	/**
	 * 注销对话框.
	 *
	 * @param ctx the ctx
	 * @param r            右按钮监听器
	 * @return the logout dialog
	 */
	public static Dialog getLogoutDialog(final Activity ctx, OnClickListener r) {
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(R.layout.dialog_logout, null);
		Button leftBtn = (Button) v.findViewById(R.id.left_btn);
		Button rightBtn = (Button) v.findViewById(R.id.right_btn);
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		rightBtn.setOnClickListener(r);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}
	
	/**
	 * 删除对话框.
	 *
	 * @param ctx the ctx
	 * @param r            右按钮监听器
	 * @return the unbind dialog
	 */
	public static Dialog getUnbindDialog(final Activity ctx, OnClickListener r) {
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(R.layout.dialog_unbind, null);
		Button leftBtn = (Button) v.findViewById(R.id.left_btn);
		Button rightBtn = (Button) v.findViewById(R.id.right_btn);
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		rightBtn.setOnClickListener(r);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}
	
	/**
	 * 确定关机对话框
	 * 
	 * @param ctx
	 * @param contentStr
	 *            对话框内容
	 * @param r
	 *            右按钮监听器
	 * @return
	 */
	public static Dialog getPowerOffDialog(final Activity ctx, OnClickListener r) {
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(R.layout.dialog_power_off, null);
		Button leftBtn = (Button) v.findViewById(R.id.left_btn);
		Button rightBtn = (Button) v.findViewById(R.id.right_btn);
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		rightBtn.setOnClickListener(r);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}
	
	/**
	 * 确定修改密码对话框
	 * 
	 * @param ctx
	 * @param contentStr
	 *            对话框内容
	 * @param r
	 *            右按钮监听器
	 * @return
	 */
	public static Dialog getPswChangeDialog(final Activity ctx, OnClickListener r) {
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(R.layout.dialog_psw_change, null);
		Button leftBtn = (Button) v.findViewById(R.id.left_btn);
		Button rightBtn = (Button) v.findViewById(R.id.right_btn);
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		rightBtn.setOnClickListener(r);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}

	/**
	 * 设备故障无法使用,拨打客服热线 对话框.
	 *
	 * @param ctx the ctx
	 * @param contentStr            对话框内容
	 * @param r            右按钮监听器
	 * @return the device errir dialog
	 */
	public static Dialog getDeviceErrirDialog(final Activity ctx,
			String contentStr, OnClickListener r) {
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(R.layout.dialog_alarm_for_conditioner,
				null);
		TextView content = (TextView) v.findViewById(R.id.fault_content);
		Button leftBtn = (Button) v.findViewById(R.id.fault_left_btn);
		Button rightBtn = (Button) v.findViewById(R.id.fault_right_btn);

		content.setText(contentStr);
		leftBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		rightBtn.setOnClickListener(r);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}

	/**
	 * 定时对话框.
	 *
	 * @param ctx the ctx
	 * @param l the l
	 * @param titleStr the title str
	 * @param index the index
	 * @return the wheel timing dialog
	 */
	public static Dialog get2WheelTimingDialog(final Activity ctx,
			final On2TimingChosenListener l, String titleStr, int indexOne,int indexTwo) {
		
		DisplayMetrics metric = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; 
		
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(
				R.layout.dialog_2choose_timing_conditioner, null);
		TextView title = (TextView) v.findViewById(R.id.wifiSSID_tv);
		title.setText(titleStr);
		Button confi_btn = (Button) v.findViewById(R.id.confi_btn);
		Button cancel_btn = (Button) v.findViewById(R.id.cancel_btn);
		cancel_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		
		final WheelView wheelveiew = (WheelView) v
				.findViewById(R.id.wheel_view_timing);
		final WheelView wheelveiewMin = (WheelView) v
				.findViewById(R.id.wheel_view_timing_min);
		
		if(width<=540){
			wheelveiew.setTEXT_SIZE(30);
			wheelveiew.setADDITIONAL_ITEM_HEIGHT(60);
			wheelveiew.setADDITIONAL_ITEMS_SPACE(5);
			
			wheelveiewMin.setTEXT_SIZE(30);
			wheelveiewMin.setADDITIONAL_ITEM_HEIGHT(60);
			wheelveiewMin.setADDITIONAL_ITEMS_SPACE(5);
		}
		
		wheelveiew.setAdapter(new NumericWheelAdapter(0,23));
		wheelveiew.setCyclic(true);
		wheelveiew.setLabel(":");
		// 初始化时显示的数据
		wheelveiew.setCurrentItem(indexOne);
		
		wheelveiewMin.setAdapter(new NumericWheelAdapter(0,59,"%02d"));
		wheelveiewMin.setCyclic(true);
		// 初始化时显示的数据
		wheelveiewMin.setCurrentItem(indexTwo);
		
		confi_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int indexHour = wheelveiew.getCurrentItem();
				int indexMin = wheelveiewMin.getCurrentItem();
				l.timingChosen(indexHour,indexMin);
				dismissDialog(ctx, dialog);
			}
		});
		
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}

	/**
	 * 延时对话框.
	 *
	 * @param ctx the ctx
	 * @param l the l
	 * @param titleStr the title str
	 * @param index the index
	 * @return the wheel timing dialog
	 */
	public static Dialog getWheelTimingDialog(final Activity ctx,
			final OnTimingChosenListener l, String titleStr, int index) {
		
		DisplayMetrics metric = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; 
		
		final Dialog dialog = new Dialog(ctx, R.style.noBackgroundDialog) {
		};
		LayoutInflater layoutInflater = LayoutInflater.from(ctx);
		View v = layoutInflater.inflate(
				R.layout.dialog_choose_timing_conditioner, null);
		TextView title = (TextView) v.findViewById(R.id.wifiSSID_tv);
		title.setText(titleStr);
		Button confi_btn = (Button) v.findViewById(R.id.confi_btn);
		Button cancel_btn = (Button) v.findViewById(R.id.cancel_btn);
		final WheelView wheelveiew = (WheelView) v
				.findViewById(R.id.wheel_view_timing);
		
		if(width<=540){
			wheelveiew.setTEXT_SIZE(30);
			wheelveiew.setADDITIONAL_ITEM_HEIGHT(60);
			wheelveiew.setADDITIONAL_ITEMS_SPACE(5);
		}
		
		wheelveiew.setAdapter(new NumericWheelAdapter(0,23));
		wheelveiew.setCyclic(true);
		wheelveiew.setLabel("h后");
		// 初始化时显示的数据
		wheelveiew.setCurrentItem(index);
		cancel_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismissDialog(ctx, dialog);
			}
		});
		confi_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = wheelveiew.getCurrentItem();
				l.timingChosen(index);
				dismissDialog(ctx, dialog);
			}
		});

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}
	
	/**
	 * Show dialog.
	 *
	 * @param ctx the ctx
	 * @param dialog the dialog
	 */
	public static void showDialog(Context ctx, Dialog dialog) {
		if (dialog != null && !dialog.isShowing() && ctx != null
				&& !((Activity) ctx).isFinishing()) {
			dialog.show();
		}
	}

	/**
	 * 隐藏dialog，加了context生命判断，避免窗口句柄泄漏.
	 *
	 * @param ctx            dialog依赖的activity
	 * @param dialog            欲隐藏的dialog
	 */
	public static void dismissDialog(Activity ctx, Dialog dialog) {
		if (dialog != null && dialog.isShowing() && ctx != null
				&& !ctx.isFinishing())
			dialog.dismiss();
	}

	/**
	 * wheel view dialog.
	 *
	 * @see OnTimingChosenEvent
	 */

	public interface On2TimingChosenListener {
		
		/**
		 * Timing hour chosen.
		 *
		 * @param HourTime the hour time
		 * 
		 * @param HourTime the hour time
		 */
		public void timingChosen(int HourTime,int MinTime);
		
	}
	
	/**
	 * wheel view dialog.
	 *
	 * @see OnTimingChosenEvent
	 */

	public interface OnTimingChosenListener {
		
		/**
		 * Timing chosen.
		 *
		 * @param time the time
		 */
		public void timingChosen(int time);
	}
}
