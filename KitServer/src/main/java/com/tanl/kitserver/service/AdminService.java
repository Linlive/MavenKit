package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.util.ServiceResult;

/**
 * Created by Administrator on 2016/5/21.
 */
public interface AdminService {
	ServiceResult<AdminDo> queryUser(AdminDo adminDo);
	ServiceResult<Integer> insertUser(AdminDo adminDo);
}
