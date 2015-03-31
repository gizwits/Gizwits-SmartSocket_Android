/**
 * Project Name:XPGSdkV4AppBase
 * File Name:MyInputFilter.java
 * Package Name:com.gizwits.framework.widget
 * Date:2015-3-31 17:27:10
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

import android.text.LoginFilter.UsernameFilterGMail;

/**
 * ClassName: Class MyInputFilter. <br/>
 * 输入过滤，该类主要用于过滤用户密码的输入。<br/>
 * date: 2015-3-31 17:27:10 <br/>
 * 
 * @author SunnyDing
 */
public class MyInputFilter extends UsernameFilterGMail {

	public MyInputFilter() {
		super();
	}

	@Override
	public boolean isAllowed(char c) {
		// Allow [a-zA-Z0-9@.]
		if ('0' <= c && c <= '9')
			return true;
		if ('a' <= c && c <= 'z')
			return true;
		if ('A' <= c && c <= 'Z')
			return true;
		return false;
	}

}
