package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.model.bean.GoodsDo;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 商品数据库处理层
 * Created by Administrator on 2016/5/24.
 */
public class GoodsDaoImpl implements GoodsDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	public int addGoods (GoodsDo goodsDo) throws SQLException {

		sqlMapClient.insert("insertGoods", goodsDo);

		return 0;
	}

	public Integer findBrand (String brandValue) throws SQLException {

		String query = "%" + brandValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryBrand", query);
	}

	public Integer findType (String typeValue) throws SQLException {

		String query = "%" + typeValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryType", query);
	}

	public Integer findSize (String sizeValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("querySize", sizeValue);
	}

	public Integer findColor (String colorValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("queryColor", colorValue);
	}
}
