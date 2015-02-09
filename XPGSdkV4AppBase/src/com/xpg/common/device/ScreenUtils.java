package com.xpg.common.device;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
 
/**
 * <P>
 * 获得屏幕相关的辅助类
 * <P>
 * 
 * @author StephenC
 * @version 1.00
 * 
 * */
public class ScreenUtils
{
	/**
	 * 获取屏幕宽度
	 * 
	 * @param context
	 *            上下文
	 * @return   屏幕宽度
	 * 
	 * */
    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
 
    /**
	 * 获取屏幕高度
	 * 
	 * @param context
	 *            上下文
	 * @return   屏幕高度
	 * 
	 * */
    public static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
 
    /**
	 * 获取屏幕截图,包含状态栏
	 * 
	 * @param activity
	 *            activity
	 * @return   屏幕截图
	 * 
	 * */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
 
    }
 
    /**
	 * 获取屏幕截图,不包含状态栏
	 * 
	 * @param activity
	 *            activity
	 * @return   屏幕截图
	 * 
	 * */
    public static Bitmap snapShotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
 
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;
 
    }
 
}
