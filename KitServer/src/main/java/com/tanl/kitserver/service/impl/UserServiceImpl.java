package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 用户服务中间层
 * Created by Administrator on 2016/5/2.
 */
@Component
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	public UserDao userDao;

	public ServiceResult<Integer> insertUser (UserDo user) {
		int increment;
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			increment = userDao.insert(user);
			result.setData(increment);
			result.setSuccess(true);
		} catch (SQLException e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<UserDo> queryUserInfo (String userId) {

		ServiceResult<UserDo> result = new ServiceResult<UserDo>();
		UserDo userDo;
		try {
			userDo = userDao.queryUserInfo(userId);
			result.setData(userDo);
			result.setSuccess(true);
		} catch (SQLException e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> resetPassword (UserDo user) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			boolean daoResult = userDao.resetPassword(user);
			result.setSuccess(true);
			result.setData(daoResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> addApplication (UserApplication ua) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		boolean resDao = false;
		try {
			resDao = userDao.addApplication(ua);
			result.setSuccess(true);
			result.setData(resDao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<String> queryApplication (UserApplication ua) {
		ServiceResult<String> result = new ServiceResult<String>();
		String resDao;
		try {
			resDao = userDao.queryApplicationState(ua);
			result.setSuccess(true);
			result.setData(resDao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
