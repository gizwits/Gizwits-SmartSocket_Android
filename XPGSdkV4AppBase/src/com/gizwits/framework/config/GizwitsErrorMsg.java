/**
 * Project Name:XPGSdkV4AppBase
 * File Name:GizwitsErrorMsg.java
 * Package Name:com.gizwits.framework.config
 * Date:2015-1-22 18:16:19
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
package com.gizwits.framework.config;

/**
 * 
 * 错误码枚举类
 * 
 * @author Lien
 */
public enum GizwitsErrorMsg {

	/** The E1. */
	E1(9001, "mac already registered!", "mac已经注册"),

	/** The E2. */
	E2(9002, "product_key invalid", "product key无效"),

	/** The E3. */
	E3(9003, "appid invalid", "appid无效"),

	/** The E4. */
	E4(9004, "token invalid", "token无效"),

	/** The E5. */
	E5(9005, "user not exist", "用户名不存在"),

	/** The E6. */
	E6(9006, "token expired", "token已过期"),

	/** The E7. */
	E7(9007, "m2m_id invalid", "m2m_id无效"),

	/** The E8. */
	E8(9008, "server error", "服务器错误"),

	/** The E9. */
	E9(9009, "code expired", "验证码已过期"),

	/** The E10. */
	E10(9010, "code invalid", "验证码无效"),

	/** The E11. */
	E11(9011, "sandbox scale quota exhausted!", "沙箱环境配额用尽"),

	/** The E12. */
	E12(9012, "production scale quota exhausted!", "生产环境配额用尽"),

	/** The E13. */
	E13(9013, "product has no request scale!", "产品未设置配额"),

	/** The E14. */
	E14(9014, "device not found!", "设备找不到"),

	/** The E15. */
	E15(9015, "form invalid!", "表单无效"),

	/** The E16. */
	E16(9016, "did or passcode invalid!", "did或者passcode无效"),

	/** The E17. */
	E17(9017, "device not bound!", "设备未绑定"),

	/** The E18. */
	E18(9018, "phone unavailable!", "手机不可用"),

	/** The E19. */
	E19(9019, "username unavailable!", "用户名不可用"),

	/** The E20. */
	E20(9020, "username or password error!", "用户名或者密码错误");

	/** 错误描述 */
	private String EngDescript, CHNDescript;

	/** 错误码 */
	private int num;

	/**
	 * 错误码枚举类构造方法
	 * 
	 * @param num
	 *            错误码
	 * @param EngDescript
	 *            错误英文描述
	 * @param CHNDescript
	 *            错误中文描述
	 */
	private GizwitsErrorMsg(int num, String EngDescript, String CHNDescript) {
		this.num = num;
		this.EngDescript = EngDescript;
		this.CHNDescript = CHNDescript;
	}

	/**
	 * 获取错误码英文描述
	 * 
	 * @return 英文描述
	 */
	public String getEngDescript() {
		return EngDescript;
	}

	/**
	 * 获取错误码中文描述
	 * 
	 * @return 中文描述
	 */
	public String getCHNDescript() {
		return CHNDescript;
	}

	/**
	 * 获取错误码
	 * 
	 * @return 错误码
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 根据错误码返回枚举类
	 * 
	 * @param num
	 *            错误码
	 * @return 枚举对象
	 */
	public static GizwitsErrorMsg getEqual(int num) {
		for (GizwitsErrorMsg GEM : GizwitsErrorMsg.values()) {
			if (GEM.getNum() == num)
				return GEM;
		}
		return E1;
	}

}
