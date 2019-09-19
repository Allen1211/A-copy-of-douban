package com.allen.douban.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allen.douban.bean.UserBean;

public class SessionUtil {
	
	public static UserBean getUserBeanFromSession(HttpServletRequest request) throws Exception{
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		if(userBean == null) {
			throw new NullPointerException("no login");
		}
		return userBean;
		
	}
	

}
