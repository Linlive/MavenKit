package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.service.GoodsService;
import com.tanl.kitserver.util.ServiceResult;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 商品中间服务层
 * Created by Administrator on 2016/5/24.
 */
public class GoodsServiceImpl implements GoodsService {

	@Resource(name = "goodsDao")
	public GoodsDao goodsDao;

	public ServiceResult<Integer> addGoods (GoodsDo goodsDo) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			goodsDao.addGoods(goodsDo);
			result.setData(0);
			result.setSuccess(true);

		} catch (SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		return result;
	}

	public ServiceResult<Integer> findBrand (String brandValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		Integer id;
		try {
			id = goodsDao.findBrand(brandValue);
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findType (String typeValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		Integer id;
		try {
			id = goodsDao.findType(typeValue);
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findSize (String sizeValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		Integer id;
		try {
			id = goodsDao.findSize(sizeValue);
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findColor (String colorValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		Integer id;
		try {
			id = goodsDao.findColor(colorValue);
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
