package com.tanl.kitserver.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取系统根目录
 * Created by Administrator on 2016/5/26.
 */
public class ServerRootPath {

	/***
	 * 	System.out.println("request path = " + request.getContextPath());
	 * 	System.out.println( request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath() +"/");
	 * 	System.out.println("request" + request.getServletPath() + "
	 * 	path info=" + request.getSession().getServletContext().getRealPath("/img"));
	 */
	/**
	 * 获取系统根目录
	 * @param request
	 * @return
	 */
	public static String getRootPath(HttpServletRequest request) {

		return request.getSession().getServletContext().getRealPath("/");
	}
}
