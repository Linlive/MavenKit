package com.tanl.kitserver.dao;

import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public interface AdminDao {
	AdminDo queryAdminInfo(AdminDo adminDo) throws SQLException;
	Integer updateAdminInfo(AdminDo adminDo) throws SQLException;
	List<UserApplication> queryApplication() throws SQLException;

	boolean handleApplication (long applicationId, boolean accept) throws SQLException;

	ArrayList<UserDo> queryAllUser() throws SQLException;
	boolean deleteUser(String userId) throws SQLException;
}
