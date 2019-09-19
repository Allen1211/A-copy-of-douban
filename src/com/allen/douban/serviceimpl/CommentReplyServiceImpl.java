package com.allen.douban.serviceimpl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.allen.douban.bean.CommentBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.bean.ReplyBean;
import com.allen.douban.dao.CommentDao;
import com.allen.douban.factory.DaoFactroy;
import com.allen.douban.dao.ReplyDao;
import com.allen.douban.entity.Comment;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.Reply;
import com.allen.douban.service.ActionService;
import com.allen.douban.service.CommentReplyService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.UserService;
import com.allen.douban.util.DbUtil;

public class CommentReplyServiceImpl implements CommentReplyService {

	private CommentDao commentDao = (CommentDao) DaoFactroy.getInstance().getDao("CommentDao");
	private ReplyDao replyDao = (ReplyDao) DaoFactroy.getInstance().getDao("ReplyDao");
	private UserService userService = (UserService) ServiceFactory.getInstance().getService("UserService");
	private ActionService actionService = (ActionService) ServiceFactory.getInstance().getService("ActionService");

	@Override
	public List<CommentBean> getArticleComment(int articleId,int userId, PageBean commentPageBean, PageBean ReplyPageBean) {
		List<CommentBean> beanList = new ArrayList<>();
		List<Comment> commentList = commentDao.queryCommentByArticleId(articleId, commentPageBean);
		for (Comment comment : commentList) {
			int commentId = comment.getCommentId();
			List<ReplyBean> replyBeanList = this.getCommentReply(commentId, ReplyPageBean);
			String commentAuthorNickname = userService.getUserNickname(comment.getUserId());
			boolean isLiked = actionService.isLiked(commentId,userId, 2);
			int likeCount = actionService.getLikeCount(commentId, 2);
			CommentBean commentBean = new CommentBean(comment, commentAuthorNickname,isLiked,likeCount, replyBeanList);
			beanList.add(commentBean);
		}

		return beanList;
	}

	@Override
	public List<ReplyBean> getCommentReply(int commentId, PageBean pageBean) {
		List<ReplyBean> replyBeanList = new ArrayList<>();
		List<Reply> replyList = replyDao.queryReplyByCommentId(commentId, pageBean);
		for (Reply reply : replyList) {
			String fromNickName = userService.getUserNickname(reply.getFromUserId());
			String toNickName = userService.getUserNickname(reply.getToUserId());
			ReplyBean replyBean = new ReplyBean(reply, fromNickName, toNickName);
			replyBeanList.add(replyBean);
		}
		return replyBeanList;
	}

	@Override
	public Msg addComment(int userId, String commentText, int articleId) {
		if(commentText == null || commentText.length() == 0) {
			return new Msg(false,"评论内容不能为空");
		}
		DbUtil.beginTransaction();
		try {
			commentDao.addComment(articleId, userId, commentText, new Date());
			DbUtil.commitTransaction();
		} catch(SQLException e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction();
			return new Msg(false,"评论失败");
		}	
		return new Msg(true,"评论成功");
	}

	@Override
	public Msg addReply(int commentId, int toReplyId, String replyText, int replyType, int fromUserId, int toUserId) {
		if(replyText == null || replyText.length() == 0) {
			return new Msg(false,"回复内容不能为空");
		}
		DbUtil.beginTransaction();
		try {
			replyDao.addReply(commentId, toReplyId, replyText, replyType, fromUserId, toUserId);
			DbUtil.commitTransaction();
		} catch(SQLException e) {
			e.printStackTrace();
			DbUtil.rollbackTransaction();
			return new Msg(false,"回复失败");
		}
		
		return new Msg(true,"回复成功");
	}

}
