package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;
import com.tanl.kitserver.service.IndentService;
import com.tanl.kitserver.util.ServerCode;
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

	public static final int TYPE_USER = 0;
	public static final int TYPE_SHOPKEEPER = 1;

	public static final String USER_TYPE_VALUE = "userId";
	public static final String SHOPKEEPER_TYPE_VALUE = "shopkeeperId";

	/**
	 * 生成订单
	 * 状态-测试成功
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
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

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(request.getReader());
		if(!je.isJsonObject()){
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonElement jo = je.getAsJsonObject();
		Gson indentGson = new Gson();
		IndentDo indentDo = indentGson.fromJson(jo, IndentDo.class);

		ServiceResult<Boolean> result = indentService.editIndent(indentDo);
		if(Client.handleError(result, response)){
			return;
		}
		Client.writeOkToClient(response);
	}

	/**
	 * 待扩展
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/removeIndent")
	public void removeIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String indentNumber = getIndentNumber(request, response);
		ServiceResult<Boolean> result = indentService.removeIndent(indentNumber);
		if (Client.handleError(result, response)) {
			return;
		}
		JSONObject outObj = new JSONObject();
		outObj.put("status", true);
		Client.writeToClient(response.getWriter(), outObj);
	}

	/**
	 * 获取客户端发出的订单号
	 * 便于后续的使用
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	private String getIndentNumber (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientInfo = Client.readFromClient(request.getReader());
		if (null == clientInfo || clientInfo.length() == 0) {
			return null;
		}
		JSONObject deleteInfoObj = new JSONObject(clientInfo);
		if (!deleteInfoObj.has("indentNumber")) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return null;
		}
		return deleteInfoObj.getString("indentNumber");
	}

	/**
	 * 彻底删除
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteIndent")
	public void deleteIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

	/**
	 * 普通买家查询订单方法
	 * 状态，测试成功
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/queryUserIndentAll")
	public void userQueryIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (!handleParam(request, response, TYPE_USER)) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
		}
	}

	@RequestMapping(value = "/queryShopkeeperIndentAll")
	public void shopkeeperQueryIndent (HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (!handleParam(request, response, TYPE_SHOPKEEPER)) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
		}
	}

	/**
	 * 分别处理用户和商家的订单查询
	 *
	 * @param request
	 * @param response
	 * @param queryUserType
	 * @return
	 * @throws IOException
	 */
	private boolean handleParam (HttpServletRequest request, HttpServletResponse response, int queryUserType) throws IOException {

		String userIdType;
		switch (queryUserType) {
			case TYPE_USER://user
				userIdType = USER_TYPE_VALUE;
				break;
			case TYPE_SHOPKEEPER:
				userIdType = SHOPKEEPER_TYPE_VALUE;
				break;
			default:
				userIdType = "userId";
				break;
		}

		String clientInfo = Client.readFromClient(request.getReader());
		if (clientInfo == null) {
			return false;
		}
		JSONObject clientObj = new JSONObject(clientInfo);
		if (!clientObj.has(userIdType)) {
			return false;
		}
		String shopkeeperId = clientObj.getString(userIdType);
		ServiceResult<List<IndentViewDo>> result = indentService.queryIndentByUserId(shopkeeperId);
		if (Client.handleError(result, response)) {
			return false;
		}
		String str = createStringByJson(result);
		Client.writeToClient(response.getWriter(), str);
		return true;
	}

	/**
	 * 用户自定义查询，根据状态查询订单信息
	 * <p>
	 * 状态，已完成-待测试
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/userQueryIndentCustom")
	public void userQueryIndentCustom (HttpServletRequest request, HttpServletResponse response) throws IOException {

		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(request.getReader());
		if (!je.isJsonObject()) {
			return;
		}
		JsonObject object = je.getAsJsonObject();
		Gson g = new Gson();
		IndentViewDo indentViewDo = g.fromJson(object, IndentViewDo.class);

		ServiceResult<List<IndentViewDo>> result = indentService.queryIndent(indentViewDo);
		if (Client.handleError(result, response)) {
			return;
		}
		String str = createStringByJson(result);
		Client.writeToClient(response.getWriter(), str);
	}

	@RequestMapping(value = "/shopkeeperQueryIndentCustom")
	public void shopkeeperQueryIndentCustom (HttpServletRequest request, HttpServletResponse response) throws IOException {

		userQueryIndentCustom(request, response);
	}

	private String createStringByJson (ServiceResult<List<IndentViewDo>> result) {

		List<IndentViewDo> daoList = result.getData();
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
