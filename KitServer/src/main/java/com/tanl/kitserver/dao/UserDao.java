package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;

import java.sql.SQLException;

/**
 * 用户数据库
 * Created by Administrator on 2016/5/1.
 */
//@Component
public interface UserDao {
	int insert(UserDo user) throws SQLException;
	boolean resetPassword(UserDo user) throws SQLException;
	UserDo queryUserInfo (String userId) throws SQLException;

	boolean updateUserInfo(UserDo userDo) throws SQLException;
	boolean addApplication(UserApplication ua) throws SQLException;
	String queryApplicationState(UserApplication ua) throws SQLException;
}
