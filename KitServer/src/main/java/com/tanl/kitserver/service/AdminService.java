package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.model.bean.UserApplication;
import com.tanl.kitserver.model.bean.UserDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * Created by Administrator on 2016/5/21.
 */
public interface AdminService {
	ServiceResult<AdminDo> queryUser(AdminDo adminDo);
	ServiceResult<Integer> insertUser(AdminDo adminDo);

	ServiceResult<List<UserApplication>> queryApplication();
	ServiceResult<Boolean> handleApplication(long applicationId, boolean accept);

	ServiceResult<List<UserDo>> queryAllUser();
	ServiceResult<Boolean> deleteUser(String userId);
}
