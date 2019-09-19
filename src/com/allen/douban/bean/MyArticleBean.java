package com.allen.douban.bean;

import java.io.Serializable;

import com.allen.douban.entity.Article;
import com.allen.douban.util.DateUtil;

public class MyArticleBean implements Serializable{
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;
	private Integer articleId;
	private String title;
	private String createdTime;
	
	public MyArticleBean() {
		// TODO Auto-generated constructor stub
	}
	public MyArticleBean(Article article) {
		this.setArticleId(article.getArticleId());
		this.setTitle(article.getArticleTitle());
		this.setCreatedTime(DateUtil.dateToString(article.getUpdateTime(), DateUtil.formatYearToSecond));
	}
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
}
