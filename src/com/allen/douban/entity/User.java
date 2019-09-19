package com.allen.douban.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	

	private static final long serialVersionUID = 1L;

	private String userDesc;

	private Boolean userStatus;

	private String userPassword;

	private String userEmail;

	private Boolean userIsAdmin;

	private Date userRegistTime;

	private Integer userId;

	private String userImage;

	private String userName;

	private String nickname;

	public User() {

	}
	public String getUserDesc() {
		return this.userDesc;
	}


	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}
	public Boolean getUserStatus() {
		return this.userStatus;
	}


	public void setUserStatus(Boolean userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserPassword() {
		return this.userPassword;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return this.userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Boolean getUserIsAdmin() {
		return this.userIsAdmin;
	}


	public void setUserIsAdmin(Boolean userIsAdmin) {
		this.userIsAdmin = userIsAdmin;
	}
	public Date getUserRegistTime() {
		return this.userRegistTime;
	}


	public void setUserRegistTime(Date userRegistTime) {
		this.userRegistTime = userRegistTime;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserImage() {
		return this.userImage;
	}


	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}
	public String getUserName() {
		return this.userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickname() {
		return this.nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}