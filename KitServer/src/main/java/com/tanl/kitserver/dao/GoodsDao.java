package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.UserDo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsDao {
	int addGoods (GoodsDo goodsDo) throws SQLException;

	public GoodsDo findSingleGoods() throws SQLException;
	// 根据商家id 查询商品
	List<GoodsDo> findGoodsByShopKeeperId(UserDo userDo) throws SQLException;
	List<GoodsDo> findGoodsFirst() throws SQLException;

	//type
	Integer findBrand(String brandValue) throws SQLException;
	Integer findType(String typeValue) throws SQLException;
	Integer findSize(String sizeValue) throws SQLException;
	Integer findColor(String colorValue) throws SQLException;

	String findBrandValue(Integer brandId) throws SQLException;
	String findTypeValue(Integer typeId) throws SQLException;
	String findSizeValue(Integer sizeId) throws SQLException;
	String findColorValue(Integer colorId) throws SQLException;
}
