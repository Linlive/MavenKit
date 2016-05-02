package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.UserDao;
import com.tanl.kitserver.model.bean.User;
import com.tanl.kitserver.service.UserService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/5/2.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	public UserDao userDao;

	public ServiceResult<Boolean> insertUser (User user) {
		userDao.insert(user);
		ServiceResult result = new ServiceResult();

		return result;
	}
}
