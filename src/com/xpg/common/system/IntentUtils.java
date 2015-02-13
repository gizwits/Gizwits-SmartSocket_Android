package com.xpg.common.system;

import java.io.File;

import com.xpg.common.useful.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;


public class IntentUtils {
	private static IntentUtils instance = null;

	/** 返回该类的一个实例 */
	public static IntentUtils getInstance() {
		if (instance == null) {
			synchronized (IntentUtils.class) {
				if (instance == null)
					instance = new IntentUtils();
			}
		}
		return instance;
	}
	
//	/**
//	 * 安装APK
//	 * @param context
//	 * @param file APK文件对象
//	 */
//	public void installApk( Context ctx, File file )
//	{
//		Intent intent = new Intent();
//
//		intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//		intent.setAction( android.content.Intent.ACTION_VIEW );
//
//		/* 调用getMIMEType()来取得MimeType */
//		String type = MimeTypeUtils.getMIMEType( file );
//
//		/* 设置intent的file与MimeType */
//		intent.setDataAndType( Uri.fromFile( file ), type );
//
//		ctx.startActivity( intent );
//	}
	
	/** 
	 * 判断是否已经安装某个应用
	 * 
	 * @param packageName 应用程序的包名
	 * @return
	 */
	public boolean existPackage( Context ctx, String packageName )
	{
		try {
			PackageManager pm = ctx.getPackageManager();
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (NameNotFoundException e) {
		     e.printStackTrace();
		     return false;
		}
	}
	
	/**
	 * 启动某个应用程序
	 * @param ctx
	 * @param packageName 应用程序的包名
	 */
	public void startPackage( Context ctx, String packageName )
	{
		Intent intent = ctx.getPackageManager().getLaunchIntentForPackage( packageName );
		if(intent != null)
		{
			intent.addFlags( Intent.FLAG_ACTIVITY_REORDER_TO_FRONT );
			ctx.startActivity( intent );
		}
	}
	
	/**
	 * 调用系统的分享程序
	 * @param ctx
	 * @param tip 分享内容
	 */
	public void share( Context ctx, String tip )
	{
		Intent intent = new Intent( Intent.ACTION_SEND );
		intent.setType( "text/plain" );
//		intent.putExtra( Intent.EXTRA_SUBJECT, "分享" ); //将以彩信形式
		intent.putExtra( Intent.EXTRA_TEXT, tip );
		ctx.startActivity( Intent.createChooser( intent, "分享" ) );
	}
	
	

	/**
	 * 打开系统的浏览器浏览网页
	 * @param context
	 * @param url
	 */
	public void startSystemBrowser( Context ctx, String url )
	{
		try {
			Uri uri = Uri.parse( url );
			Intent it = new Intent();
			it.setAction( "android.intent.action.VIEW" );
			it.setData( uri );
			it.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
			it.setClassName( "com.android.browser",
					"com.android.browser.BrowserActivity" );
			ctx.startActivity( it );
		}
		catch(Exception e) {
			startBrowser(url, ctx);
		}
		
	}

	/**
	 * 打开浏览器
	 * @param url
	 * @param context
	 */
	public void startBrowser( String url, Context ctx )
	{
		Uri uri = Uri.parse( url );
		Intent it = new Intent( Intent.ACTION_VIEW, uri );
		ctx.startActivity( it );
	}

	/**
	 * 启动某个Activity
	 * @param context
	 * @param cls
	 */
	public void startActivity( Context ctx, Class cls )
	{
		/* new一个Intent对象，并指定class */
		Intent intent = new Intent();
		intent.setClass( ctx, cls );
		/* 调用Activity */
		ctx.startActivity( intent );
	}

	/** 跳转到系统发短信界面 */
	public void gotoSystemSendMessage( Context ctx, String number, String body )
	{
		Intent intent = null;
		if(StringUtils.isEmpty( number ))
			intent = new Intent( Intent.ACTION_SENDTO, Uri.parse( "smsto:" ) );
		else
			intent = new Intent( Intent.ACTION_SENDTO, Uri.parse( "smsto:" + number ) );
		intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		
		if(!StringUtils.isEmpty( body ))
			intent.putExtra( "sms_body", body );
		intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		ctx.startActivity( intent );
	}

	/** 跳转到系统发彩信界面 */
	public void gotoSendMMS( Context ctx, String number )
	{
		Intent intent = new Intent( Intent.ACTION_SENDTO, Uri.fromParts(
				"mmsto", number, null ) );
		intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
		ctx.startActivity( intent );
	}

	
	/**
	 * 回收该类的数据资源
	 */
	public static void recycle()
	{
		instance = null;
	}
}
