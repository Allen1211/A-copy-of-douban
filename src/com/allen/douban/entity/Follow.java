package com.allen.douban.entity;


public class Follow {
	private Integer groupId;

	private Integer toUserId;

	private Integer followId;

	private Integer fromUserId;

	public Follow() {

	}
	public Integer getGroupId() {
		return this.groupId;
	}


	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getToUserId() {
		return this.toUserId;
	}


	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getFollowId() {
		return this.followId;
	}


	public void setFollowId(Integer followId) {
		this.followId = followId;
	}
	public Integer getFromUserId() {
		return this.fromUserId;
	}


	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
}