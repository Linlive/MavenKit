package test;

import com.tanl.kitserver.util.encryption.KitCoder;

/**
 * --
 * Created by Administrator on 2016/4/26.
 */
public class Main {

	public static void main(String[] args) throws Exception {

//		Gson gson = new Gson();
//		JSONObject ss = new JSONObject();
//		ss.put("aa", "sssss");
//		ss.put("bb", "aaaasssss");
//		String string = gson.toJson(ss);
//		System.out.println(string);


//		String a = new String(Base64.encode("cccda加减ddaf,.pp'[luioluiol".getBytes("utf-8"), 16));
//		String b = new String(Base64.decode(a, 16));
//		System.out.println(a + "\n" + b);

//		String password2 = "p?./a)_85545afasoin_+)_)(wodda889745rd/.,.154";
		String password = "a12";//.substring(0, 16);
		String md5 = KitCoder.encryptMD5(password);
//		String des = KitDESCoder.encrypt(password);
//		String aes = KitAESCoder.encrypt(password);
////
//		System.out.println("原文" + password);
////
		System.out.println("MD5 加密密文：" + md5 + "length = " + md5.length());
////		System.out.println("DES 加密密文：" + des + "length = " + des.length());
////
//		System.out.println("AES 加密密文：" + aes);
//		System.out.println("解密\t" + KitAESCoder.decrypt(aes));

//		System.out.println(KitAESCoder.encrypt("aa", true));

//		byte[] inputData0 = inputStr0.getBytes();
//		inputData0 = KitDESCoder.encrypt(realPassword.getBytes(), KitDESCoder.initKey(KIT_SERVER_KEY));
//		System.out.println("\n加密后的密串: " + KitDESCoder.encryptBASE64(inputData0));
//
//		byte[] outputData0 = KitDESCoder.decrypt(inputData0, KitDESCoder.initKey(KIT_SERVER_KEY));
//		String outputStr0 = new String(outputData0);
//
//		System.out.println("解密后:\t" + outputStr0);
/*
		System.out.println("----------------------");
		String inputStr = "hello";
		String key = KitAESCoder.initKey();
		System.out.println("原文:\t" + inputStr + "密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = KitAESCoder.encrypt(inputData, key);
		System.out.println("加密后:\t" + KitAESCoder.encryptBASE64(inputData));
		byte[] outputData = KitAESCoder.decrypt(inputData, key);
		String outputStr = new String(outputData);
		System.out.println("解密后:\t" + outputStr + "\n------------------");


		String inputStr2 = "hello3";
		String key2 = KitAESCoder.initKey();
		System.out.println("原文:\t" + inputStr2 + "密钥:\t" + key2);


		byte[] inputData2 = inputStr2.getBytes();
		inputData2 = KitAESCoder.encrypt(inputData2, key2);
		System.out.println("加密后:\t" + KitAESCoder.encryptBASE64(inputData2));
		byte[] outputData2 = KitAESCoder.decrypt(inputData2, key2);
		String outputStr2 = new String(outputData2);
		System.out.println("解密后:\t" + outputStr2);
		*/
	}
}
