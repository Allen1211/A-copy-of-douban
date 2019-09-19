package com.allen.douban.bean;

import java.io.Serializable;
import java.util.List;

import com.allen.douban.entity.Comment;
import com.allen.douban.util.DateUtil;

public class CommentBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer commentId;
	private Integer articleId;
	private Integer userId;
	private String nickname;
	private String commentText;
	private String createdTime;
	private Boolean isLiked;
	private Integer likeCount;
	private List<ReplyBean> replyList;

	
	public CommentBean() {
		// TODO Auto-generated constructor stub
	}
	
	public CommentBean(Comment comment,String nickname,boolean isLiked,int likeCount,List<ReplyBean> replyList) {
		this.commentId = comment.getCommentId();
		this.articleId = comment.getArticleId();
		this.userId = comment.getUserId();
		this.nickname = nickname;
		this.commentText = comment.getCommentText();
		this.createdTime = DateUtil.dateToString(comment.getCreatedTime(), DateUtil.formatYearToSecond);
		this.isLiked = isLiked;
		this.likeCount = likeCount;
		this.replyList = replyList;
	}
	
	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public List<ReplyBean> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<ReplyBean> replyList) {
		this.replyList = replyList;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickName) {
		this.nickname = nickName;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
