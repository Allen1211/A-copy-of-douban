package com.allen.douban.bean;

import java.io.Serializable;
import java.util.Date;

import com.allen.douban.util.DateUtil;

public class ArticlePreviewBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer userId;
	private String nickname;
	private Integer articleId;
	private String authorName;
	private String title;
	private String text;
	private String createdTime;
	private String[] images;
	private Integer type;	// 1-->发表文章  2-->转发文章
	
	public ArticlePreviewBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ArticlePreviewBean(int userId,String nickname,int articleId,String authorName,String title, String text
			,Date createdTime, int type) {
		this.userId = userId;
		this.nickname = nickname;
		this.articleId = articleId;
		this.authorName = authorName;
		this.title = title;
		this.text = text;
		this.createdTime = DateUtil.dateToString(createdTime, DateUtil.formatYearToMinute);
		this.type = type;
	}
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date date) {
		
		this.createdTime = DateUtil.dateToString(date, DateUtil.formatYearToMinute);
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickName) {
		this.nickname = nickName;
	}
	
	
}
