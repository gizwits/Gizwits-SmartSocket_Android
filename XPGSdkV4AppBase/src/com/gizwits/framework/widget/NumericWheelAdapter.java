package com.gizwits.framework.widget;



public class NumericWheelAdapter implements WheelAdapter {
	
	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;
	
	// Values
	private int minValue;
	private int maxValue;
	
	// format
	private String format;
	//就否间隔10
	private boolean iften;
	private String unit;
	
	/**
	 * Default constructor
	 */
	public NumericWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	public NumericWheelAdapter(int minValue, int maxValue) {
		this(minValue, maxValue,false,null,"");
	}
	
	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 * @param format the format string
	 */
	public NumericWheelAdapter(int minValue, int maxValue, String format) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.format = format;
		this.iften=false;
		this.unit="";
	}
	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 * @param format the format string
	 */
	public NumericWheelAdapter(int minValue, int maxValue,boolean iften,String format,String unit) {
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.iften=iften;
		this.format = format;
		this.unit=unit;
	}

	@Override
	public String getItem(int index) {
		String result="";
		if (index >= 0 && index < getItemsCount()) {
			int value;
			if (iften) {//乘以10
				value = (minValue + index)*10;
			}else {
				value = (minValue + index);
			}
			if (format!=null) {//有格式化
				result=String.format(format, value);
				return result;
			}else {
				if (!"".equals(unit)) {//有单位 
					result=String.valueOf(value)+unit;
					return result;
				}else {
					return Integer.toString(value);
				}
			}
		}
		return result;
	}
//	@Override
//	public String getItem(int index) {
//		if (index >= 0 && index < getItemsCount()) {
//			int value = minValue + index;
//			return format != null ? String.format(format, value) : Integer.toString(value);
//		}
//		return null;
//	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}
	
	@Override
	public int getMaximumLength() {
		int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
		int maxLen = Integer.toString(max).length();
		if (minValue < 0) {
			maxLen++;
		}
		return maxLen;
	}
}
