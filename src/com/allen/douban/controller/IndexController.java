package com.allen.douban.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allen.douban.bean.ArticlePreviewBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Msg;
import com.allen.douban.service.ArticleService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.util.PageUtil;

/**
 * Servlet implementation class IndexController
 */
@WebServlet("/index")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleService
			= (ArticleService) ServiceFactory.getInstance().getService("ArticleService");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	private void processResquest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserBean userBean = ((UserBean)session.getAttribute("user"));
		if(userBean == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		int userId = userBean.getUser().getUserId();
		PageBean pageBean = PageUtil.getPageBean(request);
		//调用service层接口，传入PageBean，得到请求页的数据
		Msg msg = (Msg) articleService.findFriendArticle(userId, pageBean);
		List<ArticlePreviewBean> data = (List<ArticlePreviewBean>) msg.getData();
		//写入request，转发跳转
		if(data != null) {
			request.setAttribute("preData",data);
			request.setAttribute("pageBean", pageBean);
		} 
		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processResquest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
