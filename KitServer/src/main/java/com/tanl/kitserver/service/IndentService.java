package com.tanl.kitserver.service;

import com.tanl.kitserver.model.bean.IndentDo;
import com.tanl.kitserver.model.bean.IndentViewDo;
import com.tanl.kitserver.util.ServiceResult;

import java.util.List;

/**
 * 订单处理服务层
 * Created by Administrator on 2016/5/31.
 */
public interface IndentService {
	ServiceResult<Boolean> createIndent(IndentDo indentDo);
	ServiceResult<Boolean> editIndent(IndentDo indentDo);
	ServiceResult<IndentDo> viewIndent(String indentId);
	ServiceResult<List<IndentViewDo>> viewIndent(IndentViewDo indentViewDo);
	ServiceResult<IndentDo> viewIndentByUserId(String userId);



	ServiceResult<Boolean> removeIndent(String indentId);
	ServiceResult<Boolean> deleteIndent(String indentId);
}
