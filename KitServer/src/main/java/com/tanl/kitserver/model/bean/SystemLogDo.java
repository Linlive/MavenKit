package com.tanl.kitserver.model.bean;

import java.sql.Timestamp;

/**
 * 用户管理员查看普通的日记记录
 * 用户登录系统、商家申请。
 * 以及用户的删除等
 * Created by Administrator on 2016/5/21.
 */
public class SystemLogDo {
	//日志记录生成的时间
	private long id;

	private int logType;
	private Timestamp recodeTime;

	private String logInfo;
	private String logExtra;

	public SystemLogDo(){

	}

	public long getId () {

		return id;
	}

	public void setId (long id) {

		this.id = id;
	}

	public int getLogType () {

		return logType;
	}

	public void setLogType (int logType) {

		this.logType = logType;
	}

	public Timestamp getRecodeTime () {

		return recodeTime;
	}

	public void setRecodeTime (Timestamp recodeTime) {

		this.recodeTime = recodeTime;
	}

	public String getLogInfo () {

		return logInfo;
	}

	public void setLogInfo (String logInfo) {

		this.logInfo = logInfo;
	}

	public String getLogExtra () {

		return logExtra;
	}

	public void setLogExtra (String logExtra) {

		this.logExtra = logExtra;
	}
}
