package com.allen.douban.entity;

import java.util.Date;

public class Article {
	private Integer articleId;

	private Date createdTime;

	private String articleTitle;

	private Date updateTime;
	
	private Integer userId;

	private String articleText;

	private Integer articleLikes;

	public Article() {

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
	public String getArticleTitle() {
		return this.articleTitle;
	}


	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUserId() {
		return this.userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getArticleText() {
		return this.articleText;
	}


	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}
	public Integer getArticleLikes() {
		return this.articleLikes;
	}


	public void setArticleLikes(Integer articleLikes) {
		this.articleLikes = articleLikes;
	}
}