package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.ShoppingCartDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * 购物车操作--服务层
 * Created by Administrator on 2016/5/28.
 */
public interface ShoppingCartService {

	ServiceResult<Boolean> addToShoppingCart (ShoppingCartDo shoppingCartDo);

	ServiceResult<Boolean> deleteFromShoppingCart (String goodsId, String userId);

	ServiceResult<List<ShoppingCartDo>> queryShoppingCart (String userId);

	ServiceResult<Boolean> addOrSub(String userId, String goodsId, boolean add);
}
