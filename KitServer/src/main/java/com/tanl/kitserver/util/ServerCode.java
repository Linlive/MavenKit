package com.tanl.kitserver.util;

/**
 * 服务器返回码
 * Created by Administrator on 2016/5/29.
 */
public class ServerCode {
	//room
	public static final String SERVER_ADDRESS = "http://192.168.1.222:8080/KitServer";
	//hp pc
//	public static final String SERVER_ADDRESS = "http://192.168.56.2:8080/KitServer";
	//ali server
//	public static final String SERVER_ADDRESS = "http://42.96.138.253:8080/KitServer";
//	public static final String SERVER_ADDRESS = "http://127.0.0.1:8080/KitServer";

	public static final int SERVER_REQUEST_OK = 200;
	public static final int SERVER_GET_USER_INFO_NULL = 404;

	public static final int DATABASE_EXCEPTION = 501;
	public static final int DATABASE_OUT_NULL = 502;

	public static final int DATA_FORMAT_ERROR = 510;
	public static final int APK_DOES_NOT_EXSIST_ERROR = 511;
}
