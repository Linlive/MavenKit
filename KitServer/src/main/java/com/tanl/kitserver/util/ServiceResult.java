package com.tanl.kitserver.util;

/**
 * 封装数据库操作结果
 * Created by Administrator on 2016/5/2.
 */
public class ServiceResult<T> {

	// pageNo
	private Integer pageNo;

	private Integer pageSize;

	private Integer total;

	private Boolean success;

	private String errorCode;

	// 从DAO层得到的数据
	private T data;


	public Integer getPageNo () {

		return pageNo;
	}

	public void setPageNo (Integer pageNo) {

		this.pageNo = pageNo;
	}

	public Integer getPageSize () {

		return pageSize;
	}

	public void setPageSize (Integer pageSize) {

		this.pageSize = pageSize;
	}

	public Integer getTotal () {

		return total;
	}

	public void setTotal (Integer total) {

		this.total = total;
	}

	public Boolean getSuccess () {

		return success;
	}

	public void setSuccess (Boolean success) {

		this.success = success;
	}

	public String getErrorCode () {

		return errorCode;
	}

	public void setErrorCode (String errorCode) {

		this.errorCode = errorCode;
	}

	public T getData () {

		return data;
	}

	public void setData (T data) {

		this.data = data;
	}

	public boolean isSuccess() {
		return this.success;
	}
}
