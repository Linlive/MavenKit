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
		if(null == indentDo){
			return null;
		}
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
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			boolean editSuccess = indentDao.editIndent(indentDo);
			result.setSuccess(true);
			result.setData(editSuccess);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ServiceResult<IndentDo> queryIndent (String indentId) {
		if(null == indentId){
			return null;
		}
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

	public ServiceResult<List<IndentViewDo>> queryIndent (IndentViewDo indentViewDo) {
		if(null == indentViewDo){
			return null;
		}
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

	public ServiceResult<List<IndentViewDo>> queryIndentByUserId (String userId) {
		if(null == userId){
			return null;
		}
		ServiceResult<List<IndentViewDo>> result = new ServiceResult<List<IndentViewDo>>();
		List<IndentViewDo> indentDoList;
		try {
			indentDoList = indentDao.viewIndentByUserId(userId);
			result.setSuccess(true);
			result.setData(indentDoList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> removeIndent (String indentNumber) {
		if(null == indentNumber){
			return null;
		}

		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			Boolean ret = indentDao.removeIndent(indentNumber);
			result.setSuccess(true);
			result.setData(ret);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ServiceResult<Boolean> deleteIndent (String indentId) {

		return null;
	}
}
