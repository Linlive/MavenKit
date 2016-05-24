package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.AdminDao;
import com.tanl.kitserver.model.bean.AdminDo;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/21.
 */
public class AdminDaoImpl implements AdminDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@Resource(name = "sqlMapClientTemplate")
	SqlMapClientTemplate sqlMapClientTemplate;

	public AdminDo queryAdminInfo (AdminDo adminDo) throws SQLException {

		return (AdminDo)sqlMapClient.queryForObject("queryAdmin");
	}
}
