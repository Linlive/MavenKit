package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.User;
import com.tanl.kitserver.util.ServiceResult;

/**
 * Created by Administrator on 2016/5/2.
 */
public interface UserService {
	ServiceResult<Boolean> insertUser(User user);
}
