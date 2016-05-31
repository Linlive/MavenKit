package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;
import com.tanl.kitserver.service.IndentService;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.Client;
import com.tanl.kitserver.util.common.GenerateRandomString;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 订单生成以及管理
 * Created by Administrator on 2016/5/31.
 */
@Controller
@RequestMapping(value = "/")
public class IndentController {

	@Resource(name = "indentService")
	IndentService indentService;

	@RequestMapping(value = "/createIndent")
	public void createIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clienInfo = Client.readFromClient(request.getReader());
		JsonParser jsonParser = new JsonParser();
		if (clienInfo == null) {
			return;
		}
		JsonElement je = jsonParser.parse(clienInfo);
		IndentDo indentDo = null;
		if (je.isJsonObject()) {
			//JsonObject indentObj = je.getAsJsonObject();
			Gson g = new Gson();
			indentDo = g.fromJson(je, IndentDo.class);
		}
		if (indentDo == null) {
			return;
		}
		//随机生成订单号
		indentDo.setIndentNumber(GenerateRandomString.generateString());

		ServiceResult<Boolean> result = indentService.createIndent(indentDo);
		if (Client.handleError(result, response)) {
			return;
		}
		JSONObject serverResult = new JSONObject();
		serverResult.put("status", result.isSuccess());
		Client.writeToClient(response.getWriter(), serverResult);
	}

	@RequestMapping(value = "/editIndent")
	public void editIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	@RequestMapping(value = "/removeIndent")
	public void removeIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	@RequestMapping(value = "/deleteIndent")
	public void deleteIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	@RequestMapping(value = "/viewIndent")
	public void viewIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientInfo = Client.readFromClient(request.getReader());

		if (clientInfo == null) {
			return;
		}
		JSONObject clientObj = new JSONObject(clientInfo);
		if (!clientObj.has("userId")) {
			return;
		}
		String userId = clientObj.getString("userId");
		ServiceResult<IndentDo> result = indentService.viewIndentByUserId(userId);
		if (Client.handleError(result, response)) {
			return;
		}
		IndentDo indentDo = result.getData();
		Gson gson = new Gson();
		String serverOut = gson.toJson(indentDo);

		Client.writeToClient(response.getWriter(), serverOut);
	}

	@RequestMapping(value = "/viewIndent")
	public void viewIndent (HttpServletRequest request, HttpServletResponse response, boolean f) throws IOException {

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(request.getReader());
		if (!je.isJsonObject()) {
			return;
		}
		JsonObject object = je.getAsJsonObject();
		Gson g = new Gson();
		IndentViewDo indentViewDo = g.fromJson(object, IndentViewDo.class);

		ServiceResult<List<IndentViewDo>> result = indentService.viewIndent(indentViewDo);
		if (Client.handleError(result, response)) {
			return;
		}
		List<IndentViewDo> clientNeedList = result.getData();
//		StringBuilder sb = new StringBuilder();
//		sb.append("{data:[");
//		int len = clientNeedList.size();
//		int i = 0;
//		for (IndentViewDo tmpDo : clientNeedList){
//			++i;
//			Gson g = new Gson();
//			sb.append(g.toJson(tmpDo));
//			if(i == len){
//				continue;
//			}
//			sb.append(",");
//		}
//		sb.append("]}");

		String str = createStringByJson(clientNeedList);
		Client.writeToClient(response.getWriter(), str);
	}

	private String createStringByJson (List<IndentViewDo> daoList) {

		StringBuilder sb = new StringBuilder();
		sb.append("{data:[");
		int len = daoList.size();
		int i = 0;
		for (IndentViewDo tmpDo : daoList) {
			++i;
			Gson g = new Gson();
			sb.append(g.toJson(tmpDo));
			if (i == len) {
				continue;
			}
			sb.append(",");
		}
		sb.append("]}");
		return sb.toString();
	}
}
