package com.tanl.kitserver.dao.impl;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.User;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/1.
 */

public class UserDaoImp implements UserDao {

	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	public void insert (User user) {

		try {
			System.out.println("name = " + user.getName());
			sqlMapClient.insert("insertUser");
			//System.out.println(o.toString());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save (User user) {

	}

	public void query (Object param) {

	}
}
