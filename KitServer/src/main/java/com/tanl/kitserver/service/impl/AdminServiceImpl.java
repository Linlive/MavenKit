package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.AdminDao;
import com.tanl.kitserver.model.bean.AdminDo;
import com.tanl.kitserver.service.AdminService;
import com.tanl.kitserver.util.ServiceResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * 查询管理员
 * Created by Administrator on 2016/5/21.
 */
@Component
public class AdminServiceImpl implements AdminService {

	@Resource(name = "adminDao")
	public AdminDao adminDao;
	public ServiceResult<AdminDo> queryUser (AdminDo adminDo) {
		AdminDo retAdminDo;
		ServiceResult<AdminDo> result = new ServiceResult<AdminDo>();
		try {
			retAdminDo = adminDao.queryAdminInfo(adminDo);
			result.setSuccess(true);
			result.setData(retAdminDo);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Integer> insertUser (AdminDo adminDo) {

		Integer retAdminDo;
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			retAdminDo = adminDao.updateAdminInfo(adminDo);
			result.setSuccess(true);
			result.setData(retAdminDo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
