package com.allen.douban.entity;


public class Blacklist {
	private Integer blacklistId;

	private Integer toUserId;

	private Integer fromUserId;

	public Blacklist() {

	}
	public Integer getBlacklistId() {
		return this.blacklistId;
	}


	public void setBlacklistId(Integer blacklistId) {
		this.blacklistId = blacklistId;
	}
	public Integer getToUserId() {
		return this.toUserId;
	}


	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getFromUserId() {
		return this.fromUserId;
	}


	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
}