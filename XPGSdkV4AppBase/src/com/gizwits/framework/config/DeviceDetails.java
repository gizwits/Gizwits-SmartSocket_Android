package com.gizwits.framework.config;

import com.gizwits.powersocket.R;

/**
 * 设备图片资源枚举类.
 *
 * @author Sunny
 */
public enum DeviceDetails {
	D1(1,R.drawable.details_icon1,"电视",R.drawable.head_icon1,R.drawable.head_icon1_down), 
	D2(2,R.drawable.details_icon2,"插座",R.drawable.head_icon2,R.drawable.head_icon2_down), 
	D3(3,R.drawable.details_icon3,"空调",R.drawable.head_icon3,R.drawable.head_icon3_down), 
	D4(4,R.drawable.details_icon4,"冰箱",R.drawable.head_icon4,R.drawable.head_icon4_down), 
	D5(5,R.drawable.details_icon5,"洗衣机",R.drawable.head_icon5,R.drawable.head_icon5_down), 
	D6(6,R.drawable.details_icon6,"DVD",R.drawable.head_icon6,R.drawable.head_icon6_down), 
	D7(7,R.drawable.details_icon7,"音响",R.drawable.head_icon7,R.drawable.head_icon7_down), 
	D8(8,R.drawable.details_icon8,"厨具",R.drawable.head_icon8,R.drawable.head_icon8_down), 
	D9(9,R.drawable.details_icon9,"风扇",R.drawable.head_icon9,R.drawable.head_icon9_down);

	private int num;
	private int resList;
	private String name;
	private int res;
	private int resSelected;

	/**
	 * 设备图片资源枚举类构造方法.
	 *
	 * @param num             序号
	 * @param resList         设备列表图片资源
	 * @param name            资源名称
	 * @param res             默认图片资源
	 * @param resSelected     选中图片资源
	 */
	private DeviceDetails(int num, int resList,String name,int res,int resSelected) {
		this.num = num;
		this.resList=resList;
		this.name=name;
		this.res = res;
		this.resSelected=resSelected;
	}

	/**
	 * 获取默认图片资源.
	 *
	 * @return 默认图片资源
	 */
	public int getRes() {
		return res;
	}

	/**
	 * 获取序号.
	 *
	 * @return 序号
	 */
	public int getNum() {
		return num;
	}

	/**
	 * 获取设备列表图片资源.
	 *
	 * @return 设备列表图片资源
	 */
	public int getResList() {
		return resList;
	}

	/**
	 * 获取资源名称.
	 *
	 * @return 资源名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取选中图片资源.
	 *
	 * @return 选中图片资源
	 */
	public int getResSelected() {
		return resSelected;
	}

	/**
	 * 根据序号返回枚举类.
	 *
	 * @param num            序号
	 * @return 枚举对象
	 */
	public static DeviceDetails findByNum(int num) {
		for (DeviceDetails dd : DeviceDetails.values()) {
			if (dd.getNum() == num)
				return dd;
		}
		return D2;
	}
}
