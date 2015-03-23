/**
 * Project Name:XPGSdkV4AppBase
 * File Name:SlidingMenu.java
 * Package Name:com.gizwits.framework.widget
 * Date:2015-3-20 14:48:07
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
package com.gizwits.framework.widget;

import com.gizwits.framework.utils.DensityUtil;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * The Class SlidingMenu.
 * 
 * 侧拉菜单
 * 
 * @author Sunny
 */
public class SlidingMenu extends HorizontalScrollView {
	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;

	/**
	 * dp
	 */
	private int mMenuRightPadding;

	/**
	 * dip
	 */
	private final int num = 60;

	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;

	/**
	 * 菜单的半宽度
	 */
	private int mHalfMenuWidth;

	/**
	 * 菜单打开标志位
	 */
	private boolean isOpen;

	/**
	 * 初始化标志位
	 */
	private boolean once;

	/**
	 * 侧拉菜单控件
	 */
	private ViewGroup mMenu;

	/**
	 * 构造函数
	 */
	public SlidingMenu(Context context) {
		this(context, null, 0);
	}

	/**
	 * 构造函数
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	/**
	 * 构造函数
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScreenWidth = getScreenWidth(context);

		mMenuRightPadding = DensityUtil.dip2px(context, num);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		/**
		 * 显示的设置一个宽度
		 */
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) wrapper.getChildAt(0);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			mMenu.getLayoutParams().width = mMenuWidth;
			for (int i = 1; i < wrapper.getChildCount(); i++) {
				ViewGroup mContent = (ViewGroup) wrapper.getChildAt(i);
				mContent.getLayoutParams().width = mScreenWidth;
			}
		}
		updateState();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			// 将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	/**
	 * 更新当前状态
	 */
	private void updateState() {
		if (isOpen) {
			this.scrollTo(0, 0);
		} else {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (isOpen&&ev.getX() > mMenuWidth) {
				toggle();
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!isOpen)
			return true;

		int action = ev.getAction();
		switch (action) {
		// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			if (scrollX > mHalfMenuWidth) {
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;

		this.smoothScrollTo(0, 0);
		this.isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			this.isOpen = false;
		}
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale);

	}

	public boolean isOpen() {
		return this.isOpen;
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	private static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

}
