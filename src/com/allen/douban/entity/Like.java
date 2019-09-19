package com.allen.douban.entity;

import java.util.*;

public class Like {
	private Date createdTime;

	private Integer userId;

	private Integer targetId;

	private Integer id;

	private Integer likeType;

	public Like() {

	}
	public Date getCreatedTime() {
		return this.createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTargetId() {
		return this.targetId;
	}


	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}
	public Integer getId() {
		return this.id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLikeType() {
		return this.likeType;
	}


	public void setLikeType(Integer likeType) {
		this.likeType = likeType;
	}
}