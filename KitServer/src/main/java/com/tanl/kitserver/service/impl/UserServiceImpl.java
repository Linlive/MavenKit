package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserDO;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/2.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	public UserDao userDao;

	public ServiceResult<Integer> insertUser (UserDO user) {
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

	public ServiceResult<UserDO> queryUserInfo (Map param) {

		ServiceResult<UserDO> result = new ServiceResult<UserDO>();
		UserDO userDO;
		try {
			userDO = userDao.queryUserInfo(param);
			result.setData(userDO);
			result.setSuccess(true);
		} catch (SQLException e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	public ServiceResult<List<UserDO>> queryAllUser (Map param) {

		ServiceResult<List<UserDO>> result = new ServiceResult<List<UserDO>>();
		List<UserDO> dos;
		try {
			dos = userDao.queryAllUser(param);
			result.setData(dos);
			result.setSuccess(true);
		} catch (SQLException e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
}
