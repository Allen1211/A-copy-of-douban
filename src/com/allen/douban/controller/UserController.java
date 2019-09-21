package com.allen.douban.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.FriendBean;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.entity.User;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.FriendService;
import com.allen.douban.service.UserService;
import com.allen.douban.util.ImageIOUtil;
import com.allen.douban.util.JSONUtil;
import com.allen.douban.util.ResultUtil;
import com.allen.douban.util.SessionUtil;
import com.allen.douban.util.XSSUtil;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = (UserService) ServiceFactory.getInstance().getService("UserService");
	private FriendService friendService = (FriendService)ServiceFactory.getInstance().getService("FriendService");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	private void getUserImage(HttpServletRequest request, HttpServletResponse response) {
		String para = request.getParameter("userId");
		if (para == null) {
			return;
		}
		try {
			int userId = Integer.parseInt(para);
			String path = "e:\\Java\\Project\\douban\\WebContent\\img\\user\\" + userId + "\\head.jpg";
			byte[] data = ImageIOUtil.getFileByPath(path);
			if (data == null) {
				return;
			}
			response.setContentType("image/jpeg");
			response.getOutputStream().write(data);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserBean userBean = null;
		try {
			userBean = SessionUtil.getUserBeanFromSession(request);
		}catch (Exception e) {
			response.sendRedirect("error.jsp");
			return;
		}
		
		JSONObject json = JSONUtil.getResquestJSONObject(request);
		String img = XSSUtil.escapeXSS(json.getString("img"));
		String nickname = XSSUtil.escapeXSS(json.getString("nickname"));
		String email = XSSUtil.escapeXSS(json.getString("email"));
		String desc = XSSUtil.escapeXSS(json.getString("desc"));

		Msg msg = userService.editUserInfo(userBean.getUser(), img, nickname, email, desc);
		
		if(msg.isSuccess()) {
			HttpSession session = request.getSession();
			session.removeAttribute("user");
			userBean.setUser((User) (msg.getData()));
			session.setAttribute("user", userBean);
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
		
	}

	private void userpage(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserBean userBean = null;
		try {
			userBean = SessionUtil.getUserBeanFromSession(request);
		} catch (Exception e) {
			response.sendRedirect("login.jsp");
			return;
		}
		int userId = userBean.getUser().getUserId();
		String param = request.getParameter("userId");
		String requireIdStr = request.getParameter("userId");
        int requireId;  // 请求参数中的Id
        if(requireIdStr == null || requireIdStr.length() == 0){
		    requireId = userId;
        }else{
		    requireId = Integer.parseInt(requireIdStr);
        }
		Msg msg;
		User pageUser;
		boolean isMyPage;
		if(userId == requireId){
			msg = friendService.findAllFirendId(userId);
			pageUser = userBean.getUser();
			isMyPage = true;
		}else{
			msg = friendService.findAllFollowerId(requireId);
			pageUser = userService.findUserById(requireId);
			isMyPage = false;
		}
		List<FriendBean> friendIdList = (List<FriendBean>) msg.getData();
		request.setAttribute("isMyFriend",friendService.isFriend(userId,requireId).isSuccess());
		request.setAttribute("pageUser",pageUser);
		request.setAttribute("size",friendIdList.size());
		request.setAttribute("isMyPage",isMyPage);
		request.setAttribute("friendIdList",friendIdList);
		request.getRequestDispatcher("userpage.jsp").forward(request,response);
	}

	private void follow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserBean userBean = null;
		try {
			userBean = SessionUtil.getUserBeanFromSession(request);
		}catch (Exception e) {
			response.sendRedirect("login.jsp");
			return;
		}
		int fromUserId = userBean.getUser().getUserId();
		int toUserId = Integer.valueOf(request.getParameter("toUserId"));
		Msg msg = friendService.follow(fromUserId,toUserId);
		if(msg.isSuccess()){
			ResultUtil.successResponse(response);
		}else{
			ResultUtil.failResponse(response,400,msg.getMessage());
		}
	}

	private void cancelFollow(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserBean userBean = null;
		try {
			userBean = SessionUtil.getUserBeanFromSession(request);
		}catch (Exception e) {
			response.sendRedirect("login.jsp");
			return;
		}
		int fromUserId = userBean.getUser().getUserId();
		int toUserId = Integer.valueOf(request.getParameter("toUserId"));
		Msg msg = friendService.cancelFollow(fromUserId,toUserId);
		if(msg.isSuccess()){
			ResultUtil.successResponse(response);
		}else{
			ResultUtil.failResponse(response,400,msg.getMessage());
		}
	}
}
