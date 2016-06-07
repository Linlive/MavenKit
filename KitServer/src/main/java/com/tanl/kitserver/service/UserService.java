package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.util.ServiceResult;

/**
 * Created by Administrator on 2016/5/2.
 */
public interface UserService {
	ServiceResult<Integer> insertUser(UserDo user);
	ServiceResult<UserDo> queryUserInfo (String userId);

	ServiceResult<Boolean> resetPassword(UserDo user);

	ServiceResult<Boolean> addApplication (UserApplication ua);
	ServiceResult<String> queryApplication (UserApplication ua);
}
