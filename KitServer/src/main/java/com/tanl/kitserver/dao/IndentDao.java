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
	IndentDo viewIndent(String indentId) throws SQLException;
	List<IndentViewDo> viewIndent(IndentViewDo indentViewDo) throws SQLException;
	IndentDo viewIndentByUserId(String userId) throws SQLException;


	boolean removeIndent(String indentId) throws SQLException;
	boolean deleteIndent(String indentId) throws SQLException;
}
