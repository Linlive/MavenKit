package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.ShoppingCartDo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/28.
 */
public interface ShoppingCartDao {
	boolean addToShoppingCart(ShoppingCartDo cartDo) throws SQLException;
	boolean deleteFromShoppingCart(ShoppingCartDo cartDo) throws SQLException;
	List<ShoppingCartDo> queryShoppingCart(String userId) throws SQLException;
}
