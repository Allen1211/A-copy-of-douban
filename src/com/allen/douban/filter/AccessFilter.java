package com.allen.douban.filter;


import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allen.douban.bean.UserBean;

/**
 * Servlet Filter implementation class AccessFilter
 */
@WebFilter(filterName = "AccessFilter", urlPatterns = "/*")
public class AccessFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AccessFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		if (!isAccessable((HttpServletRequest) request)) {
			HttpServletResponse res = (HttpServletResponse) response;
			//应该转到错误页面，提示无权限信息
			res.sendRedirect("login.jsp");
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private boolean isAccessable(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String destination = request.getRequestURI();
		/**
		 * 特定的URL，不需要登录，放行
		 */
		if(destination.equals("/douban/login.jsp") || destination.equals("/douban/regist.jsp") 
				|| destination.equals("/douban/error.jsp")) {
			return true;
		}
		UserBean user = (UserBean) session.getAttribute("user");
		if (user == null ) {
			// 用户未登陆
			return isAutoLogin(request);
		}
		if (user.getUser().getUserStatus() || user.getUser().getUserIsAdmin()) {
			return true;
		}else {
			return false;
		}
//		List<String> accessURLS = user.getAccessUrls();
//		return accessURLS.contains(destination);
	}
	private boolean isAutoLogin(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		Cookie autoLoginCookie = null;
		for(Cookie c:cookies) {
			if(c.getName().equals("autoLogin")) {
				autoLoginCookie = c;
				break;
			}
		}
		return autoLoginCookie!=null;
	}
}
