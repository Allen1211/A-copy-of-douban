package com.allen.douban.entity;


public class Reply {
	private Integer replyId;

	private Integer toReplyId;

	private String replyText;

	private Integer toUserId;

	private Integer replyType;

	private Integer commentId;

	private Integer fromUserId;

	public Reply() {

	}
	public Integer getReplyId() {
		return this.replyId;
	}


	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Integer getToReplyId() {
		return this.toReplyId;
	}


	public void setToReplyId(Integer toReplyId) {
		this.toReplyId = toReplyId;
	}
	public String getReplyText() {
		return this.replyText;
	}


	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public Integer getToUserId() {
		return this.toUserId;
	}


	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getReplyType() {
		return this.replyType;
	}


	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	public Integer getCommentId() {
		return this.commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getFromUserId() {
		return this.fromUserId;
	}


	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
}