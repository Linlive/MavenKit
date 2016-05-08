package com.tanl.kitserver.util.common;

/**
 * 服务器处理完请求后返回的类型
 * 包装给客户端
 * Created by Administrator on 2016/5/4.
 */
public class CilentResult<T> {
	private String message;
	private Boolean success;

	private T data;

	public String getMessage () {

		return message;
	}

	public void setMessage (String message) {

		this.message = message;
	}

	public Boolean getSuccess () {

		return success;
	}

	public void setSuccess (Boolean success) {

		this.success = success;
	}

	public T getData () {

		return data;
	}

	public void setData (T data) {

		this.data = data;
	}
	public boolean isSuccess(){
		return this.success;
	}
}
