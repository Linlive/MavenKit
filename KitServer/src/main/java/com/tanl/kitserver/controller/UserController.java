package com.tanl.kitserver.controller;

import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.Client;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/4.
 */
@Controller
public class UserController {

	@Resource(name = "userService")
	UserService userService;

	boolean isShopper = false;

	@RequestMapping(value = "/queryUserInfo")
	public void queryUserInfo (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String userId = getClientString(request, response);
		if(null == userId){
			return;
		}
		ServiceResult<UserDo> result = userService.queryUserInfo(userId);
		if (Client.handleError(result, response)) {
			return;
		}
		UserDo retDao = result.getData();
		boolean isShopkeeper = false;
		if ("2".equals(retDao.getShopKeeper())) {
			//isShopper
			isShopkeeper = true;
			isShopper = true;
		}
		JSONObject outObj = new JSONObject();
		outObj.put("status", true);
		outObj.put("isShopkeeper", isShopkeeper);
		Client.writeToClient(response.getWriter(), outObj);

	}

	@RequestMapping(value = "/userApplicationAShop")
	public void userApplicationAShop (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String userId = getClientString(request, response);
		if(null == userId){
			return;
		}
		UserApplication ua = new UserApplication();
		ua.setUserId(userId);
		//1 申请成为商家
		ua.setApplicationType(1);
		ServiceResult<Boolean> result = userService.addApplication(ua);
		if(Client.handleError(result, response)){
			return;
		}
		Client.writeOkToClient(response);
	}
	@RequestMapping(value = "/queryApplicationState")
	public void userQueryApplicationState (HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = getClientString(request, response);
		if(null == userId){
			return;
		}
		UserApplication ua = new UserApplication();
		ua.setUserId(userId);
		ServiceResult<String> result = userService.queryApplication(ua);
		if(Client.handleError(result, response)){
			return;
		}
		JSONObject outObj = new JSONObject();
		outObj.put("status", true);
		outObj.put("applicationState", result.getData());
		Client.writeToClient(response.getWriter(), outObj);
	}

	/**
	 * 获取客户端发出的userId
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String getClientString (HttpServletRequest request, HttpServletResponse response) throws IOException {
		String str = Client.readFromClient(request.getReader());
		if (null == str || str.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return null;
		}
		JSONObject obj = new JSONObject(str);
		String userId;
		if (obj.has("userId")) {
			userId = obj.getString("userId");
		} else {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return null;
		}
		return userId;
	}
}
