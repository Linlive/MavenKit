package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.UserDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

	public GoodsDo findSingleGoods() throws SQLException {
		Object obj = sqlMapClient.queryForObject("");
		return (GoodsDo)(obj);
	}

	public List<GoodsDo> findGoodsByShopKeeperId(UserDo userDo) throws SQLException {
		if(userDo == null){
			return findGoodsFirst();
		}
		List goodsDos = sqlMapClient.queryForList("queryGoodsByShopKeeper", userDo);
		return listToListBean(goodsDos);
	}

	public List<GoodsDo> findGoodsFirst () throws SQLException {

		List goodsDos = sqlMapClient.queryForList("queryGoodsDefault");
		return listToListBean(goodsDos);
	}
	private List<GoodsDo> listToListBean(List goods) throws SQLException {
		List<GoodsDo> retGoods = new LinkedList<GoodsDo>();
		GoodsDo tmp;
		for (Object goodsDo : goods) {
			tmp = (GoodsDo) goodsDo;
			tmp.setGoodsBrandValue(findBrandValue(tmp.getGoodsBrand()));
			tmp.setGoodsTypeValue(findTypeValue(tmp.getGoodsType()));
			tmp.setGoodsSizeValue(findSizeValue(tmp.getGoodsSize()));
			tmp.setGoodsColorValue(findColorValue(tmp.getGoodsColor()));

			retGoods.add(tmp);
		}
		return retGoods;
	}

	/**
	 * 根据值查询type
	 *
	 * 或根据type查找值
	 *
	 */
	public Integer findBrand (String brandValue) throws SQLException {

		String query = "%" + brandValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryBrand", query);
	}

	public String findBrandValue (Integer brandId) throws SQLException {

		return (String)sqlMapClient.queryForObject("Goods.queryBrandValue", brandId);
	}

	public Integer findType (String typeValue) throws SQLException {

		String query = "%" + typeValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryType", query);
	}

	public String findTypeValue (Integer typeId) throws SQLException {

		return (String)sqlMapClient.queryForObject("Goods.queryTypeValue", typeId);
	}

	public Integer findSize (String sizeValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("querySize", sizeValue);
	}

	public String findSizeValue (Integer sizeId) throws SQLException {

		return (String)sqlMapClient.queryForObject("Goods.querySizeValue", sizeId);
	}

	public Integer findColor (String colorValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("queryColor", colorValue);
	}

	public String findColorValue (Integer colorId) throws SQLException {

		return (String)sqlMapClient.queryForObject("Goods.queryColorValue", colorId);
	}
}
