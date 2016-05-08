package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.UserDO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 用户数据库
 * Created by Administrator on 2016/5/1.
 */
//@Component
public interface UserDao {
	int insert(UserDO user) throws SQLException;
	//UserDO queryUser(UserDO userDO) throws SQLException;
	UserDO queryUserInfo(Map param) throws SQLException;
	List<UserDO> queryAllUser(Map param) throws SQLException;
}
