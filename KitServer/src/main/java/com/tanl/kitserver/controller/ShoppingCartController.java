package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tanl.kitserver.model.bean.CartDo;
import com.tanl.kitserver.model.bean.ShoppingCartDo;
import com.tanl.kitserver.service.ShoppingCartService;
import com.tanl.kitserver.util.ServerCode;
import com.tanl.kitserver.util.ServiceResult;
import com.tanl.kitserver.util.common.Client;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * 购物车控制类
 * <p>
 * 处理添加至购物车和从购物车中删除商品
 * 以及请求购物车里面的所有商品细节
 * <p>
 * Created by Administrator on 2016/5/28.
 */
@Controller
@RequestMapping(value = "/")
public class ShoppingCartController {

	@Resource(name = "shoppingCartService")
	ShoppingCartService cartService;


	@RequestMapping(value = "/addToShoppingCart")
	public void addToShoppingCart (HttpServletRequest request, HttpServletResponse response) throws IOException {

		ShoppingCartDo shoppingCartDo;
		shoppingCartDo = getClientInfo(request);
		ServiceResult<Boolean> result = cartService.addToShoppingCart(shoppingCartDo);
		if (Client.handleError(result, response)) return;

		JsonObject out = new JsonObject();
		out.addProperty("status", true);
		out.addProperty("code", ServerCode.SERVER_REQUEST_OK);
		Client.writeToClient(response.getWriter(), out);
	}

	@RequestMapping(value = "/deleteFromShoppingCart")
	public void deleteFromShoppingCart (HttpServletRequest request, HttpServletResponse response) throws IOException {

		String clientInfo = Client.readFromClient(new BufferedReader(request.getReader()));
		JSONObject object = new JSONObject(clientInfo);
		if(!object.has("goodsId") || !object.has("userId")){
			return;
		}
		String goodsId = object.getString("goodsId");
		String userId = object.getString("userId");
		ServiceResult<Boolean> result = cartService.deleteFromShoppingCart(goodsId, userId);
		if (Client.handleError(result, response)) return;

		JSONObject objOut = new JSONObject();
		objOut.put("status", result.isSuccess());
		Client.writeToClient(response.getWriter(), objOut);
	}

	@RequestMapping(value = "/queryShoppingCart")
	public void queryShoppingCart (HttpServletRequest request, HttpServletResponse response) throws IOException {


		String clientStr = Client.readFromClient(new BufferedReader(request.getReader()));
		if(clientStr == null){
			response.sendError(ServerCode.SERVER_GET_USER_INFO_NULL);
			return;
		}
		String userId;
		JSONObject inObj = new JSONObject(clientStr);
		if(!inObj.has("userId")){
			response.sendError(ServerCode.SERVER_GET_USER_INFO_NULL);
		}
		userId = inObj.getString("userId");
		ServiceResult<List<ShoppingCartDo>> result;
		result = cartService.queryShoppingCart(userId);
		if (Client.handleError(result, response)) return;

		Gson cartGson = new Gson();
		List<ShoppingCartDo> cartDos = result.getData();
		Client.writeToClient(response.getWriter(), cartGson.toJson(cartDos));
	}

	@RequestMapping(value = "/addOrSubCart")
	public void addOrSubCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String clientStr = Client.readFromClient(request.getReader());
		if(null == clientStr || clientStr.length() == 0){
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		JSONObject clientObj = new JSONObject(clientStr);
		if(!clientObj.has("userId") || !clientObj.has("goodsId") || !clientObj.has("add")){
			response.sendError(ServerCode.DATA_FORMAT_ERROR);
			return;
		}
		String userId = clientObj.getString("userId");
		String goodsId = clientObj.getString("goodsId");
		boolean add = clientObj.getBoolean("add");
		ServiceResult<Boolean> result = cartService.addOrSub(userId, goodsId, add);
		if(Client.handleError(result, response)){
			return;
		}
		JSONObject object = new JSONObject();
		object.put("status", result.getData());
		Client.writeToClient(response.getWriter(), object);
	}

	private ShoppingCartDo getClientInfo(HttpServletRequest request) throws IOException {
		ShoppingCartDo shoppingCartDo = new ShoppingCartDo();
		BufferedReader bf = new BufferedReader(request.getReader());
		String client = Client.readFromClient(bf);

		System.out.println(client);

		JsonParser jp = new JsonParser();
		if (client == null) {
			return null;
		}
		JsonElement je = jp.parse(client);
		if (!je.isJsonObject()) {
			return null;
		}
		JsonObject object = je.getAsJsonObject();
		Gson gson = new Gson();
		CartDo cartDo = gson.fromJson(object, CartDo.class);
		shoppingCartDo = changeToShoppingCart(cartDo);
		return shoppingCartDo;
	}

	private ShoppingCartDo changeToShoppingCart (CartDo cartDo) {

		ShoppingCartDo shoppingCartDo = new ShoppingCartDo();

		shoppingCartDo.setUserId(cartDo.getUserId());
		shoppingCartDo.setGoodsId(cartDo.getGoodsDo().getGoodsId());
		shoppingCartDo.setGoodsChooseNumber(cartDo.getGoodsCount());
		shoppingCartDo.setGoodsColor(cartDo.getGoodsDo().getGoodsColor());
		shoppingCartDo.setGoodsPrice(cartDo.getGoodsDo().getGoodsPrice());
		shoppingCartDo.setGoodsSize(cartDo.getGoodsDo().getGoodsSize());
		shoppingCartDo.setGoodsType(cartDo.getGoodsDo().getGoodsType());
		shoppingCartDo.setShopKeeperId(cartDo.getGoodsDo().getShopKeeperId());
		shoppingCartDo.setGoodsExtra(cartDo.getGoodsDo().getGoodsExtras());

		shoppingCartDo.setGoodsColorValue(cartDo.getGoodsDo().getGoodsColorValue());
		shoppingCartDo.setGoodsTypeValue(cartDo.getGoodsDo().getGoodsTypeValue());
		shoppingCartDo.setGoodsSizeValue(cartDo.getGoodsDo().getGoodsSizeValue());

		return shoppingCartDo;
	}

}
