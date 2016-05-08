package com.tanl.kitserver.util.common;

/**
 * 封装服务器到客户端的数据
 * Created by Administrator on 2016/5/5.
 */
public class ClientInfoObj<T> {

	/**
	 * 0 成功
	 * 1 格式错误
	 * 2 数据库操作失败
	 */
	private int errorCode;
	private Boolean operateSuccess = Boolean.FALSE;
	private String digestMessage;

	private T data;

	public int getErrorCode () {

		return errorCode;
	}

	public void setErrorCode (int errorCode) {

		this.errorCode = errorCode;
	}

	public String getDigestMessage () {

		return digestMessage;
	}

	public void setDigestMessage (String digestMessage) {

		this.digestMessage = digestMessage;
	}

	public Boolean getOperateSuccess () {

		return operateSuccess;
	}
	public boolean isSuccess(){
		return this.operateSuccess;
	}

	public void setOperateSuccess (Boolean operateSuccess) {

		this.operateSuccess = operateSuccess;
	}

	public T getData () {

		return data;
	}

	public void setData (T data) {

		this.data = data;
	}
}
