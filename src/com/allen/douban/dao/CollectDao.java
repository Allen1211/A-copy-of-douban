package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CollectDao extends BaseDao {
	public int getArticleCollectCount(int articleId) {
		String sql = "SELECT COUNT(*) FROM collect WHERE collect.article_id=?";
		return queryCount(sql, getParams(articleId));
	}

	public boolean isCollected(int articleId, int userId) {
		String sql = "SELECT COUNT(*) FROM collect WHERE collect.article_id=? AND collect.user_id=?";
		int count = queryCount(sql,getParams(articleId,userId));
		return count!=0;
	}

	public void addCollect(int articleId, int userId, Date createdTime) throws SQLException {
		String sql = "INSERT INTO collect(article_id,user_id,created_time) VALUES(?,?,?)";
		List<Object> params = getParams(articleId, userId, createdTime);
		try {
			update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
}
