package com.allen.douban.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.CommentBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.service.CommentReplyService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.util.JSONUtil;
import com.allen.douban.util.PageUtil;
import com.allen.douban.util.ResultUtil;
import com.allen.douban.util.XSSUtil;

/**
 * Servlet implementation class CommentController
 */
@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentReplyService service =
			(CommentReplyService) ServiceFactory.getInstance().getService("CommentReplyService");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentController() {
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
	
	private void getComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int articleId = 0;
		int userId = 0;
		try {
			articleId = Integer.parseInt(request.getParameter("articleId"));
			userId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
		} catch(Exception e) {
			e.printStackTrace();
		}
		PageBean commentPageBean = PageUtil.getPageBean(request);
		List<CommentBean> commentList = service.getArticleComment(articleId,userId, commentPageBean, null);
		request.setAttribute("commentPageBean", commentPageBean);
		request.setAttribute("commentList", commentList);
		request.getRequestDispatcher("Article.jsp").forward(request, response);
	}
	
	private void addComment(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = JSONUtil.getResquestJSONObject(request);
		int articleId = 0;
		int userId = 0;
		String text = null;
		try {
			articleId = json.getIntValue("articleId");
			userId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
			text = XSSUtil.escapeXSS(json.getString("text"));
		} catch(Exception e) {
			e.printStackTrace();
			ResultUtil.failResponse(response, 400, "评论失败");
			return;
		}		
		Msg msg = service.addComment(userId, text, articleId);
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
	}
	
	private void addReply(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONObject json = JSONUtil.getResquestJSONObject(request);
		int commentId = 0;
		int toReplyId = 0;
		int fromUserId = 0;
		int toUserId = 0;
		int replyType = -1;
		String text = null;
		try {
			commentId = json.getIntValue("commentId");
			toReplyId = json.getIntValue("toReplyId");
			fromUserId = ((UserBean)request.getSession().getAttribute("user")).getUser().getUserId();
			toUserId = json.getIntValue("toUserId");
			replyType = json.getIntValue("replyType");
			text = XSSUtil.escapeXSS(json.getString("text"));
		} catch(Exception e) {
			e.printStackTrace();
			JSONObject output = new JSONObject();
			output.put("isSuccess", false);
			output.put("msg", "回复失败");
			response.getWriter().write(output.toJSONString());
		}
		
		Msg msg = service.addReply(commentId, toReplyId, text, replyType, fromUserId, toUserId);
		
		if(msg.isSuccess()) {
			ResultUtil.successResponse(response);
		}else {
			ResultUtil.failResponse(response, 500, msg.getMessage());
		}
	}
}
