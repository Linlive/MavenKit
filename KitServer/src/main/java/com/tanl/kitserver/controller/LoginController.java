package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.model.bean.client.SessionWrapper;
import com.tanl.kitserver.service.AdminService;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.Sms.SmsSender;
import com.tanl.kitserver.util.common.Client;
import com.tanl.kitserver.util.common.ClientInfoObj;
import com.tanl.kitserver.util.common.NormalCheck;
import com.tanl.kitserver.util.email.EmailSender;
import com.tanl.kitserver.util.encryption.KitAESCoder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 处理login-register等请求
 * Created by Administrator on 2016/5/1.
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

	@Autowired
	ApplicationContext context;

	final boolean LOGIN_STATUS_SUCCESS = true;
	final boolean LOGIN_STATUS_FAILED = false;

	static String sessionId = "";

	static HashMap<String, String> userSessionMap;
	static HashMap<String, String> userCodeMap;
	@Resource(name = "userService")
	UserService userService;

	@Resource(name = "adminService")
	AdminService adminService;

	private final int PASSWORD_MAX_LENGTH = 60;


	@RequestMapping(value = "/login")
	public void login (HttpServletRequest request, HttpServletResponse response) throws IOException {

//		userService = (UserService) context.getBean("userService");
//		adminService = (AdminService) context.getBean("adminService");

		UserDo clientUser = getInfo(request);
		if (clientUser == null) return;

		checkFormat(clientUser, response);
		if (clientUser.getPermissionLevel() == 0) {
			handleAdmin(clientUser, request, response);
			return;
		}

		ServiceResult<UserDo> result = checkDbOk(clientUser, request, response);

		if (result == null) return;

		UserDo userDB = result.getData();
		boolean notFound = userDB == null;

		if (notFound) {
			handleNotFond(response);
			return;
		}
		if (!(clientUser.getUserPassword().equals(userDB.getUserPassword()))) {//密码不匹配
			handlePasswordNotMatch(response);
			return;
		}
		handleLoginSuccess(response);
	}


	@RequestMapping(value = "/register")
	public void register (HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject;
		UserDo clientUser = getInfo(request);
		if (clientUser == null) return;

		checkFormat(clientUser, response);
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();
		ServiceResult<UserDo> result = checkDbOk(clientUser, request, response);

		if (result == null) return;

		jsonObject = new JSONObject();
		UserDo userDB = result.getData();
		boolean exist = (userDB != null);
		if (exist) {
			jsonObject.put("status", LOGIN_STATUS_FAILED);
			jsonObject.put("message", "userIsExist");

			infoObj.setData(jsonObject);
			infoObj.setErrorCode(0);
			infoObj.setDigestMessage("userIsExist");
			infoObj.setOperateSuccess(true);
			Client.writeToClient(response.getWriter(), infoObj);
			return;
		}
		ServiceResult<Integer> insertResult = userService.insertUser(clientUser);
		if (!insertResult.isSuccess()) {
			return;
		}

		jsonObject.put("status", true);
		infoObj.setData(jsonObject);
		infoObj.setDigestMessage("registerSuccess");
		infoObj.setOperateSuccess(true);
		infoObj.setErrorCode(0);
		Client.writeToClient(response.getWriter(), infoObj);
	}

	@RequestMapping(value = "/resetPassword")
	public void resetPassword (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientInfo = Client.readFromClient(request.getReader());
		if (clientInfo == null || clientInfo.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JSONObject jsonObject = new JSONObject(clientInfo);
		if (!jsonObject.has("email") && !jsonObject.has("phone")) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		String emailAddr = null;
		String phoneNumber = null;
		if (jsonObject.has("eamil")) {
			emailAddr = jsonObject.getString("email");
		}
		if (jsonObject.has("phone")) {
			phoneNumber = jsonObject.getString("phone");
		}
		if (NormalCheck.isEmail(emailAddr)) {
			EmailSender.sendTo(emailAddr);
		}
		if (NormalCheck.isPhoneNumber(phoneNumber)) {
			sessionId = request.getSession().getId();
			SmsSender.sendSms(jsonObject, sessionId, response);
			userSessionMap = SmsSender.getUserSessionMap();
			userCodeMap = SmsSender.getUserCodeMap();
			//getSms(jsonObject, request.getSession().getId(), response);
		}
	}
	/**
	private boolean getSms (JSONObject userObj, String session, HttpServletResponse response) throws IOException {

		System.out.println("session = " + session);
		sessionId = session;

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
				new NameValuePair("smsText", "验证码:" + code)};
		post.setRequestBody(data);
		client.executeMethod(post);

		String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
		System.out.println(result); //打印返回消息状态
		post.releaseConnection();

		SessionWrapper sw = new SessionWrapper();
		sw.setSessionId(sessionId);
		sw.setStatus(true);
		sw.setExtraInfo("empty");
		Client.writeToClient(response.getWriter(), sw);
		return true;
	}
	*/

	@RequestMapping(value = "/checkSms")
	public void checkSms (HttpServletRequest request, HttpServletResponse response) throws IOException {

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(request.getReader());
		Gson g = new Gson();
		SessionWrapper sw = g.fromJson(je, SessionWrapper.class);
		String clientSession = sw.getSessionId();
		String serverSesstion;

		if (null == clientSession || clientSession.length() < 1) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		String userId = sw.getUserId();
		if (null == userId || userId.length() < 1) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		serverSesstion = userSessionMap.get(userId);
		if (!serverSesstion.equals(clientSession)) {
			//不是一个正确的回话
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		SessionWrapper sw2 = new SessionWrapper();
		sw2.setSessionId(clientSession);
		if (!(sw.getCode().equals(userCodeMap.get(sw.getUserId())))) {
			sw.setStatus(false);
			sw.setExtraInfo("check error");
			Client.writeToClient(response.getWriter(), sw2);
			return;
		}
		//验证成功
		sw2.setSessionId(clientSession);
		if (!resetPassword(sw.getUserId(), sw.getExtraInfo())) {
			sw2.setStatus(false);
			sw2.setExtraInfo("密码修改失败");
			Client.writeToClient(response.getWriter(), sw2);
			return;
		}
		sw2.setStatus(true);
		sw2.setExtraInfo("密码修改成功");
		Client.writeToClient(response.getWriter(), sw2);
	}

	private boolean resetPassword (String phone, String password) {

		UserDo userDo = new UserDo();
		userDo.setUserPhone(phone);
		try {
			userDo.setUserPassword(KitAESCoder.encrypt(password));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		ServiceResult<Boolean> result = userService.resetPassword(userDo);
		if (null == result) {
			return false;
		}
		if (!result.isSuccess()) {
			return false;
		}

		return true;
	}

	private void handleAdmin (UserDo clientUer, HttpServletRequest request, HttpServletResponse response) throws IOException {

		AdminDo adminDo = new AdminDo();
		adminDo.setName(clientUer.getUserName());
		adminDo.setPassword(clientUer.getUserPassword());
		ServiceResult<AdminDo> daoResult = null;

		try {
			daoResult = checkDbOk(adminDo, request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (daoResult == null) return;

		AdminDo adminDoDb = daoResult.getData();
		boolean notFound = adminDoDb == null;
		if (notFound) {
			handleNotFond(response);
			return;
		}
		if (!adminDo.getPassword().equals(adminDoDb.getPassword())) {
			handlePasswordNotMatch(response);
			return;
		}
		handleLoginSuccess(response);
	}

	private void handleNotFond (HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();
		jsonObject.put("status", LOGIN_STATUS_FAILED);
		jsonObject.put("message", "userNotExist");

		infoObj.setData(jsonObject);
		infoObj.setErrorCode(0);
		infoObj.setDigestMessage("userNotExist");
		infoObj.setOperateSuccess(true);
		Client.writeToClient(response.getWriter(), infoObj);
	}

	private void handlePasswordNotMatch (HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();

		jsonObject.put("status", LOGIN_STATUS_FAILED);
		jsonObject.put("message", "passwordNotMatch");
		infoObj.setData(jsonObject);
		infoObj.setErrorCode(0);
		infoObj.setDigestMessage("passwordNotMatch");
		infoObj.setOperateSuccess(true);

		Client.writeToClient(response.getWriter(), infoObj);
	}

	private void handleLoginSuccess (HttpServletResponse response) throws IOException {

		JSONObject jsonObject = new JSONObject();
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();
		jsonObject.put("status", LOGIN_STATUS_SUCCESS);
		infoObj.setData(jsonObject);
		infoObj.setDigestMessage("loginSuccess");
		infoObj.setOperateSuccess(true);
		infoObj.setErrorCode(0);
		Client.writeToClient(response.getWriter(), infoObj);
	}

	/**
	 * 获取客户端的数据，并将信息封装成服务器实体对象
	 *
	 * @param request 请求
	 * @return 封装后的实体对象
	 * @throws IOException
	 */
	private UserDo getInfo (HttpServletRequest request) throws IOException {

		UserDo user;

		String clientMessage = Client.readFromClient(request.getReader());

		if (clientMessage == null) {
			return null;
		}
		JSONObject object = new JSONObject(clientMessage);

		String tmpName = null;
		String tmpPassword = null;
		int permissionLevel = 1;
		if (object.has("userName")) {
			tmpName = object.getString("userName");
		}
		if (object.has("userPassword")) {
			tmpPassword = object.getString("userPassword");
		}
		if (object.has("permissionLevel")) {
			permissionLevel = object.getInt("permissionLevel");
		}
		if (tmpName == null || tmpPassword == null) {
			return null;
		}
		String name = null;
		try {
			name = KitAESCoder.decrypt(tmpName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user = new UserDo();
		user.setUserName(name);
		if (tmpPassword.length() > PASSWORD_MAX_LENGTH) {
			tmpPassword = tmpPassword.substring(0, PASSWORD_MAX_LENGTH);
		}
		user.setUserPassword(tmpPassword);
		user.setPermissionLevel(permissionLevel);
		return user;
	}

	/**
	 * 检查客户端发送的数据是否合法，能否组成一个服务器实体对象
	 * 如果异常，向客户端返回数据格式异常
	 *
	 * @param clientUser 客户端的实体对象
	 * @param response   回应客户端
	 * @throws IOException
	 */
	private void checkFormat (UserDo clientUser, HttpServletResponse response) throws IOException {

		ClientInfoObj<JSONObject> outInfo = new ClientInfoObj<JSONObject>();
		if (clientUser == null) {
			outInfo.setDigestMessage("clientFormatError");
			outInfo.setErrorCode(1);
			outInfo.setOperateSuccess(false);
			outInfo.setData(null);
			//object.put("clientFormatError");
			Client.writeToClient(response.getWriter(), outInfo);
			return;
		}
	}

	/**
	 * 检查数据库是否操作异常
	 *
	 * @param clientUser 客户端发送的实体对象
	 * @param request    请求
	 * @param response   回应
	 * @return 数据库操作返回值
	 * @throws IOException
	 */
	private ServiceResult<UserDo> checkDbOk (UserDo clientUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

		HashMap<String, String> dbMapParam = new HashMap<String, String>();
		dbMapParam.put("userName", clientUser.getUserName());
		ServiceResult<UserDo> result = userService.queryUserInfo(dbMapParam);

		if (!result.isSuccess()) {
			outToClient(result, request, response);
			return null;
		}
		return result;
	}

	private ServiceResult<AdminDo> checkDbOk (AdminDo clintAdmin, HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServiceResult<AdminDo> result = adminService.queryUser(clintAdmin);

		if (!result.isSuccess()) {
			outToClient(result, request, response);
			return null;
		}
		return result;
	}

	private void outToClient (ServiceResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

		ClientInfoObj<JSONObject> outInfo = new ClientInfoObj<JSONObject>();

		Logger logger = LoggerFactory.getLogger(LoginController.class);
		logger.error("数据库操作失败", new Object[]{request, result});

		//response.sendError(404 + 1);
		outInfo.setDigestMessage("databaseError");
		outInfo.setErrorCode(2);
		outInfo.setOperateSuccess(false);
		outInfo.setData(null);
		Client.writeToClient(response.getWriter(), outInfo);
	}

}