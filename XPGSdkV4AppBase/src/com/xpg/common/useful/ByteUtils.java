package com.xpg.common.useful;

/**
 * <P>
 * byte数组操作类，包含byte数组的输出，格式化，转换等方法。
 * <P>
 * 
 * @author Lien Li
 * @version 1.00
 */
public class ByteUtils {
	/**
	 * 将指定字符串src，以每两个字符分割转换为16进制形式\n 如："2B44EFD9" to byte[]{0x2B, 0×44,
	 * 0xEF,0xD9}
	 * 
	 * @param src
	 *            String 传入的字符串
	 * @return byte[] 返回的数组
	 */
	public static byte[] HexString2Bytes(String src) {
		int leng = src.length() / 2;
		byte[] ret = new byte[leng];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < leng; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF" to 0xEF
	 * 
	 * @param src0
	 *            ASCII字符1
	 * @param src1
	 *            ASCII字符2
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 将指定byte数组以16进制的形式打印到控制台
	 * 
	 * @param hint
	 *            标签
	 * @param b
	 *            需要打印的数组
	 */
	public static void printHexString(String hint, byte[] b) {
		System.out.print(hint);
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			System.out.print(hex.toUpperCase() + " ");
		}
		System.out.println("");
	}

	/**
	 * 将指定byte数组转换为16进制的形式
	 * 
	 * @param b
	 *            传入的数组
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = "0" + hex;
			}
			ret += hex.toUpperCase() + " ";
		}
		return ret;
	}

	/**
	 * 将指定int转换为16进制的形式
	 * 
	 * @param i
	 *            指定的整数
	 * @return String
	 */
	public static String int2HaxString(int i) {
		String hex = Integer.toHexString(i);
		if (hex.length() == 1) {
			hex = "0" + hex;
		}
		return hex;
	}

	/**
	 * 讲指定的short按位取值
	 * 
	 * @param n
	 *            指定的字节
	 * @param index
	 *            位数下标
	 * @return boolean
	 * */
	public static boolean getBitFromShort(int n, int index) {
		if (((n >> index) & 0x1) > 0)
			return true;
		else
			return false;
	}
}
