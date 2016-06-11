package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.MyPage;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.GoodsImgsService;
import com.tanl.kitserver.service.GoodsService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录成功后，访问所有商家的所有商品，
 * 客户端将从服务器获取的数据，进行分页展示，load加载更多数据
 * Created by Administrator on 2016/5/25.
 */
@Controller
@RequestMapping(value = "/")
public class GoodsIndexController {

	private static final int CODE_SERVER_DATABASE_ERROR = 0;
	private static final int CODE_SERVER_SERVER_ERROR = 1;
	private static final int CODE_SERVER_DATA_EMPTY = 1;

	@Resource(name = "goodsService")
	GoodsService goodsService;

	@Resource(name = "goodsImgsService")
	GoodsImgsService goodsImgsService;

	/**
	 * 获取所有的商品
	 */
	@RequestMapping(value = "/getGoods")
	public void getAllGoodsDefault (HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServiceResult<List<GoodsDo>> daoResult = goodsService.queryFirstGoods();

		if (daoResult == null) {
			JSONObject obj = new JSONObject();
			obj.put("data", "NULL");
			Client.writeToClient(response.getWriter(), obj);
			return;
		}
		if (!daoResult.isSuccess()) {
			JSONObject obj = new JSONObject();
			obj.put("data", "NULL");
			obj.put("code", CODE_SERVER_DATABASE_ERROR);
			Client.writeToClient(response.getWriter(), obj);
			return;
		}
		sendDataPage(daoResult, response);
	}

	@RequestMapping(value = "/loadMoreGoods")
	public void loadGoods (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String serString = Client.readFromClient(request.getReader());
		if (null == serString || serString.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(serString);
		MyPage page = null;
		if (je.isJsonObject()) {
			JsonObject ob = je.getAsJsonObject();
			Gson gson = new Gson();
			page = gson.fromJson(ob, MyPage.class);
		}
		if (null == page) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}

		ServiceResult<List<GoodsDo>> daoResult;
		daoResult = goodsService.queryLoadMoreGoods(page.getPageNo(), page.getPageSize());
		if (Client.handleError(daoResult, response)) {
			return;
		}
		sendDataPage(daoResult, response);
	}

	/**
	 * 商家查看
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/getGoodsByShopkeeper")
	public void getMyGoods (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientStr = Client.readFromClient(request.getReader());
		if (null == clientStr || clientStr.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(clientStr);
		if (!je.isJsonObject()) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonObject clientObj = je.getAsJsonObject();
		UserDo user = null;
		MyPage page = null;
		Gson g;
		if (clientObj.has("user")) {
			JsonElement jo = clientObj.get("user");//.getAsJsonObject("user");
			g = new Gson();
			user = g.fromJson(jo, UserDo.class);
		}
		if (null == user) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		if (clientObj.has("page")) {
			JsonObject jo = clientObj.getAsJsonObject("page");
			g = new Gson();
			page = g.fromJson(jo, MyPage.class);
		}
		if (null == page) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		//所有格式均正确，开始进行数据库的查询操作
		ServiceResult<List<GoodsDo>> goodsDoResult = goodsService.queryShopkeeperGoods(user, page);
		if (Client.handleError(goodsDoResult, response)) {
			response.sendError(ServerCode.DATABASE_OUT_NULL);
			return;
		}
		sendDataPage(goodsDoResult, response);
	}

	@RequestMapping(value = "/getGoodsBySpecialKey")
	public void getSpecialGoods (HttpServletRequest request, HttpServletResponse response) throws IOException {
		String clientStr = Client.readFromClient(request.getReader());
		if (null == clientStr || clientStr.length() == 0) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(clientStr);
		if (!je.isJsonObject()) {
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JsonObject clientObj = je.getAsJsonObject();
		String key = null;
		MyPage page = null;
		if(clientObj.has("key")){
			key = clientObj.get("key").getAsString();
		}
		if(clientObj.has("page")){
			Gson g = new Gson();
			page = g.fromJson(clientObj.get("page").getAsJsonObject(), MyPage.class);
		}
		if(null == key || null == page){
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		ServiceResult<List<GoodsDo>> goodsDoResult = goodsService.querySpecialGoods(key, page);
		if (Client.handleError(goodsDoResult, response)) {
			response.sendError(ServerCode.DATABASE_OUT_NULL);
			return;
		}
		sendDataPage(goodsDoResult, response);
	}

	/**
	 * 将查询到的商品数据发送到客户端
	 *
	 * @param goodsList
	 * @param response
	 * @throws IOException
	 */
	private void sendDatas (List<GoodsDo> goodsList, HttpServletResponse response) throws IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("{data:[");
		int len = goodsList.size();
		int i = 0;
		for (GoodsDo tmpDo : goodsList) {
			++i;
			Gson g = new Gson();
			sb.append(g.toJson(tmpDo));
			if (i == len) {
				continue;
			}
			sb.append(",");
			;//避免警告
		}
		sb.append("]}");
		Client.writeToClient(response.getWriter(), sb.toString());
	}

	private void sendDataPage (ServiceResult<List<GoodsDo>> daoResult, HttpServletResponse response) throws IOException {

		if (Client.handleError(daoResult, response)) {
			return;
		}
		StringBuilder sb = new StringBuilder();

		List<GoodsDo> goodsList = daoResult.getData();
		MyPage page = new MyPage();
		page.setPageNo(daoResult.getPageNo());
		page.setPageSize(daoResult.getPageSize());
		Gson g = new Gson();
		String pageString = g.toJson(page);
		sb.append("{page:");
		sb.append(pageString);
		sb.append(",data:[");
		int len = goodsList.size();
		int i = 0;
		for (GoodsDo tmpDo : goodsList) {
			++i;
			g = new Gson();
			sb.append(g.toJson(tmpDo));
			if (i == len) {
				continue;
			}
			sb.append(",");
			;//避免警告
		}
		sb.append("]}");
		Client.writeToClient(response.getWriter(), sb.toString());
	}

	/**
	 * 通过用户推荐排序，考虑是否放在客户端执行
	 */
	@RequestMapping(value = "getGoodsByRecommend")
	public void getAllGoodsByRecommend () {

		List<GoodsDo> goodsList = new ArrayList<GoodsDo>();
		ServiceResult<List<GoodsDo>> daoResult = goodsService.queryFirstGoods();

	}

}