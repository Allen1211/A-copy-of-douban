package com.allen.douban.entity;

import java.util.Date;

public class Mail {
	private Integer mailId;

	private Date mailTime;

	private Integer toUserId;

	private Integer fromUserId;

	public Mail() {

	}
	public Integer getMailId() {
		return this.mailId;
	}


	public void setMailId(Integer mailId) {
		this.mailId = mailId;
	}
	public Date getMailTime() {
		return this.mailTime;
	}


	public void setMailTime(Date mailTime) {
		this.mailTime = mailTime;
	}
	public Integer getToUserId() {
		return this.toUserId;
	}


	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getFromUserId() {
		return this.fromUserId;
	}


	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
}