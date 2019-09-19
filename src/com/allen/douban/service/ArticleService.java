package com.allen.douban.service;

import java.awt.image.BufferedImage;
import java.util.List;

import com.allen.douban.bean.ArticleEditBean;
import com.allen.douban.bean.DiscoveryArticleBean;
import com.allen.douban.bean.MyArticleBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.Type;

public interface ArticleService {

	ArticleEditBean findArticleEdit(int articleId);

	Msg findArticleDetail(int articleId, int userId);

	Msg findFriendArticle(int userId, PageBean pageBean);

	List<MyArticleBean> findMyArticle(int userId, PageBean pageBean);

	List<DiscoveryArticleBean> findArticleByType(String typeId, PageBean pageBean);

	List<DiscoveryArticleBean> findHitArticle(PageBean pageBean);
	
//	List<> findCollectedArticle(int userId);

	Msg addNewArticle(int userId, String title, String text, List<String> typeId);

	Msg editArticle(String articleId, String title, String text, List<String> typeId);

	List<String> editArticleImage(List<String> img, int articleId);

	Msg deleteArticle(int userId, String articleId);

	int getNextId();

	BufferedImage getImage(int articleId, String imgId);
}
