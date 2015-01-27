/**
 * Project Name:XPGSdkV4AppBase
 * File Name:RefreshableListView.java
 * Package Name:com.gizwits.framework.widget
 * Date:2015-1-27 14:48:23
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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gizwits.aircondition.R;

// TODO: Auto-generated Javadoc
/**
 *  
 * ClassName: Class RefreshableListView. <br/> 
 * 刷新列表
 * <br/>
 * date: 2015-1-27 14:48:23 <br/> 
 *
 * @author Lien
 */
public class RefreshableListView extends ListView {

	/** The m header container. */
	private View mHeaderContainer = null;
	
	/** The m header view. */
	private View mHeaderView = null;
	
	/** The m arrow. */
	private ImageView mArrow = null;
	
	/** The m progress. */
	private ProgressBar mProgress = null;
	
	/** The m text. */
	private TextView mText = null;
	
	/** The m y. */
	private float mY = 0;
	
	/** The m historical y. */
	private float mHistoricalY = 0;
	
	/** The m historical top. */
	private int mHistoricalTop = 0;
	
	/** The m initial height. */
	private int mInitialHeight = 0;
	
	/** The m flag. */
	private boolean mFlag = false;
	
	/** The m arrow up. */
	private boolean mArrowUp = false;
	
	/** The m is refreshing. */
	private boolean mIsRefreshing = false;
	
	/** The m header height. */
	private int mHeaderHeight = 0;
	
	/** The m listener. */
	private OnRefreshListener mListener = null;

	/** The Constant REFRESH. */
	private static final int REFRESH = 0;
	
	/** The Constant NORMAL. */
	private static final int NORMAL = 1;
	
	/** The Constant HEADER_HEIGHT_DP. */
	private static final int HEADER_HEIGHT_DP = 62;

	// private static final String TAG =
	// RefreshableListView.class.getSimpleName();

	/**
	 * Instantiates a new refreshable list view.
	 *
	 * @param context the context
	 */
	public RefreshableListView(final Context context) {
		super(context);
		initialize();
	}

	/**
	 * Instantiates a new refreshable list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public RefreshableListView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	/**
	 * Instantiates a new refreshable list view.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 * @param defStyle the def style
	 */
	public RefreshableListView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);
		initialize();
	}

	// 下拉刷新监听
	/**
	 * Sets the on refresh listener.
	 *
	 * @param l the new on refresh listener
	 */
	public void setOnRefreshListener(final OnRefreshListener l) {
		mListener = l;
	}

	// 完成刷新
	/**
	 * Complete refreshing.
	 */
	public void completeRefreshing() {
		mProgress.setVisibility(View.INVISIBLE);
		mArrow.setVisibility(View.VISIBLE);
		mHandler.sendMessage(mHandler.obtainMessage(NORMAL, mHeaderHeight, 0));
		mIsRefreshing = false;
		invalidateViews();
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView#onInterceptTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onInterceptTouchEvent(final MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mHandler.removeMessages(REFRESH);
			mHandler.removeMessages(NORMAL);
			mY = mHistoricalY = ev.getY();
			if (mHeaderContainer.getLayoutParams() != null) {
				mInitialHeight = mHeaderContainer.getLayoutParams().height;
			}
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(final MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			mHistoricalTop = getChildAt(0).getTop();
			break;
		case MotionEvent.ACTION_UP:
			if (!mIsRefreshing) {
				if (mArrowUp) {
					startRefreshing();
					mHandler.sendMessage(mHandler.obtainMessage(REFRESH,
							(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
				} else {
					if (getChildAt(0).getTop() == 0) {
						mHandler.sendMessage(mHandler.obtainMessage(NORMAL,
								(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
					}
				}
			} else {
				mHandler.sendMessage(mHandler.obtainMessage(REFRESH,
						(int) (ev.getY() - mY) / 2 + mInitialHeight, 0));
			}
			mFlag = false;
			break;
		}
		return super.onTouchEvent(ev);
	}

	/* (non-Javadoc)
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(final MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_MOVE
				&& getFirstVisiblePosition() == 0) {
			float direction = ev.getY() - mHistoricalY;
			int height = (int) (ev.getY() - mY) / 2 + mInitialHeight;
			if (height < 0) {
				height = 0;
			}

			float deltaY = Math.abs(mY - ev.getY());
			ViewConfiguration config = ViewConfiguration.get(getContext());
			if (deltaY > config.getScaledTouchSlop()) {

				// Scrolling downward
				if (direction > 0) {
					// Refresh bar is extended if top pixel of the first item is
					// visible
					if (getChildAt(0).getTop() == 0) {
						if (mHistoricalTop < 0) {

							// mY = ev.getY(); // TODO works without
							// this?mHistoricalTop = 0;
						}

						// Extends refresh bar
						setHeaderHeight(height);

						// Stop list scroll to prevent the list from
						// overscrolling
						ev.setAction(MotionEvent.ACTION_CANCEL);
						mFlag = false;
					}
				} else if (direction < 0) {
					// Scrolling upward

					// Refresh bar is shortened if top pixel of the first item
					// is
					// visible
					if (getChildAt(0).getTop() == 0) {
						setHeaderHeight(height);

						// If scroll reaches top of the list, list scroll is
						// enabled
						if (getChildAt(1) != null
								&& getChildAt(1).getTop() <= 1 && !mFlag) {
							ev.setAction(MotionEvent.ACTION_DOWN);
							mFlag = true;
						}
					}
				}
			}

			mHistoricalY = ev.getY();
		}
		try {
			return super.dispatchTouchEvent(ev);
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.AbsListView#performItemClick(android.view.View, int, long)
	 */
	@Override
	public boolean performItemClick(final View view, final int position,
			final long id) {
		if (position == 0) {
			// This is the refresh header element
			return true;
		} else {
			return super.performItemClick(view, position - 1, id);
		}
	}

	// 初始化视图
	/**
	 * Initialize.
	 */
	private void initialize() {
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mHeaderContainer = inflater.inflate(R.layout.messagelist_head, null);
		mHeaderView = mHeaderContainer
				.findViewById(R.id.refreshable_list_header);
		mArrow = (ImageView) mHeaderContainer
				.findViewById(R.id.refreshable_list_arrow);
		mProgress = (ProgressBar) mHeaderContainer
				.findViewById(R.id.refreshable_list_progress);
		mText = (TextView) mHeaderContainer
				.findViewById(R.id.refreshable_list_text);
		addHeaderView(mHeaderContainer);

		mHeaderHeight = (int) (HEADER_HEIGHT_DP * getContext().getResources()
				.getDisplayMetrics().density);
		setHeaderHeight(0);
	}

	/**
	 * Sets the header height.
	 *
	 * @param height the new header height
	 */
	private void setHeaderHeight(final int height) {
		if (height <= 1) {
			mHeaderView.setVisibility(View.GONE);
		} else {
			mHeaderView.setVisibility(View.VISIBLE);
		}

		// Extends refresh bar
		LayoutParams lp = (LayoutParams) mHeaderContainer.getLayoutParams();
		if (lp == null) {
			lp = new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
		lp.height = height;
		mHeaderContainer.setLayoutParams(lp);

		// Refresh bar shows up from bottom to top
		LinearLayout.LayoutParams headerLp = (LinearLayout.LayoutParams) mHeaderView
				.getLayoutParams();
		if (headerLp == null) {
			headerLp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT);
		}
		headerLp.topMargin = -mHeaderHeight + height;
		mHeaderView.setLayoutParams(headerLp);

		if (!mIsRefreshing) {
			// If scroll reaches the trigger line, start refreshing
			if (height > mHeaderHeight && !mArrowUp) {
				mArrow.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.rotate));
				mText.setText("刷新数据");
				rotateArrow();
				mArrowUp = true;
			} else if (height < mHeaderHeight && mArrowUp) {
				mArrow.startAnimation(AnimationUtils.loadAnimation(
						getContext(), R.anim.rotate));
				mText.setText("下拉刷新");
				rotateArrow();
				mArrowUp = false;
			}
		}
	}

	/**
	 * Rotate arrow.
	 */
	private void rotateArrow() {
		Drawable drawable = mArrow.getDrawable();
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.save();
		canvas.rotate(180.0f, canvas.getWidth() / 2.0f,
				canvas.getHeight() / 2.0f);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		canvas.restore();
		mArrow.setImageBitmap(bitmap);
	}

	// 下拉松开后开始刷新数据
	/**
	 * Start refreshing.
	 */
	private void startRefreshing() {
		mArrow.setVisibility(View.INVISIBLE);
		mProgress.setVisibility(View.VISIBLE);
		mText.setText("加载...");
		mIsRefreshing = true;

		if (mListener != null) {
			mListener.onRefresh(this);
		}
	}

	/** The m handler. */
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(final Message msg) {
			super.handleMessage(msg);

			int limit = 0;
			switch (msg.what) {
			case REFRESH:
				limit = mHeaderHeight;
				break;
			case NORMAL:
				limit = 0;
				break;
			}

			// Elastic scrolling
			if (msg.arg1 >= limit) {
				setHeaderHeight(msg.arg1);
				int displacement = (msg.arg1 - limit) / 10;
				if (displacement == 0) {
					mHandler.sendMessage(mHandler.obtainMessage(msg.what,
							msg.arg1 - 1, 0));
				} else {
					mHandler.sendMessage(mHandler.obtainMessage(msg.what,
							msg.arg1 - displacement, 0));
				}
			}
		}

	};

	/**
	 * The listener interface for receiving onRefresh events.
	 * The class that is interested in processing a onRefresh
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addOnRefreshListener<code> method. When
	 * the onRefresh event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see OnRefreshEvent
	 */
	public interface OnRefreshListener {
		
		/**
		 * On refresh.
		 *
		 * @param listView the list view
		 */
		public void onRefresh(RefreshableListView listView);
	}
}
