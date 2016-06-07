package com.tanl.kitserver.model.bean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/6/4.
 */
public class UserApplication {
	private long id;
	private Timestamp applicationTime;
	private int applicationType;
	private String filePath;
	private String userId;
	private int applicationState;

	public long getId () {

		return id;
	}

	public void setId (long id) {

		this.id = id;
	}

	public Timestamp getApplicationTime () {

		return applicationTime;
	}

	public void setApplicationTime (Timestamp applicationTime) {

		this.applicationTime = applicationTime;
	}

	public int getApplicationType () {

		return applicationType;
	}

	public void setApplicationType (int applicationType) {

		this.applicationType = applicationType;
	}

	public String getFilePath () {

		return filePath;
	}

	public void setFilePath (String filePath) {

		this.filePath = filePath;
	}

	public String getUserId () {

		return userId;
	}

	public void setUserId (String userId) {

		this.userId = userId;
	}

	public int getApplicationState () {

		return applicationState;
	}

	public void setApplicationState (int applicationState) {

		this.applicationState = applicationState;
	}
}
