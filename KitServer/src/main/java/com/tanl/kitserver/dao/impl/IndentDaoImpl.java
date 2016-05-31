package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.GoodsDao;
import com.tanl.kitserver.dao.IndentDao;
import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class IndentDaoImpl implements IndentDao {

	@Resource(name = "sqlMapClient")
	SqlMapClient sqlMapClient;

	@Resource(name = "goodsDao")
	GoodsDao goodsDao;

	public boolean createIndent (IndentDo indentDo) throws SQLException {
		sqlMapClient.insert("createIndent", indentDo);
		return true;
	}

	public boolean editIndent (IndentDo indentDo) throws SQLException {

		return false;
	}

	public IndentDo viewIndent (String indentId) throws SQLException {
		return (IndentDo) sqlMapClient.insert("viewIndent", indentId);
	}

	public List<IndentViewDo> viewIndent (IndentViewDo indentViewDo) throws SQLException {
		int stateId = findStateId(indentViewDo.getIndentState());

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("buyerId", indentViewDo.getBuyerId());
		map.put("indentState", stateId);
		List retList = sqlMapClient.queryForList("viewIndentByState", map);
		List<IndentViewDo> resultList = new LinkedList<IndentViewDo>();
		IndentViewDo viewDo;
		for(Object o : retList){
			IndentDo indentDo = (IndentDo) o;
			viewDo = changeIndentToIndentViewDo(indentDo);
			resultList.add(viewDo);
		}
		return resultList;
	}

	/**
	 * 将IndentDo转换成IndentViewDo
	 * @param indentDo do
	 * @return
	 * @throws SQLException
	 */
	private IndentViewDo changeIndentToIndentViewDo(IndentDo indentDo) throws SQLException {
		IndentViewDo viewDo = new IndentViewDo();
		String goodsId = indentDo.getGoodsId();
		viewDo.setBuyerId(indentDo.getBuyerId());
		viewDo.setGoodsName(getGoodsName(goodsId));
		viewDo.setPictureUrls(goodsDao.findGoodsUrl(goodsId));
		viewDo.setGoodsDesc(goodsDao.findGoodsDesc(goodsId));
		return viewDo;
	}

	public IndentDo viewIndentByUserId (String userId) throws SQLException {
		return (IndentDo) sqlMapClient.insert("viewIndentByUserId", userId);
	}

	public boolean removeIndent (String indentId) throws SQLException {

		return false;
	}

	public boolean deleteIndent (String indentId) throws SQLException {

		return false;
	}

	private String getGoodsName(String goodsId) throws SQLException {
		return (String) sqlMapClient.queryForObject("getGoodsName", goodsId);
	}

	/**
	 * 根据状态ID查找值
	 * @return
	 * @throws SQLException
	 */
	private String findStateValue(int stateId) throws SQLException {
		return (String) sqlMapClient.queryForObject("findStateValue", stateId);
	}

	/**
	 * 根据状态值查询ID（编号）
	 * @param stateValue
	 * @return
	 * @throws SQLException
	 */
	private int findStateId(String stateValue) throws SQLException {
		return (Integer) sqlMapClient.queryForObject("findState", stateValue);
	}

}
