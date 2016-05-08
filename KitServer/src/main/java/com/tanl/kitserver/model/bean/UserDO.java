package com.tanl.kitserver.model.bean;

/**
 * 用户实体
 * 数据库中的表
 * Created by Administrator on 2016/5/1.
 */
public class UserDO {

	private int id;

	private String userId;
	private String userName;
	private String userPassword;
	/**
	 * 必须为Integer,否则数据库查询异常
	 */
	private Integer userAge;
	private String userPhoneNumber;
	private String userEmailAddress;
	private String userSex;

	/**
	 * 数据库字段，不提供外部设置，仅能获取该值
	 * @return
	 */

	public void setId(int id){
		this.id = id;
	}

	public int getId () {

		return id;
	}

	public String getUserId () {

		return userId;
	}

	public void setUserId (String userId) {

		this.userId = userId;
	}

	public String getUserName () {

		return userName;
	}

	public void setUserName (String userName) {

		this.userName = userName;
	}

	public String getUserPassword () {

		return userPassword;
	}

	public void setUserPassword (String userPassword) {

		this.userPassword = userPassword;
	}

	public Integer getUserAge () {

		return userAge;
	}

	public void setUserAge (Integer userAge) {

		this.userAge = userAge;
	}

	public String getUserPhoneNumber () {

		return userPhoneNumber;
	}

	public void setUserPhoneNumber (String userPhoneNumber) {

		this.userPhoneNumber = userPhoneNumber;
	}

	public String getUserEmailAddress () {

		return userEmailAddress;
	}

	public void setUserEmailAddress (String userEmailAddress) {

		this.userEmailAddress = userEmailAddress;
	}

	public String getUserSex () {

		return userSex;
	}

	public void setUserSex (String userSex) {

		this.userSex = userSex;
	}
}
