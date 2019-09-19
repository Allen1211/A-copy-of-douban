package com.allen.douban.bean;

import java.io.Serializable;

import com.allen.douban.entity.Reply;

public class ReplyBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer replyId;
	private Integer commentId;
	private Integer toReplyId;
	private String replyText;
	private Integer replyType; // 1->回复回复 2->回复评论
	private Integer fromUserId;
	private Integer toUserId;
	private String fromUserNickname;
	private String toUserNickname;

	public ReplyBean() {
		// TODO Auto-generated constructor stub
	}
	
	public ReplyBean(Reply reply,String fromUserNickname,String toUserNickname) {
		this.replyId = reply.getReplyId();
		this.commentId = reply.getCommentId();
		this.toReplyId = reply.getToReplyId();
		this.replyText = reply.getReplyText();
		this.replyType = reply.getReplyType();
		this.fromUserId = reply.getFromUserId();
		this.toUserId = reply.getToUserId();
		this.fromUserNickname = fromUserNickname;
		this.toUserNickname = toUserNickname;
	}
	
	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getToReplyId() {
		return toReplyId;
	}

	public void setToReplyId(Integer toReplyId) {
		this.toReplyId = toReplyId;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}

	public Integer getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Integer formUserId) {
		this.fromUserId = formUserId;
	}

	public Integer getToUserId() {
		return toUserId;
	}

	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}

	public String getFromUserNickname() {
		return fromUserNickname;
	}

	public void setFromUserNickname(String formUserNickname) {
		this.fromUserNickname = formUserNickname;
	}

	public String getToUserNickname() {
		return toUserNickname;
	}

	public void setToUserNickname(String toUserNickname) {
		this.toUserNickname = toUserNickname;
	}

}
