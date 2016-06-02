package com.tanl.kitserver.util.common;

import java.util.Random;

/**
 * Created by Administrator on 2016/5/24.
 */
public class GenerateRandomString {

	private static final String GOODS_ID_BASE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+-=_";
	private static final int GOODS_ID_BASE_LENGTH = GOODS_ID_BASE.length();
	private static final int GOODS_ID_LEN = 20;
	private static final int SMS_CODE_LEN = 6;

	/**
	 * 随机生成字符串，组成商品的目录
	 * 其中，商品目录包含userId + thisString + upLoadName三个要素
	 * @return
	 */
	public static String generateString () {

		return generateString(GOODS_ID_LEN);
	}
	public static String generateString (int length) {

		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(GOODS_ID_BASE.charAt(random.nextInt(GOODS_ID_BASE_LENGTH)));
		}
		return sb.toString();
	}
	public static String generateVerificationString (int length) {

		Random random = new Random();
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(GOODS_ID_BASE.charAt(random.nextInt(10)));
		}
		return sb.toString();
	}
}
