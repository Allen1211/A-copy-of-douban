package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Comment;

public class CommentDao extends BaseDao{

	public List<Comment> queryCommentByArticleId(int articleId,PageBean pageBean){
		String sql = "SELECT * FROM `comment` WHERE article_id=? ORDER BY created_time";
		return queryMutiple(sql, getParams(articleId), pageBean,Comment.class);
	}
	
	public void addComment(int articleId,int userId,String commentText,Date createdTime) throws SQLException {
		String sql = "INSERT INTO `comment`(article_id,user_id,comment_text,created_time) VALUES(?,?,?,?)";
		try {
			update(sql,getParams(articleId,userId,commentText,createdTime));
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
