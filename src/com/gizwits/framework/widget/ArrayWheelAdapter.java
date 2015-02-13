/**
 * Project Name:XPGSdkV4AppBase
 * File Name:ArrayWheelAdapter.java
 * Package Name:com.gizwits.framework.widget
 * Date:2015-1-27 14:47:49
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

// TODO: Auto-generated Javadoc
/**
 * The simple Array wheel adapter.
 *
 * @author Lien
 * @param <T> the element type
 */
public class ArrayWheelAdapter<T> implements WheelAdapter {
	
	/**  The default items length. */
	public static final int DEFAULT_LENGTH = -1;
	
	// items
	/** The items. */
	private T items[];
	// length
	/** The length. */
	private int length;

	/**
	 * Constructor.
	 *
	 * @param items the items
	 * @param length the max items length
	 */
	public ArrayWheelAdapter(T items[], int length) {
		this.items = items;
		this.length = length;
	}
	
	/**
	 * Contructor.
	 *
	 * @param items the items
	 */
	public ArrayWheelAdapter(T items[]) {
		this(items, DEFAULT_LENGTH);
	}

	
	/* (non-Javadoc)
	 * @see com.gizwits.framework.widget.WheelAdapter#getItem(int)
	 */
	public String getItem(int index) {
		if (index >= 0 && index < items.length) {
			return items[index].toString();
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.gizwits.framework.widget.WheelAdapter#getItemsCount()
	 */
	public int getItemsCount() {
		return items.length;
	}

	
	/* (non-Javadoc)
	 * @see com.gizwits.framework.widget.WheelAdapter#getMaximumLength()
	 */
	public int getMaximumLength() {
		return length;
	}

}
