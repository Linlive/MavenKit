package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.AdminDao;
import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.AdminService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * 查询管理员
 * Created by Administrator on 2016/5/21.
 */
@Component
public class AdminServiceImpl implements AdminService {

	@Resource(name = "adminDao")
	public AdminDao adminDao;
	@Resource(name = "userDao")
	public UserDao userDao;

	public ServiceResult<AdminDo> queryUser (AdminDo adminDo) {
		AdminDo retAdminDo;
		ServiceResult<AdminDo> result = new ServiceResult<AdminDo>();
		try {
			retAdminDo = adminDao.queryAdminInfo(adminDo);
			result.setSuccess(true);
			result.setData(retAdminDo);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> insertUser (AdminDo adminDo) {

		Integer retAdminDo;
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			retAdminDo = adminDao.updateAdminInfo(adminDo);
			result.setSuccess(true);
			result.setData(retAdminDo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<List<UserApplication>> queryApplication () {
		ServiceResult<List<UserApplication>> result = new ServiceResult<List<UserApplication>>();
		List<UserApplication> daoRes;
		try {
			daoRes = adminDao.queryApplication();
			result.setSuccess(true);
			result.setData(daoRes);


		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public ServiceResult<Boolean> handleApplication(long applicationId, boolean accept){
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		boolean daoRet = false;
		try {
			daoRet = adminDao.handleApplication(applicationId, accept);
			result.setSuccess(true);
			result.setData(daoRet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<List<UserDo>> queryAllUser () {
		ServiceResult<List<UserDo>> result = new ServiceResult<List<UserDo>>();
		List<UserDo> datas;
		try {
			datas = adminDao.queryAllUser();
			result.setSuccess(true);
			result.setData(datas);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> deleteUser (String userId) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		boolean daoRet;
		try {
			daoRet = adminDao.deleteUser(userId);
			result.setSuccess(true);
			result.setData(daoRet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
