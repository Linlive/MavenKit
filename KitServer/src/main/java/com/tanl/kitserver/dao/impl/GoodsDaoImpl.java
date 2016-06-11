package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.model.bean.GoodsDo;
import com.tanl.kitserver.model.bean.MyPage;
import com.tanl.kitserver.model.bean.UserDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.*;

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

	public int butGoods (String goodsId) throws SQLException {
		return sqlMapClient.update("buyGoods", goodsId);
	}

	public GoodsDo findSingleGoods () throws SQLException {

		Object obj = sqlMapClient.queryForObject("");
		return (GoodsDo) (obj);
	}

	public List<GoodsDo> findGoodsByShopKeeperId (UserDo userDo) throws SQLException {

		if (userDo == null) {
			return findGoodsFirst();
		}
		List goodsDos = sqlMapClient.queryForList("queryGoodsByShopKeeper", userDo);
		return listToListBean(goodsDos);
	}

	public List<GoodsDo> findGoodsFirst () throws SQLException {

		List goodsDos = sqlMapClient.queryForList("queryGoodsDefault");
		return listToListBean(goodsDos);
	}

	/**
	 * 数据分页查询
	 *
	 * @param start 起始页号 0 开始
	 * @param size  页面大小
	 * @return 查询到的数据
	 * @throws SQLException
	 */
	public List<GoodsDo> queryLoadMoreGoods (int start, int size) throws SQLException {

		MyPage myPage = new MyPage();
		myPage.setPageNo(start);
		myPage.setStartColumn((start) * size);
		myPage.setPageSize(size);

		List list = sqlMapClient.queryForList("loadMoreGoods", myPage);

		return listToListBean(list);
	}

	private List<GoodsDo> listToListBean (List goods) throws SQLException {

		List<GoodsDo> retGoods = new LinkedList<GoodsDo>();
		GoodsDo tmp;
		for (Object goodsDo : goods) {
			tmp = (GoodsDo) goodsDo;
			//设置value
			tmp.setGoodsBrandValue(findBrandValue(tmp.getGoodsBrand()));
			tmp.setGoodsTypeValue(findTypeValue(tmp.getGoodsType()));
			tmp.setGoodsSizeValue(findSizeValue(tmp.getGoodsSize()));
			tmp.setGoodsColorValue(findColorValue(tmp.getGoodsColor()));
			//设置imgUrl
			tmp.setImgUrlList(findGoodsUrl(tmp.getGoodsId()));
			retGoods.add(tmp);
		}
		return retGoods;
	}

	/**
	 * 商家查询
	 *
	 * @param user
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public List<GoodsDo> queryShopkeeperGoods (UserDo user, MyPage page) throws SQLException {

		if (null == user || null == page) {
			return null;
		}
		String shopkeeperId = user.getShopKeeperId();
		int pageNo = page.getPageNo();
		int pageSize = page.getPageSize();
		int column = (pageNo + 1) * pageSize;

		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setStartColumn(column);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopkeeperId", shopkeeperId);
		map.put("startColumn", pageNo);
		map.put("pageSize", pageSize);

		List goodsDos = sqlMapClient.queryForList("queryGoodsByShopKeeper", map);

		return listToListBean(goodsDos);
	}

	public List<GoodsDo> querySpecialGoods (String key, MyPage page) throws SQLException {

		if (null == key || null == page) {
			return null;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopkeeperId", key);
		map.put("startColumn", page.getPageNo());
		map.put("pageSize", page.getPageSize());
		List<GoodsDo> listRet;
//		List goodsDosShop = sqlMapClient.queryForList("queryGoodsBySpecialKey", map);
//		listRet = listToListBean(goodsDosShop);
		map.put("goodsName", "%" + key + "%");
		List goodsDosName = sqlMapClient.queryForList("queryGoodsBySpecialKey", map);
		listRet = listToListBean(goodsDosName);
		return listRet;
	}

	public Integer getGoodsCount () throws SQLException {

		return (Integer) sqlMapClient.queryForObject("goodsAllCount");
	}

	/**
	 * 根据值查询type
	 * <p>
	 * 或根据type查找值
	 */
	public Integer findBrand (String brandValue) throws SQLException {

		String query = "%" + brandValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryBrand", query);
	}

	public int insertBrand (String brandValue) throws SQLException {

		return (Integer) sqlMapClient.insert("insertBrand", brandValue);
	}

	public String findBrandValue (Integer brandId) throws SQLException {

		return (String) sqlMapClient.queryForObject("queryBrandValue", brandId);
	}

	public Integer findType (String typeValue) throws SQLException {

		String query = "%" + typeValue + "%";
		return (Integer) sqlMapClient.queryForObject("queryType", query);
	}

	public int insertType (String typeValue) throws SQLException {

		return (Integer) sqlMapClient.insert("insertType", typeValue);
	}

	public String findTypeValue (Integer typeId) throws SQLException {

		return (String) sqlMapClient.queryForObject("queryTypeValue", typeId);
	}

	public Integer findSize (String sizeValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("querySize", sizeValue);
	}

	public int insertSize (String sizeValue) throws SQLException {

		return (Integer) sqlMapClient.insert("insertSize", sizeValue);
	}

	public String findSizeValue (Integer sizeId) throws SQLException {

		return (String) sqlMapClient.queryForObject("querySizeValue", sizeId);
	}

	public Integer findColor (String colorValue) throws SQLException {

		return (Integer) sqlMapClient.queryForObject("queryColor", colorValue);
	}

	public int insertColor (String colorValue) throws SQLException {

		return (Integer) sqlMapClient.insert("insertColor", colorValue);
	}

	public String findColorValue (Integer colorId) throws SQLException {

		return (String) sqlMapClient.queryForObject("queryColorValue", colorId);
	}

	public List<String> findGoodsUrl (String goodsId) throws SQLException {

		List<String> imgs = new ArrayList<String>();
		List result = sqlMapClient.queryForList("queryImgs", goodsId);
		Iterator iterator = result.iterator();
		while (iterator.hasNext()) {
			String tmp = (String) iterator.next();
			if (tmp.contains("\\")) {
				tmp = tmp.replaceAll("\\\\", "/");
			}
			imgs.add(tmp);
		}
		return imgs;
	}

	public String findGoodsDesc (String goodsId) throws SQLException {

		return (String) sqlMapClient.queryForObject("queryGoodsDesc", goodsId);
	}
}
