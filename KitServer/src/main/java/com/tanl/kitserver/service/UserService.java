package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.UserDO;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/2.
 */
public interface UserService {
	ServiceResult<Integer> insertUser(UserDO user);
	ServiceResult<UserDO> queryUserInfo (Map param);
	ServiceResult<List<UserDO>> queryAllUser (Map param);
}
