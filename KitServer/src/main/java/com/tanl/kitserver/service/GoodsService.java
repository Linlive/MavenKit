package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsService {

	ServiceResult<Integer> addGoods(GoodsDo goodsDo);
	ServiceResult<List<GoodsDo>> queryFirstGoods();


	ServiceResult<Integer> findBrand(String brandValue);
	ServiceResult<Integer> findType(String typeValue);
	ServiceResult<Integer> findSize(String sizeValue);
	ServiceResult<Integer> findColor(String colorValue);

}
