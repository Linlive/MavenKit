package com.tanl.kitserver.model.bean;

/**
 * 管理员对象，目前仅进行简单的管理工作。
 *
 * Created by Administrator on 2016/5/21.
 */
public class AdminDo {
	private String name;
	private String password;

	public AdminDo(){

	}

	public String getName () {

		return name;
	}

	public void setName (String name) {

		this.name = name;
	}

	public String getPassword () {

		return password;
	}

	public void setPassword (String password) {

		this.password = password;
	}
}
