package com.tanl.kitserver.controller;

import com.tanl.kitserver.model.bean.UserDO;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.Client;
import com.tanl.kitserver.util.common.ClientInfoObj;
import com.tanl.kitserver.util.encryption.KitAESCoder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

	final boolean login_status_success = true;
	final boolean login_status_failed = false;
	String login_status_failed_info = "PASSWORD";

	UserService userService;


	static int i = 1;

	@RequestMapping(value = "/login")
	public void login (HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject;
		UserDO clientUser = getInfo(request);
		if (clientUser == null) return;

		checkFormat(clientUser, response);
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();
		ServiceResult<UserDO> result = checkDbOk(clientUser, request, response);

		if (result == null) return;

		jsonObject = new JSONObject();
		UserDO userDB = result.getData();
		boolean notFound = userDB == null;

		if (notFound) {
			jsonObject.put("status", login_status_failed);
			jsonObject.put("message", "userNotExist");

			infoObj.setData(jsonObject);
			infoObj.setErrorCode(0);
			infoObj.setDigestMessage("userNotExist");
			infoObj.setOperateSuccess(true);
			Client.writeToClient(response.getWriter(), infoObj);
			return;
		}
		String userRequestPassword = clientUser.getUserPassword();

		if (!userRequestPassword.equals(userDB.getUserPassword())) {//密码不匹配
			jsonObject.put("status", login_status_failed);
			jsonObject.put("message", "passwordNotMatch");
			infoObj.setData(jsonObject);
			infoObj.setErrorCode(0);
			infoObj.setDigestMessage("passwordNotMatch");
			infoObj.setOperateSuccess(true);

			Client.writeToClient(response.getWriter(), infoObj);
			return;
		}

		jsonObject.put("status", login_status_success);

		infoObj.setData(jsonObject);
		infoObj.setDigestMessage("loginSuccess");
		infoObj.setOperateSuccess(true);
		infoObj.setErrorCode(0);
		Client.writeToClient(response.getWriter(), infoObj);
		System.out.println("login request success !");
	}

	@RequestMapping(value = "/register")
	public void register (HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONObject jsonObject;
		UserDO clientUser = getInfo(request);
		if (clientUser == null) return;

		checkFormat(clientUser, response);
		ClientInfoObj<JSONObject> infoObj = new ClientInfoObj<JSONObject>();
		ServiceResult<UserDO> result = checkDbOk(clientUser, request, response);

		if (result == null) return;

		jsonObject = new JSONObject();
		UserDO userDB = result.getData();
		boolean exist = (userDB != null);
		if (exist) {
			jsonObject.put("status", login_status_failed);
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


	/**
	 * 获取客户端的数据，并将信息封装成服务器实体对象
	 *
	 * @param request 请求
	 * @return 封装后的实体对象
	 * @throws IOException
	 */
	private UserDO getInfo (HttpServletRequest request) throws IOException {

		UserDO user;
		userService = (UserService) context.getBean("userService");
		String clientMessage = Client.readFromClient(request.getReader());

		if (clientMessage == null) {
			return null;
		}
		JSONObject object = new JSONObject(clientMessage);

		String tmpName = null;
		String tmpPassword = null;
		if (object.has("userName")) {
			tmpName = object.getString("userName");
		}
		if (object.has("userPassword")) {
			tmpPassword = object.getString("userPassword");
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
		user = new UserDO();
		user.setUserName(name);
		user.setUserPassword(tmpPassword);
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
	private void checkFormat (UserDO clientUser, HttpServletResponse response) throws IOException {

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
	private ServiceResult<UserDO> checkDbOk (UserDO clientUser, HttpServletRequest request, HttpServletResponse response) throws IOException {

		ClientInfoObj<JSONObject> outInfo = new ClientInfoObj<JSONObject>();
		HashMap<String, String> dbMapParam = new HashMap<String, String>();
		dbMapParam.put("userName", clientUser.getUserName());
		ServiceResult<UserDO> result = userService.queryUserInfo(dbMapParam);

		if (!result.isSuccess()) {
			Logger logger = LoggerFactory.getLogger(LoginController.class);
			logger.error("数据库操作失败", new Object[]{request, result});

			//response.sendError(404 + 1);
			outInfo.setDigestMessage("databaseError");
			outInfo.setErrorCode(2);
			outInfo.setOperateSuccess(false);
			outInfo.setData(null);
			Client.writeToClient(response.getWriter(), outInfo);
			return null;
		}
		return result;
	}
}