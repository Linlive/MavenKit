package com.tanl.kitserver.controller;

import com.google.gson.Gson;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.service.GoodsImgsService;
import com.tanl.kitserver.service.GoodsService;
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
	public void getAllGoodsDefault(HttpServletRequest request, HttpServletResponse response) throws IOException {

		List<GoodsDo> goodsList = new ArrayList<GoodsDo>();
		ServiceResult<List<GoodsDo>> daoResult = goodsService.queryFirstGoods();

		if(daoResult == null){
			JSONObject obj = new JSONObject();
			obj.put("data", "NULL");
			Client.writeToClient(response.getWriter(), obj);
			return;
		}
		if(!daoResult.isSuccess()){
			JSONObject obj = new JSONObject();
			obj.put("data", "NULL");
			obj.put("code", CODE_SERVER_DATABASE_ERROR);
			Client.writeToClient(response.getWriter(), obj);
			return;
		}
		StringBuilder sb = new StringBuilder();
		goodsList = daoResult.getData();
		sb.append("{data:[");
		int len = goodsList.size();
		int i = 0;
		for (GoodsDo tmpDo : goodsList){
			++i;
			Gson g = new Gson();
			sb.append(g.toJson(tmpDo));
			if(i == len){
				continue;
			}
			sb.append(",");;//避免警告
		}
		sb.append("]}");
		Client.writeToClient(response.getWriter(), sb.toString());

	}

	/**
	 * 通过用户推荐排序，考虑是否放在客户端执行
	 */
	@RequestMapping(value = "getGoodsByRecommend")
	public void getAllGoodsByRecommend() {

	}

}