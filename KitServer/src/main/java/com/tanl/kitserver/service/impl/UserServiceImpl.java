package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	public ServiceResult<UserDo> queryUserInfo (Map param) {

		ServiceResult<UserDo> result = new ServiceResult<UserDo>();
		UserDo userDo;
		try {
			userDo = userDao.queryUserInfo(param);
			result.setData(userDo);
			result.setSuccess(true);
		} catch (SQLException e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	public ServiceResult<List<UserDo>> queryAllUser (Map param) {

		ServiceResult<List<UserDo>> result = new ServiceResult<List<UserDo>>();
		List<UserDo> dos;
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
