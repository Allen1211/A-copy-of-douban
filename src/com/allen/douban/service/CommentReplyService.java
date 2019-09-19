package com.allen.douban.service;

import java.util.List;

import com.allen.douban.bean.CommentBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.bean.ReplyBean;
import com.allen.douban.entity.Msg;

public interface CommentReplyService {

	List<CommentBean> getArticleComment(int articleId,int userId,PageBean commentPageBean, PageBean ReplyPageBean);

	List<ReplyBean> getCommentReply(int commentId,PageBean pageBean);
	
	Msg addComment(int userId, String commentText,int articleId);

	Msg addReply(int commentId,int toReplyId,String replyText,int replyType,int fromUserId,int toUserId);
	
}
