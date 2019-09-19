package com.allen.douban.dao;

import java.sql.SQLException;
import java.util.List;

import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Reply;

public class ReplyDao extends BaseDao {

	public List<Reply> queryReplyByCommentId(int commentId, PageBean pageBean) {
		String sql ="SELECT * FROM reply WHERE comment_id=?";
		return queryMutiple(sql, getParams(commentId), pageBean, Reply.class);
	}
	
	public void addReply(int commentId,int toReplyId,String replyText,int replyType,int fromUserId,int toUserId) throws SQLException {
		String sql = "INSERT INTO reply(comment_id,to_reply_id,reply_text,reply_type,from_user_id,to_user_id) VALUES(?,?,?,?,?,?)";
		try {
			update(sql,getParams(commentId,toReplyId,replyText,replyType,fromUserId,toUserId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
	}
}
