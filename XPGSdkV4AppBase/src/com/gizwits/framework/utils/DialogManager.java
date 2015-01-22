/**
 * Project Name:XPGSdkV4AppBase
 * File Name:DialogManager.java
 * Package Name:com.gizwits.aircondition.utils
 * Date:2014-12-4 14:21:07
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

import com.gizwits.aircondition.R;
import com.gizwits.framework.widget.ArrayWheelAdapter;
import com.gizwits.framework.widget.WheelView;

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
	 * 注销对话框
	 * 
	 * @param ctx
	 * @param contentStr
	 *            对话框内容
	 * @param r
	 *            右按钮监听器
	 * @return
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
	 * 删除对话框
	 * 
	 * @param ctx
	 * @param contentStr
	 *            对话框内容
	 * @param r
	 *            右按钮监听器
	 * @return
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
	 * 设备故障无法使用,拨打客服热线 对话框
	 * 
	 * @param ctx
	 * @param contentStr
	 *            对话框内容
	 * @param l
	 *            左按钮监听器
	 * @param r
	 *            右按钮监听器
	 * @return
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
	 * 定时开关机对话框
	 * 
	 * @param ctx
	 * @param l
	 * @return
	 */
	public static Dialog getWheelTimingDialog(final Activity ctx,
			final OnTimingChosenListener l, String titleStr, int index) {
		
		DisplayMetrics metric = new DisplayMetrics();
		ctx.getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels; 
		
		
		String[] hours = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
				"11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				"21", "22", "23", "24", "关闭" };
		final int[] hour = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15,
				16, 17, 18, 19, 20, 21, 22, 23, 24, 0 };
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
		
		wheelveiew.setAdapter(new ArrayWheelAdapter<String>(hours));
		wheelveiew.setCyclic(true);
		wheelveiew.setLabel("小时");
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
				l.timingChosen(hour[index]);
				dismissDialog(ctx, dialog);
			}
		});

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContentView(v);
		return dialog;
	}

	public static void showDialog(Context ctx, Dialog dialog) {
		if (dialog != null && !dialog.isShowing() && ctx != null
				&& !((Activity) ctx).isFinishing()) {
			dialog.show();
		}
	}

	/**
	 * 隐藏dialog，加了context生命判断，避免窗口句柄泄漏
	 * 
	 * @param ctx
	 *            dialog依赖的activity
	 * @param dialog
	 *            欲隐藏的dialog
	 */
	public static void dismissDialog(Activity ctx, Dialog dialog) {
		if (dialog != null && dialog.isShowing() && ctx != null
				&& !ctx.isFinishing())
			dialog.dismiss();
	}

	/**
	 * wheel view dialog
	 * 
	 * @return
	 */

	public interface OnTimingChosenListener {
		public void timingChosen(int time);
	}
}
