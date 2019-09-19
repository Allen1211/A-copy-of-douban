package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.allen.douban.util.DateUtil;

public class LikeDao extends BaseDao {
	public Boolean isLiked(int targetId,int userId,int likeType) {
		String sql = "SELECT COUNT(*) FROM `like` WHERE target_id=? AND user_id=? AND like_type=?";
		int count = queryCount(sql, getParams(targetId,userId,likeType));
		return count != 0;
	}
	
	public int getLikeCount(int targetId,int likeType) {
		String sql = "SELECT COUNT(*) FROM `like` WHERE target_id=? AND like_type=?";
		int count = queryCount(sql,getParams(targetId,likeType));
		return count;
	}
	
	public void addLike(int targetId, int userId, int likeType, Date createdTime) throws SQLException {
		String sql = "INSERT INTO `like` (target_id,user_id,like_type,created_time) VALUES(?,?,?,?)";
		List<Object> params = getParams(targetId, userId, likeType, createdTime);
		try {
			update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	public void cancelLike(int targetId,int userId,int likeType) throws SQLException {
		String sql = "DELETE FROM `like` WHERE target_id=? AND user_id=? AND like_type=?";
		update(sql,getParams(targetId,userId,likeType));
	}
}
