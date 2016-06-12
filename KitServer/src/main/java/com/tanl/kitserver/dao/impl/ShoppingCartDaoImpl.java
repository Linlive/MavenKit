package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.ShoppingCartDao;
import com.tanl.kitserver.model.bean.ShoppingCartDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	public boolean addToShoppingCart (ShoppingCartDo cartDo) throws SQLException {

		Object obj = queryCart(cartDo.getGoodsId(), cartDo.getUserId());
		if (null == obj) {
			sqlMapClient.insert("addToCart", cartDo);
		} else {
			Integer num = (Integer) obj + 1;
			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("goodsId", cartDo.getGoodsId());
			paramMap.put("userId", cartDo.getUserId());
			paramMap.put("num", num);
			sqlMapClient.update("updateCart", num);
		}
		return true;
	}

	public boolean deleteFromShoppingCart (String goodsId, String userId) throws SQLException {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("goodsId", goodsId);
		map.put("userId", userId);
		sqlMapClient.delete("deleteFromCart", map);
		return true;
	}

	public List<ShoppingCartDo> queryShoppingCart (String userId) throws SQLException {

		List<ShoppingCartDo> cartDos = new ArrayList<ShoppingCartDo>();

		List ret = sqlMapClient.queryForList("queryCart", userId);

		for (Object aRet : ret) {
			ShoppingCartDo cartDo = (ShoppingCartDo) aRet;
			queryTypeSoOn(cartDo);
			cartDos.add(cartDo);
		}
		return cartDos;
	}

	public boolean addOrSub (String userId, String goodsId, boolean add) throws SQLException {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		paramMap.put("goodsId", goodsId);
		int line;
		if (add) {
			line = sqlMapClient.update("addChooseNumber", paramMap);
		} else {
			line = sqlMapClient.update("subChooseNumber", paramMap);
		}
		if (line >= 1) {
			return true;
		}
		return false;
	}

	private void queryTypeSoOn (ShoppingCartDo cartDo) throws SQLException {

		Object typeValue = sqlMapClient.queryForObject("queryTypeValue", cartDo.getGoodsType());
		Object sizeValue = sqlMapClient.queryForObject("querySizeValue", cartDo.getGoodsSize());
		Object colorValue = sqlMapClient.queryForObject("queryColorValue", cartDo.getGoodsColor());
		Object extraValue = sqlMapClient.queryForObject("queryExtra", cartDo.getGoodsId());
		Object goodsName = sqlMapClient.queryForObject("getGoodsName", cartDo.getGoodsId());

		cartDo.setGoodsName((String) goodsName);
		cartDo.setGoodsTypeValue((String) typeValue);
		cartDo.setGoodsSizeValue((String) sizeValue);
		cartDo.setGoodsColorValue((String) colorValue);
		cartDo.setGoodsExtra((String) extraValue);
	}

	private Object queryCart (String goodsId, String userId) throws SQLException {

		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("goodsId", goodsId);
		paramMap.put("userId", userId);
		return sqlMapClient.queryForObject("queryChooseNumber", paramMap);
	}
}
