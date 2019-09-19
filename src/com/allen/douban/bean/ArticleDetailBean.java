package com.allen.douban.bean;

import java.io.Serializable;
import java.util.List;

import com.allen.douban.entity.Article;
import com.allen.douban.entity.Type;
import com.allen.douban.util.DateUtil;

public class ArticleDetailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Article article;
	private Integer forwardCount;
	private Integer collectCount;
	private List<Type> type;
	private String nickname;
	private boolean isLiked;
	private boolean isForwarded;
	private boolean isCollected;

	public ArticleDetailBean(Article article, int forwardCount, int collectCount, List<Type> type,
			String nickname,boolean isLiked,boolean isForwarded,boolean isCollected) {
		this.article = article;
		this.forwardCount = forwardCount;
		this.collectCount = collectCount;
		this.type = type;
		this.nickname = nickname;
		this.isLiked = isLiked;
		this.isForwarded = isForwarded;
		this.isCollected = isCollected;
	}

	public ArticleDetailBean() {
		// TODO Auto-generated constructor stub
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

	public List<Type> getType() {
		return type;
	}

	public void setType(List<Type> type) {
		this.type = type;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String userName) {
		this.nickname = userName;
	}


	public boolean getIsLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public boolean getIsForwarded() {
		return isForwarded;
	}

	public void setForwarded(boolean isForwarded) {
		this.isForwarded = isForwarded;
	}

	public boolean getIsCollected() {
		return isCollected;
	}

	public void setCollected(boolean isCollected) {
		this.isCollected = isCollected;
	}

}
