package com.tanl.kitserver.service;

import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsImgsService {
	ServiceResult addGoodsImgsToDB (String goodsId, List<String> paths);
	ServiceResult addGoodsSingleImgToDB (String goodsId, String path);
}
