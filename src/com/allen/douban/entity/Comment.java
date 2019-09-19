package com.allen.douban.entity;

import java.util.Date;

public class Comment {
	private Integer articleId;

	private String commentText;

	private Date createdTime;

	private Integer userId;

	private Integer commentId;

	public Comment() {

	}
	public Integer getArticleId() {
		return this.articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getCommentText() {
		return this.commentText;
	}


	public void setCommentText(String commentText) {
		this.commentText = commentText;
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
	public Integer getCommentId() {
		return this.commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
}