package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Article;
import com.allen.douban.entity.Msg;

public class ArticleDao extends BaseDao {

	public List<Map<String, Object>> queryFriArticleByUserId(int userId, PageBean pageBean) {
		String sql = "(SELECT `user`.user_id,article.user_id as author_id,nickname,article.article_id,article_title,article_text,created_time FROM article "
				+ "JOIN follow ON follow.to_user_id=article.user_id "
				+ "JOIN `user`  ON `user`.user_id = follow.to_user_id " + " WHERE follow.from_user_id = ?)" + "UNION "
				+ "(SELECT `user`.user_id,article.user_id as author_id,`user`.nickname,article.article_id,article_title,article_text,forward.created_time FROM article "
				+ "JOIN follow ON follow.from_user_id = ? " + "JOIN `user` ON `user`.user_id = follow.to_user_id "
				+ "JOIN forward ON forward.article_id = article.article_id AND forward.user_id=`user`.user_id) "
				+ "ORDER BY created_time DESC";
		return queryMutipleMap(sql, getParams(userId, userId), pageBean);
	}

	public List<Map<String,Object>> queryArticleByTypeId(int typeId,PageBean pageBean){
		String sql = "SELECT article.user_id,`user`.nickname,article.article_id,article_title,article_text," +
				"article.created_time FROM article " +
				"JOIN article_type ON article.article_id = article_type.article_id " + 
				"JOIN `user` ON `user`.user_id = article.user_id " + 
				"WHERE article_type.type_id =? " +
				"ORDER BY article_likes";
		return queryMutipleMap(sql,getParams(typeId),pageBean);
	}

	public List<Map<String,Object>> queryHitArticle(PageBean pageBean){
		String sql = "SELECT article.user_id,`user`.nickname,article.article_id,article_title,article_text," +
				"article.created_time FROM article " +
				"JOIN `user` ON `user`.user_id = article.user_id " +
				"ORDER BY article_likes DESC";
		return queryMutipleMap(sql,null,pageBean);
	}

	public Article queryArticleById(int articleId) {
		String sql = "SELECT * FROM article WHERE article_id=?";
		List<Object> params = new ArrayList<>();
		params.add(articleId);
		return querySingle(sql, params, Article.class);
	}

	public List<Article> queryArticleByUserId(int userId, PageBean pageBean) {
		String sql = "SELECT * FROM article WHERE user_id=?";
		return queryMutiple(sql, getParams(userId), pageBean, Article.class);
	}
	
	
	public String queryAuthorByArticleId(int articleId) {
		String sql = "SELECT DISTINCT nickname FROM user JOIN article ON user.user_id=article.user_id WHERE article_id=?";
		List<Object> params = new ArrayList<>();
		params.add(articleId);
		String authorName = (String) querySingleMap(sql, params).get("nickname");
		return authorName;
	}

	public List<Map<String,Object>> queryArticleCollected(int userId,PageBean pageBean){
		String sql = "SELECT article.article_id,article_title,article_text,collect.created_time FROM article " + 
				"JOIN collect ON article.article_id=collect.article_id " + 
				"WHERE collect.user_id =?";
		return queryMutipleMap(sql, getParams(userId), pageBean);
	}
	
	public void addArticle(Article article) throws SQLException {
		String sql = "INSERT INTO article(article_id,user_id,article_title,article_text,article_likes,update_time,created_time) "
				+ "VALUES(?,?,?,?,?,?,?)";
		List<Object> params = new ArrayList<>();
		params.add(article.getArticleId());
		params.add(article.getUserId());
		params.add(article.getArticleTitle());
		params.add(article.getArticleText());
		params.add(article.getArticleLikes());
		params.add(article.getUpdateTime());
		params.add(article.getCreatedTime());
		try {
			update(sql, params);
		} catch (SQLException e) {
			System.out.println("文章插入错误");
			e.printStackTrace();
			throw e;
		}
	}

	public void editArticle(int articleId, String title, String text, Date updateTime) throws SQLException {
		String sql = "UPDATE article SET article_title=?,article_text=?,update_time=? WHERE article_id=?";
		List<Object> params = new ArrayList<>();
		params.add(title);
		params.add(text);
		params.add(updateTime);
		params.add(articleId);
		try {
			update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteArticle(int userId,int articleId) throws SQLException {
		String sql = "DELETE FROM article WHERE user_id=? AND article_id=? ";
		try {
			update(sql, getParams(userId,articleId));
		} catch (SQLException e) {
			System.out.println("文章更新错误");
			e.printStackTrace();
			throw e;
		}
	}

	public int getNextId() {
		return getAutoIncrement("douban", "article");
	}
}
