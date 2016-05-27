package test;

import com.tanl.kitserver.util.encryption.KitAESCoder;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;


/**
 * --
 * Created by Administrator on 2016/4/26.
 */
public class Main {


	public static void main(String[] args) throws Exception {

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data = {
				new NameValuePair("Uid", "本站用户名"),
				new NameValuePair("Key", "接口安全秘钥"),
				new NameValuePair("smsMob", "手机号码"),
				new NameValuePair("smsText", "验证码：8888")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		System.out.println(result); //打印返回消息状态
		post.releaseConnection();


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
		String password = "aa";//.substring(0, 16);
//		String md5 = KitCoder.encryptMD5(password);
//		String des = KitDESCoder.encrypt(password);
		String aes = KitAESCoder.encrypt(password);

////
//		System.out.println("原文" + password);
////
//		System.out.println("MD5 加密密文：" + md5 + "length = " + md5.length());
////		System.out.println("DES 加密密文：" + des + "length = " + des.length());
////
		System.out.println("AES 加密密文:" + aes + "a");
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
