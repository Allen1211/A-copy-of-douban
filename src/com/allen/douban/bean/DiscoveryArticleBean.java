package com.allen.douban.bean;

import java.io.Serializable;
import java.util.Date;

import com.allen.douban.util.DateUtil;

public class DiscoveryArticleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer authorId;
	private Integer articleId;
	private String authorName;
	private String title;
	private String text;
	private String createdTime;
	private Integer likeCount;
	private Integer forwardCount;
	private Integer collectCount;

	public DiscoveryArticleBean() {
		// TODO Auto-generated constructor stub
	}

	public DiscoveryArticleBean(int authorId,int articleId,String authorName,String title,String text,
			Date createdTime,int likeCount,int forwardCount,int collectCount) {
		this.authorId = authorId;
		this.articleId = articleId;
		this.authorName = authorName;
		this.title = title;
		this.text = text;
		this.createdTime = DateUtil.dateToString(createdTime, DateUtil.formatYearToMinute);
		this.likeCount = likeCount;
		this.forwardCount = forwardCount;
		this.collectCount = collectCount;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getForwardCount() {
		return forwardCount;
	}

	public void setForwardCount(Integer forwardCount) {
		this.forwardCount = forwardCount;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}
}
