package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.ShoppingCartDao;
import com.tanl.kitserver.model.bean.ShoppingCartDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/5/28.
 */
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	public boolean addToShoppingCart (ShoppingCartDo cartDo) throws SQLException {
		Object obj = queryCart(cartDo.getGoodsId());
		if(null == obj){
			sqlMapClient.insert("addToCart", cartDo);
		} else {
			Integer num = (Integer) obj + 1;
			sqlMapClient.update("updateCart", num);
		}
		return true;
	}

	public boolean deleteFromShoppingCart (ShoppingCartDo cartDo) throws SQLException {

		sqlMapClient.delete("deleteFromCart", cartDo);
		return true;
	}

	public List<ShoppingCartDo> queryShoppingCart (String userId) throws SQLException{

		List<ShoppingCartDo> cartDos = new ArrayList<ShoppingCartDo>();

		List ret = sqlMapClient.queryForList("queryCart", userId);
		Iterator<ShoppingCartDo> iterator = ret.iterator();
		while (iterator.hasNext()){
			cartDos.add(iterator.next());
		}
		return cartDos;
	}

	private Object queryCart(String goodsId) throws SQLException {
		return sqlMapClient.queryForObject("queryChooseNumber", goodsId);
	}
}
