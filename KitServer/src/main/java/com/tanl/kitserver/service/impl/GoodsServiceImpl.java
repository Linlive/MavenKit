package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.MyPage;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.GoodsService;
import com.tanl.kitserver.util.ServiceResult;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

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

	public ServiceResult<List<GoodsDo>> queryFirstGoods () {

		ServiceResult<List<GoodsDo>> result = new ServiceResult<List<GoodsDo>>();
		List<GoodsDo> goodsList;
		int total;
		try {
			goodsList = goodsDao.findGoodsFirst();
			total = goodsDao.getGoodsCount();
			result.setTotal(total);
			result.setPageNo(0);
			/**
			 * 默认每次给出两条数据
			 */
			result.setPageSize(5);
			result.setSuccess(true);
			result.setData(goodsList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<List<GoodsDo>> queryFreshGoods () {

		return null;
	}

	public ServiceResult<List<GoodsDo>> queryLoadMoreGoods (int start, int size) {
		ServiceResult<List<GoodsDo>> result = new ServiceResult<List<GoodsDo>>();
		List<GoodsDo> goodsDoList;
		int total = 0;
		try {
			goodsDoList = goodsDao.queryLoadMoreGoods(start, size);
			total = goodsDao.getGoodsCount();
			result.setPageNo(start);
			result.setPageSize(size);
			result.setTotal(total);
			result.setSuccess(true);
			result.setData(goodsDoList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 商家查询商品，根据商家的Id查询
	 *
	 * @param user
	 * @param page
	 * @return
	 */
	public ServiceResult<List<GoodsDo>> queryShopkeeperGoods (UserDo user, MyPage page) {

		ServiceResult<List<GoodsDo>> result = new ServiceResult<List<GoodsDo>>();
		List<GoodsDo> data;
		try {
			data = goodsDao.queryShopkeeperGoods(user, page);
			result.setSuccess(true);
			result.setData(data);
			result.setPageNo(page.getPageNo());
			result.setPageSize(page.getPageSize());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 搜索用户指定的关键字商品
	 * @param key
	 * @param page
	 * @return
	 */
	public ServiceResult<List<GoodsDo>> querySpecialGoods (String key, MyPage page) {

		ServiceResult<List<GoodsDo>> result = new ServiceResult<List<GoodsDo>>();
		List<GoodsDo> data;

		try {
			data = goodsDao.querySpecialGoods(key, page);
			result.setSuccess(true);
			result.setData(data);
			result.setPageNo(page.getPageNo());
			result.setPageSize(page.getPageSize());

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ServiceResult<Integer> findBrand (String brandValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		if(null == brandValue || brandValue.length() == 0){
			result.setSuccess(false);
			return result;
		}
		Integer id;
		try {
			id = goodsDao.findBrand(brandValue);
			if (null == id){
				id = goodsDao.insertBrand(brandValue);
			}
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findType (String typeValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		if(null == typeValue || typeValue.length() == 0){
			result.setSuccess(false);
			return result;
		}
		Integer id;
		try {
			id = goodsDao.findType(typeValue);
			if (null == id){
				id = goodsDao.insertType(typeValue);
			}
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findSize (String sizeValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		if(null == sizeValue || sizeValue.length() == 0){
			result.setSuccess(false);
			return result;
		}
		Integer id;
		try {
			id = goodsDao.findSize(sizeValue);
			if (null == id){
				id = goodsDao.insertSize(sizeValue);
			}
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> findColor (String colorValue) {

		ServiceResult<Integer> result = new ServiceResult<Integer>();
		if(null == colorValue || colorValue.length() == 0){
			result.setSuccess(false);
			return result;
		}
		Integer id;
		try {
			id = goodsDao.findColor(colorValue);
			if (null == id){
				id = goodsDao.insertColor(colorValue);
			}
			result.setSuccess(true);
			result.setData(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
