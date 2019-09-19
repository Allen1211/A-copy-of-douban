package com.allen.douban.controller;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.service.ActionService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.util.ResultUtil;

/**
 * Servlet implementation class ActionController
 */
@WebServlet("/ActionController")
@SuppressWarnings(value = { "all" })
public class ActionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActionService service =
			(ActionService) ServiceFactory.getInstance().getService("ActionService");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionController() {
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
	
	private void like(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		int targetId;
		int userId;
		int likeType;
		try {
			targetId = Integer.parseInt(request.getParameter("targetId"));
			likeType = Integer.parseInt(request.getParameter("likeType"));
			userId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
			return;
		}
		Msg msg = service.doLike(targetId, userId, likeType);
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
	}
	
	private void collect(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		int articleId;
		int userId;
		try {
			articleId = Integer.parseInt(request.getParameter("articleId"));
			userId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
			return;
		}
		Msg msg = service.doCollect(articleId, userId);
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
		
	}
	
	private void forward(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		int userId;
		int articleId;
		try {
			articleId = Integer.parseInt(request.getParameter("articleId"));
			userId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
		} catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
			return;
		}
		Msg msg = service.doForward(articleId, userId);
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
	}
	
	private void getLikeCount(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		int targetId;
		int likeType;
		try {
			targetId = Integer.parseInt(request.getParameter("targetId"));
			likeType = Integer.parseInt(request.getParameter("likeType"));
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
			return;
		}
		Integer likeCount = service.getLikeCount(targetId, likeType);
		response.getWriter().write(likeCount);
	}
}
