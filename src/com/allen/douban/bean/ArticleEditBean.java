package com.allen.douban.bean;

import java.io.Serializable;
import java.util.List;

import com.allen.douban.entity.Type;

public class ArticleEditBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer articleId;
	private String title;
	private String text;
	private List<Type> typeList;

	public ArticleEditBean() {
		// TODO Auto-generated constructor stub
	}

	public ArticleEditBean(int articleId,String title,String text,List<Type> typeList){
		this.articleId = articleId;
		this.title = title;
		this.text = text;
		this.typeList = typeList;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

}
