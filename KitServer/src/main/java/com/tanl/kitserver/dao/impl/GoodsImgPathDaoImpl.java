package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.GoodsImgPathDao;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/5/24.
 */
public class GoodsImgPathDaoImpl implements GoodsImgPathDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	public void insert (String goodsId, List<String> goodsImgPaths) throws SQLException {

		HashMap<String, Object> goodsImgs = new HashMap<String, Object>();
		goodsImgs.put("goodsId", goodsId);
		goodsImgs.put("goodsPath", goodsImgPaths);

		sqlMapClient.insert("addImgs", goodsImgs);
	}

	public void insert (String goodsId, String goodsImgPath) throws SQLException {
		HashMap<String, String> goodsImgs = new HashMap<String, String>();
		goodsImgs.put("goodsId", goodsId);
		goodsImgs.put("imgPath", goodsImgPath);

//		sqlMapClient.insert("addImgsSingle", goodsImgs);
		sqlMapClient.insert("addImgsSingle", goodsImgs);
	}
}
