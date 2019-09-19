package com.allen.douban.entity;

public class RoleAccess {
	private Integer roleAccessId;

	private Integer roleId;

	private Integer accessId;

	private Boolean status;

	public RoleAccess() {

	}
	public Integer getRoleAccessId() {
		return this.roleAccessId;
	}


	public void setRoleAccessId(Integer roleAccessId) {
		this.roleAccessId = roleAccessId;
	}
	public Integer getRoleId() {
		return this.roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getAccessId() {
		return this.accessId;
	}


	public void setAccessId(Integer accessId) {
		this.accessId = accessId;
	}
	public Boolean getStatus() {
		return this.status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}
}