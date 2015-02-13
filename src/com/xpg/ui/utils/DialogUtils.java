package com.xpg.ui.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;

/**
 * 
 * <P>
 * 数据加载提示框显示与取消
 * <P>
 * 
 * @author Lien Li
 * @version 1.00
 */
public class DialogUtils {

	/**
	 * 数据加载对话框框消失方法，避免矿口句柄溢出
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param pd
	 *            目标对话框
	 * 
	 * 
	 * */
	public static void dismiss(Activity ctx, ProgressDialog pd) {
		if (pd != null && pd.isShowing() && ctx != null && !ctx.isFinishing())
			pd.dismiss();
	}

	/**
	 * 普通对话框框消失方法，避免矿口句柄溢出
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param dialog
	 *            目标对话框
	 * 
	 * 
	 * */
	public static void dismiss(Activity ctx, Dialog dialog) {
		if (dialog != null && dialog.isShowing() && ctx != null
				&& !ctx.isFinishing())
			dialog.dismiss();
	}

	/**
	 * 数据加载提示框显示
	 * 
	 * @param ctx
	 *            依附的activity
	 * @param title
	 *            对话框标题
	 * @param message
	 *            对话框正文
	 * @return ProgressDialog
	 */
	public static ProgressDialog showTip(Activity ctx, String title,
			String message) {
		ProgressDialog pd = ProgressDialog.show(ctx, title, message);
		pd.setCancelable(true);
		return pd;
	}
}
