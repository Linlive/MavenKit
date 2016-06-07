package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;

import java.sql.SQLException;
import java.util.List;

/**
 * 订单处理 数据库层
 * Created by Administrator on 2016/5/31.
 */
public interface IndentDao {
	boolean createIndent(IndentDo indentDo) throws SQLException;
	boolean editIndent(IndentDo indentDo) throws SQLException;
	IndentDo queryUserIndentCustom (String indentNumber) throws SQLException;
	List<IndentViewDo> queryUserIndentCustom (IndentViewDo indentViewDo) throws SQLException;
	//List<IndentViewDo> queryShopkeeperIndentCustom (String shopkeeperId, int stateId) throws SQLException;
	List<IndentViewDo> viewIndentByUserId(String userId) throws SQLException;
	List<IndentViewDo> viewIndentByShopkeeperId (String shopkeeper) throws SQLException;


	boolean removeIndent(String indentNumber) throws SQLException;
	boolean deleteIndent(String indentNumber) throws SQLException;
}
