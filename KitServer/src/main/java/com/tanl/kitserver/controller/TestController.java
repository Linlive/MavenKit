package com.tanl.kitserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/4/26.
 */
@Controller
@RequestMapping(value = "/")
public class TestController {
	@Autowired
	ApplicationContext context;
	@RequestMapping(value = "/hello")
	public ModelAndView hello(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		return mv;
	}

	@RequestMapping(value = "/getSMS")
	public void getSms(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String session = request.getSession().getId();
		System.out.println("session = " + session);
		response.getWriter().write(session);
		/*
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
		NameValuePair[] data = {
				new NameValuePair("Uid", "nil_tan"),
				new NameValuePair("Key", "dfeb60885c7d53ab6b8c"),
				new NameValuePair("smsMob", "18380165651"),
				new NameValuePair("smsText", "验证码：A656128")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		System.out.println(result); //打印返回消息状态
		post.releaseConnection();
		*/
	}
}
