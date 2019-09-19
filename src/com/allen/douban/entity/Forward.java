package com.allen.douban.entity;

import java.util.Date;

public class Forward {
	private Integer articleId;

	private Date createdTime;

	private Integer forwardId;

	private Integer userId;

	public Forward() {

	}
	public Integer getArticleId() {
		return this.articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Date getCreatedTime() {
		return this.createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getForwardId() {
		return this.forwardId;
	}


	public void setForwardId(Integer forwardId) {
		this.forwardId = forwardId;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}