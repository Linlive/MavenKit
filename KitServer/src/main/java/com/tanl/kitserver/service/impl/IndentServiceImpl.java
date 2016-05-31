package com.tanl.kitserver.service.impl;

import com.tanl.kitserver.dao.IndentDao;
import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;
import com.tanl.kitserver.service.IndentService;
import com.tanl.kitserver.util.ServiceResult;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/5/31.
 */
public class IndentServiceImpl implements IndentService {

	@Resource(name = "indentDao")
	IndentDao indentDao;

	public ServiceResult<Boolean> createIndent (IndentDo indentDo) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {

			boolean daoRet = indentDao.createIndent(indentDo);
			result.setSuccess(true);
			result.setData(daoRet);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> editIndent (IndentDo indentDo) {

		return null;
	}

	public ServiceResult<IndentDo> viewIndent (String indentId) {
		ServiceResult<IndentDo> result = new ServiceResult<IndentDo>();
		IndentDo indentDo;
		try {
			indentDo = indentDao.viewIndent(indentId);
			result.setSuccess(true);
			result.setData(indentDo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<List<IndentViewDo>> viewIndent (IndentViewDo indentViewDo) {
		ServiceResult<List<IndentViewDo>> result = new ServiceResult<List<IndentViewDo>>();
		try {
			List<IndentViewDo> retList = indentDao.viewIndent(indentViewDo);
			result.setSuccess(true);
			result.setData(retList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<IndentDo> viewIndentByUserId (String userId) {

		ServiceResult<IndentDo> result = new ServiceResult<IndentDo>();
		IndentDo indentDo;
		try {
			indentDo = indentDao.viewIndentByUserId(userId);
			result.setSuccess(true);
			result.setData(indentDo);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> removeIndent (String indentId) {

		return null;
	}

	public ServiceResult<Boolean> deleteIndent (String indentId) {

		return null;
	}
}
