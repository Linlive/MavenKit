package com.tanl.kitserver.util.Sms;

import com.tanl.kitserver.model.bean.client.SessionWrapper;
import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.common.Client;
import com.tanl.kitserver.util.common.GenerateRandomString;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/6/2.
 */
public class SmsSender {

	static HashMap<String, String> userSessionMap = new HashMap<String, String>();
	static HashMap<String, String> userCodeMap = new HashMap<String, String>();

	public static HashMap<String, String> getUserSessionMap() {
		return userSessionMap;
	}

	public static HashMap<String, String> getUserCodeMap() {
		return userCodeMap;
	}

	public static boolean sendSms (JSONObject userObj, String session, HttpServletResponse response) throws IOException {

		String code = GenerateRandomString.generateVerificationString(6);

		//获取用户设置密码的方式
		if (userObj.has("userId")) {
			userSessionMap.put(userObj.getString("userId"), session);
			userCodeMap.put(userObj.getString("userId"), code);
		} else if (userObj.has("phone")) {
			userSessionMap.put(userObj.getString("phone"), session);
			userCodeMap.put(userObj.getString("phone"), code);
		} else if (userObj.has("email")) {
			userSessionMap.put(userObj.getString("email"), session);
			userCodeMap.put(userObj.getString("email"), code);
		} else {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return false;
		}

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
		NameValuePair[] data = {
				new NameValuePair("Uid", "nil_tan"),
				new NameValuePair("Key", "dfeb60885c7d53ab6b8c"),
				new NameValuePair("smsMob", userObj.getString("phone")),
				new NameValuePair("smsText", "验证码:" + code + "，5分钟内有效。如非本人操作，请忽略。")};
		post.setRequestBody(data);
		client.executeMethod(post);

		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		System.out.println("短信已发送至" + userObj.getString("phone") + "数量：" + result); //打印返回消息状态
		post.releaseConnection();

		SessionWrapper sw = new SessionWrapper();
		sw.setSessionId(session);
		sw.setStatus(true);
		sw.setExtraInfo("empty");
		Client.writeToClient(response.getWriter(), sw);
		return true;
	}

	public static boolean sendEmail (String emailAddr) {

		return false;
	}

	public static boolean checkSms (String code) {

		return false;
	}
}
