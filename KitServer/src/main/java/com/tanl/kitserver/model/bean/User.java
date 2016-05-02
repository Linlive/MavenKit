package com.tanl.kitserver.model.bean;

/**
 * 用户实体
 * 数据库中的表
 * Created by Administrator on 2016/5/1.
 */
public class User {

	private int id;

	private String userId;
	private String name;
	private String password;
	private int age;
	private String phoneNumber;
	private String emailAddress;

	/**
	 * 数据库字段，不提供外部设置，仅能获取该值
	 * @return
	 */
	public int getId () {

		return id;
	}

	public String getUserId () {

		return userId;
	}

	public void setUserId (String userId) {

		this.userId = userId;
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

	public int getAge () {

		return age;
	}

	public void setAge (int age) {

		this.age = age;
	}

	public String getPhoneNumber () {

		return phoneNumber;
	}

	public void setPhoneNumber (String phoneNumber) {

		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress () {

		return emailAddress;
	}

	public void setEmailAddress (String emailAddress) {

		this.emailAddress = emailAddress;
	}
}
