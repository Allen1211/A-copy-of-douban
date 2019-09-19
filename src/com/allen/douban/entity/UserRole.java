package com.allen.douban.entity;


public class UserRole {
	private Integer userRoleId;

	private Integer userId;

	private Integer roleId;

	public UserRole() {

	}
	public Integer getUserRoleId() {
		return this.userRoleId;
	}


	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return this.roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}