package com.allen.douban.entity;

import java.util.Date;

public class Collect {
	private Integer articleId;

	private Date createdTime;

	private Integer collectId;

	private Integer userId;

	public Collect() {

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
	public Integer getCollectId() {
		return this.collectId;
	}


	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}