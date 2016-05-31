package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.ShoppingCartDao;
import com.tanl.kitserver.model.bean.ShoppingCartDo;
import com.tanl.kitserver.service.ShoppingCartService;
import com.tanl.kitserver.util.ServiceResult;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 购物车服务层处理细节
 * Created by Administrator on 2016/5/28.
 */
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Resource(name = "shoppingCartDao")
	ShoppingCartDao shoppingCartDao;

	public ServiceResult<Boolean> addToShoppingCart (ShoppingCartDo shoppingCartDo) {

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			boolean success = shoppingCartDao.addToShoppingCart(shoppingCartDo);
			result.setSuccess(true);
			result.setData(success);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ServiceResult<Boolean> deleteFromShoppingCart (String goodsId) {

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			boolean success = shoppingCartDao.deleteFromShoppingCart(goodsId);
			result.setSuccess(true);
			result.setData(success);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ServiceResult<List<ShoppingCartDo>> queryShoppingCart (String userId) {
		ServiceResult<List<ShoppingCartDo>> result = new ServiceResult<List<ShoppingCartDo>>();
		try {
			List<ShoppingCartDo> data = shoppingCartDao.queryShoppingCart(userId);
			result.setSuccess(true);
			result.setData(data);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
