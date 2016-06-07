package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.AdminService;
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
import java.util.List;

/**
 * Created by Administrator on 2016/6/5.
 */
@Controller
@RequestMapping(value = "/")
public class ApplicationController {

	@Resource(name = "userService")
	UserService userService;
	@Resource(name = "adminService")
	AdminService adminService;

	@RequestMapping(value = "/queryApplication")
	public void findApplication (HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServiceResult<List<UserApplication>> result = adminService.queryApplication();
		if (Client.handleError(result, response)) {
			return;
		}
		List<UserApplication> applications = result.getData();
		StringBuffer sb = new StringBuffer();
		Gson g = new Gson();
		sb.append("{");
		sb.append("status:").append(true).append(",");
		sb.append("data:[");
		for (UserApplication ua : applications) {
			sb.append(g.toJson(ua));
		}
		sb.append("]}");
		Client.writeToClient(response.getWriter(), sb.toString());
	}

	@RequestMapping(value = "/handleApplication")
	public void handleApplication (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientStr = Client.readFromClient(request.getReader());
		if (null == clientStr || clientStr.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JSONObject jo = new JSONObject(clientStr);

		int applicationId;
		if (!jo.has("applicationId")) {
			return;
		}
		if (!jo.has("isAccept")) {
			return;
		}
		applicationId = jo.getInt("applicationId");
		ServiceResult<Boolean> result = adminService.handleApplication(applicationId, jo.getBoolean("isAccept"));
		if (Client.handleError(result, response)) {
			return;
		}
		if (result.getData()) {
			Client.writeOkToClient(response.getWriter());
		}
	}

	@RequestMapping(value = "/queryAllUser")
	public void queryAllUser (HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServiceResult<List<UserDo>> result = adminService.queryAllUser();
		if (Client.handleError(result, response)) {
			return;
		}
		List<UserDo> applications = result.getData();
		StringBuffer sb = new StringBuffer();
		int i = 0;
		Gson g = new Gson();
		sb.append("{");
		sb.append("data:[");

		for (UserDo ua : applications) {
			i++;
			sb.append(g.toJson(ua));
			if(i == applications.size()){
				continue;
			}
			sb.append(",");
		}
		sb.append("]}");
		Client.writeToClient(response.getWriter(), sb.toString());

	}

	@RequestMapping(value = "/deleteUser")
	public void deleteUser (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String userId = getUserId(request, response);
		ServiceResult<Boolean> result = adminService.deleteUser(userId);
		if (Client.handleError(result, response)) {
			return;
		}
		if (result.getData()) {
			Client.writeOkToClient(response);
		}
	}

	@RequestMapping(value = "/rejectUserApplication")
	public void rejectUserApplication (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientStr = Client.readFromClient(request.getReader());
		if (null == clientStr || clientStr.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JSONObject object = new JSONObject(clientStr);
		String userId;
		if (!object.has("userId")) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		userId = object.getString("userId");
		ServiceResult<Boolean> result = null;// = adminService.handleApplication(userId);
		if (Client.handleError(result, response)) {
			return;
		}
		if (result.getData()) {
			Client.writeOkToClient(response);
		}
	}

	private String getUserId (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientStr = Client.readFromClient(request.getReader());
		if (null == clientStr || clientStr.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return null;
		}
		JSONObject object = new JSONObject(clientStr);
		String userId;
		if (!object.has("userId")) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return null;
		}
		userId = object.getString("userId");
		return userId;
	}
}
