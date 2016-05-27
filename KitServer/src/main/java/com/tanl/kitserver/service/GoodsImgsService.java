package com.tanl.kitserver.service;

import com.tanl.kitserver.util.ServiceResult;

import java.util.HashMap;
import java.util.List;

/**
 * 商品图片处理
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsImgsService {

	//将商品图片的路径存储到数据库中
	ServiceResult addGoodsImgsToDB (String goodsId, List<String> paths);
	//将单个的商品图片添加到数据库
	ServiceResult addGoodsSingleImgToDB (String goodsId, String path);

	ServiceResult<List<HashMap<String,String>>> queryAllGoods();

}
