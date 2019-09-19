package com.allen.douban.entity;


public class ArticleType {
	private Integer articleId;

	private Integer articleTypeId;

	private Integer typeId;

	public ArticleType() {

	}
	public Integer getArticleId() {
		return this.articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	public Integer getArticleTypeId() {
		return this.articleTypeId;
	}


	public void setArticleTypeId(Integer articleTypeId) {
		this.articleTypeId = articleTypeId;
	}
	public Integer getTypeId() {
		return this.typeId;
	}


	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
}