package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.MyPage;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsService {

	ServiceResult<Integer> addGoods(GoodsDo goodsDo);
	ServiceResult<List<GoodsDo>> queryFirstGoods();
	ServiceResult<List<GoodsDo>> queryFreshGoods();
	ServiceResult<List<GoodsDo>> queryLoadMoreGoods (int start, int size);


	ServiceResult<List<GoodsDo>> queryShopkeeperGoods (UserDo user, MyPage page);
	ServiceResult<List<GoodsDo>> querySpecialGoods (String key, MyPage page);


	ServiceResult<Integer> findBrand(String brandValue);
	ServiceResult<Integer> findType(String typeValue);
	ServiceResult<Integer> findSize(String sizeValue);
	ServiceResult<Integer> findColor(String colorValue);

}
