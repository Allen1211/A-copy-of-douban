package com.allen.douban.bean;

import java.io.Serializable;
import java.util.List;

import com.allen.douban.entity.User;
import com.allen.douban.util.ImageIOUtil;
import com.allen.douban.util.DateUtil;

public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;
	private List<String> accessUrls;

	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	public UserBean(User user, List<String> urls) {
		this.user = user;
		this.accessUrls = urls;
	}


	public List<String> getAccessUrls() {
		return accessUrls;
	}

	public void setAccessUrls(List<String> accessUrls) {
		this.accessUrls = accessUrls;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
