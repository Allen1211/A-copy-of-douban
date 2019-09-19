package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.Date;

public class ForwardDao extends BaseDao {
	public void addForward(int userId, int articleId, Date createdTime) throws SQLException {
		String sql = "INSERT INTO forward (user_id,article_id,created_time) VALUES(?,?,?)";
		try {
			update(sql, getParams(userId, articleId, createdTime));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public int getForwardCount(int articleId) {
		String sql = "SELECT COUNT(*) FROM forward WHERE article_id=?";
		int count = queryCount(sql, getParams(articleId));
		return count;
	}

	public boolean isForwarded(int userId, int articleId) {
		String sql = "SELECT COUNT(*) FROM forward WHERE user_id=? AND article_id=?";
		int count = queryCount(sql, getParams(userId, articleId));
		return count != 0;
	}
}
