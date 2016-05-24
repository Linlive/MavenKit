package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.AdminDo;

import java.sql.SQLException;

/**
 * Created by Administrator on 2016/5/21.
 */
public interface AdminDao {
	AdminDo queryAdminInfo(AdminDo adminDo) throws SQLException;
}
