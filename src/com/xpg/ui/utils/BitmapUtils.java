package com.xpg.ui.utils;

import java.io.InputStream;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

/**
 * 
 * <P>
 * Bitmap处理方法
 * <P>
 * 
 * @author Sunny Ding
 * @version 1.00
 */
public class BitmapUtils {
	/**
	 * 改变Bitmap透明度
	 * 
	 * @param sourceImg
	 *            原始Bitmap
	 * @param number
	 *            需要改变的透明度百分比
	 * @return   目标Bitmap
	 * 
	 * */
	public static Bitmap getTransparentBitmap(Bitmap sourceImg, int number) {
		int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

		sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0,
				sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值

		number = number * 255 / 100;
		for (int i = 0; i < argb.length; i++) {
			if (argb[i] != 0) {// 透明的颜色不作处理
				argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);
			}
		}
		// 用处理好的数组建Bitmap
		sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(),
				sourceImg.getHeight(), Config.ARGB_8888);
		return sourceImg;
	}
	
	/**
	 * 建立Bitmap防止Out Of Memery Crash
	 * 
	 * @param res
	 *            原始资源
	 * @param resId
	 *            资源id
	 * @param reqWidth 
	 *            目标Bitmap宽度
	 * @param reqHeight 
	 *            目标Bitmap长度  
	 * @return  目标Bitmap
	 * 
	 * */
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inPurgeable = true;
		options.inInputShareable = true;
		// BitmapFactory.decodeResource(res, resId, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize =calculateInSampleSize(options, reqWidth,
				reqHeight);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		InputStream is =res.openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, options);
	}
	
	//根据指定宽高计算图片适合的尺寸
	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
}
