package com.allen.douban.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.UserService;
import com.allen.douban.util.JSONUtil;
import com.allen.douban.util.ResultUtil;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/AccountController")
@SuppressWarnings(value = { "all" })
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = (UserService) ServiceFactory.getInstance().getService("UserService");
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		// 截取请求的xx.do方法名
		String methodName = "";
		if(url.contains("?")){
			methodName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
		}else{
			methodName = url.substring(url.lastIndexOf("/") + 1);
		}
		Method method = null;
		try {
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			// 出错
			e.printStackTrace();
			response.sendRedirect("/douban/error.jsp");
		}
	}
	
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				
		HttpSession session = request.getSession();
		
		
		JSONObject json = JSONUtil.getResquestJSONObject(request);
//		//如果json转换失败，返回错误信息。
//		if (json.getString("err") != null) {
//			response.getWriter().write(json.toJSONString());
//			return;
//		}
		String userName = json.getString("userName");
		String password = json.getString("password");
		String code = json.getString("code");
		String autoLogin = json.getString("autoLogin");
		String correctCode = (String) session.getAttribute("code");
		
		
		if (!code.equals(correctCode)) {
			ResultUtil.failResponse(response, 400, "验证码错误");
			return;
		}

		Msg msg = userService.login(userName, password);
		UserBean user = (UserBean) msg.getData();
		//登录成功
		if (msg.isSuccess()) {
			session.setAttribute("user", user);
			//如果用户选中了自动登录
			if(autoLogin.equals("1")) {
				//设置自动登录的Cookies
				Cookie cookie = new Cookie("autoLogin",userName+"_"+password);
				cookie.setMaxAge(24*60*60);
				response.addCookie(cookie);
			}
			ResultUtil.successResponse(response);
		} else {
			ResultUtil.failResponse(response, 400, msg.getMessage());
		}
	}
	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//销毁自动登录的Cookie
		Cookie autoLoginCookie = new Cookie("autoLogin","0");
		autoLoginCookie.setMaxAge(0);
		response.addCookie(autoLoginCookie);
		//销毁session
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect("login.jsp");
	}
	
	private void regist(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		JSONObject json = JSONUtil.getResquestJSONObject(request);
		
		String correctCode = (String) session.getAttribute("code");
		String code = json.getString("code");
		if (!code.equals(correctCode)) {
			ResultUtil.failResponse(response, 400, "验证码错误");
			return;
		}
		
		String userName = json.getString("userName");
		String password = json.getString("password");
		String repeatPassword = json.getString("repeatPassword");
		String email = json.getString("email");
		String nickname = json.getString("nickname");

		Msg msg = userService.regist(userName, password, repeatPassword, email,nickname);
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {			
			ResultUtil.failResponse(response, 400, msg.getMessage());
		}
	}
}
