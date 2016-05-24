package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/2.
 */
public interface UserService {
	ServiceResult<Integer> insertUser(UserDo user);
	ServiceResult<UserDo> queryUserInfo (Map param);
	ServiceResult<List<UserDo>> queryAllUser (Map param);
}
