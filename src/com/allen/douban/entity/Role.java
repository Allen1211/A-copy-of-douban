package com.allen.douban.entity;


public class Role {
	private String roleName;

	private Integer roleId;

	private Boolean roleStatus;

	public Role() {

	}
	public String getRoleName() {
		return this.roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getRoleId() {
		return this.roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Boolean getRoleStatus() {
		return this.roleStatus;
	}


	public void setRoleStatus(Boolean roleStatus) {
		this.roleStatus = roleStatus;
	}
}