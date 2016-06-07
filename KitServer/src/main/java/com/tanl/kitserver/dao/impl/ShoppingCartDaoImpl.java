package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.ShoppingCartDao;
import com.tanl.kitserver.model.bean.ShoppingCartDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	public boolean addToShoppingCart (ShoppingCartDo cartDo) throws SQLException {

		Object obj = queryCart(cartDo.getGoodsId());
		if (null == obj) {
			sqlMapClient.insert("addToCart", cartDo);
		} else {
			Integer num = (Integer) obj + 1;
			sqlMapClient.update("updateCart", num);
		}
		return true;
	}

	public boolean deleteFromShoppingCart (String goodsId) throws SQLException {

		sqlMapClient.delete("deleteFromCart", goodsId);
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

	private Object queryCart (String goodsId) throws SQLException {

		return sqlMapClient.queryForObject("queryChooseNumber", goodsId);
	}
}
