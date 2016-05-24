package com.tanl.kitserver.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface GoodsImgPathDao {
	void insert(String goodsId, List<String> goodsImgPaths) throws SQLException;

	void insert(String goodsId, String goodsImgPath) throws SQLException;

}
