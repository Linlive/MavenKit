package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.GoodsDo;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsDao {
	int addGoods (GoodsDo goodsDo) throws SQLException;
	Integer findBrand(String brandValue) throws SQLException;
	Integer findType(String typeValue) throws SQLException;
	Integer findSize(String sizeValue) throws SQLException;
	Integer findColor(String colorValue) throws SQLException;

}
