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
		Object o1 = sqlMapClient.insert("createIndent", indentDo);
		int line = goodsDao.butGoods(indentDo.getGoodsId());
		return true;
	}

	public boolean editIndent (IndentDo indentDo) throws SQLException {
		int updateNum = sqlMapClient.update("editIndent", indentDo);
		if(updateNum >= 1){
			return true;
		}
		return false;
	}

	public IndentDo queryUserIndentCustom (String indentNumber) throws SQLException {
		return (IndentDo) sqlMapClient.insert("queryIndent", indentNumber);
	}

	public List<IndentViewDo> queryUserIndentCustom (IndentViewDo indentViewDo) throws SQLException {
		if(indentViewDo == null){
			return null;
		}
		int stateId = findStateId(indentViewDo.getIndentState());

		if(indentViewDo.getShopkeeperId() != null && indentViewDo.getShopkeeperId().length() > 0){
			if(stateId == 0){
				return viewIndentByUserId(indentViewDo.getShopkeeperId());
			}
			return viewIndentByShopkeeperId(indentViewDo.getShopkeeperId());
		}
		if(indentViewDo.getBuyerId() != null && indentViewDo.getBuyerId().length() > 0){

			if(stateId == 0){
				return viewIndentByUserId(indentViewDo.getBuyerId());
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("buyerId", indentViewDo.getBuyerId());
			map.put("indentState", stateId);
			List retList = sqlMapClient.queryForList("userQueryIndentByState", map);
			return changeIndentToIndentView(retList);
		}
		return null;
	}

	private List<IndentViewDo> queryShopkeeperIndentCustom (String shopkeeperId, int stateId) throws SQLException {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("shopkeeperId", shopkeeperId);
		map.put("indentState", stateId);
		List retList = sqlMapClient.queryForList("shopkeeperQueryIndentByState", map);
		return changeIndentToIndentView(retList);
	}
	private List<IndentViewDo> changeIndentToIndentView (List retList) throws SQLException {
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
		viewDo.setIndentState(findStateValue(indentDo.getIndentState()));
		viewDo.setGoodsId(goodsId);
		return viewDo;
	}

	public List<IndentViewDo> viewIndentByUserId (String buyerId) throws SQLException {
		List<IndentViewDo> resultList = new LinkedList<IndentViewDo>();
		List daoResult = sqlMapClient.queryForList("queryIndentByUserId", buyerId);
		IndentViewDo viewDo;
		for(Object o : daoResult){
			IndentDo indentDo = (IndentDo) o;
			viewDo = changeIndentToIndentViewDo(indentDo);
			resultList.add(viewDo);
		}
		return resultList;
	}

	public List<IndentViewDo> viewIndentByShopkeeperId (String shopkeeper) throws SQLException {
		List<IndentViewDo> resultList = new LinkedList<IndentViewDo>();
		List daoResult = sqlMapClient.queryForList("queryIndentByShopkeeperId", shopkeeper);
		IndentViewDo viewDo;
		for(Object o : daoResult){
			IndentDo indentDo = (IndentDo) o;
			viewDo = changeIndentToIndentViewDo(indentDo);
			resultList.add(viewDo);
		}
		return resultList;
	}

	public boolean removeIndent (String indentNumber) throws SQLException {
		int num = sqlMapClient.delete("removeIndent", indentNumber);
		if(num >= 1){
			return true;
		}
		return false;
	}

	/**
	 * 暂未实现
	 * @param indentNumber
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteIndent (String indentNumber) throws SQLException {

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
		if("全部".equals(stateValue)){
			return 0;
		}
		return (Integer) sqlMapClient.queryForObject("findState", stateValue);
	}

}
