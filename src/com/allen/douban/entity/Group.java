package com.allen.douban.entity;


public class Group {
	private Integer groupId;

	private Integer userId;

	private String groupName;

	public Group() {

	}
	public Integer getGroupId() {
		return this.groupId;
	}


	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getGroupName() {
		return this.groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}