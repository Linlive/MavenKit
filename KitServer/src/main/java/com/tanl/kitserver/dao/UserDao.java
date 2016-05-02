package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.User;

/**
 * 用户数据库
 * Created by Administrator on 2016/5/1.
 */
//@Component
public interface UserDao {
	void insert(User user);
	void save(User user);
	void query(Object param);
}
