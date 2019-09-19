package com.allen.douban.entity;


public class Access {
	private String accessTitle;

	private String accessUrls;

	private Integer accessStatus;

	private Integer accessId;

	public Access() {

	}
	public String getAccessTitle() {
		return this.accessTitle;
	}


	public void setAccessTitle(String accessTitle) {
		this.accessTitle = accessTitle;
	}
	public String getAccessUrls() {
		return this.accessUrls;
	}


	public void setAccessUrls(String accessUrls) {
		this.accessUrls = accessUrls;
	}
	public Integer getAccessStatus() {
		return this.accessStatus;
	}


	public void setAccessStatus(Integer accessStatus) {
		this.accessStatus = accessStatus;
	}
	public Integer getAccessId() {
		return this.accessId;
	}


	public void setAccessId(Integer accessId) {
		this.accessId = accessId;
	}
}