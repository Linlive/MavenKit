package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.UserDo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 用户数据库
 * Created by Administrator on 2016/5/1.
 */
//@Component
public interface UserDao {
	int insert(UserDo user) throws SQLException;
	boolean resetPassword(UserDo user) throws SQLException;
	UserDo queryUserInfo(Map param) throws SQLException;
	List<UserDo> queryAllUser(Map param) throws SQLException;
}
