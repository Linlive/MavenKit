package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.GoodsImgPathDao;
import com.tanl.kitserver.service.GoodsImgsService;
import com.tanl.kitserver.util.ServiceResult;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * 商品的路径管理
 * Created by Administrator on 2016/5/24.
 */
public class GoodsImgsServiceImpl implements GoodsImgsService {

	@Resource(name = "goodsImgPathDao")
	GoodsImgPathDao goodsImgPathDao;

	public ServiceResult<Integer> addGoodsImgsToDB (String goodsId, List<String> paths) {

		ServiceResult<Integer> dbResult = new ServiceResult<Integer>();
		try {
			goodsImgPathDao.insert(goodsId, paths);
			dbResult.setSuccess(true);
			dbResult.setData(paths.size());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dbResult;
	}

	public ServiceResult<Integer> addGoodsSingleImgToDB (String goodsId, String path) {
		ServiceResult<Integer> dbResult = new ServiceResult<Integer>();
		try {
			goodsImgPathDao.insert(goodsId, path);
			dbResult.setSuccess(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dbResult;
	}

	public ServiceResult<List<HashMap<String, String>>> queryAllGoods () {

		return null;
	}
}
