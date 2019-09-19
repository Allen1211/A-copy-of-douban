package com.allen.douban.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen.douban.bean.DiscoveryArticleBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.entity.Type;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.ArticleService;
import com.allen.douban.service.TypeService;
import com.allen.douban.util.PageUtil;

/**
 * Servlet implementation class DiscoveryController
 */
@WebServlet("/DiscoveryController")
public class DiscoveryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleService
			= (ArticleService) ServiceFactory.getInstance().getService("ArticleService");
	private TypeService typeService
			= (TypeService) ServiceFactory.getInstance().getService("TypeService");
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DiscoveryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String typeIdStr = request.getParameter("typeId");
		PageBean pageBean = PageUtil.getPageBean(request);
		List<DiscoveryArticleBean> data = null;
		if(typeIdStr != null){
			data = articleService.findArticleByType(typeIdStr, pageBean);
		}else{
			data = articleService.findHitArticle(pageBean);
		}
		List<Type> typeData = typeService.findAllType();
		if (data == null || typeData == null) {
			response.sendRedirect("error.jsp");
			return;
		}
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("TypeList", typeData);
		request.setAttribute("ArticleDataList", data);
		request.getRequestDispatcher("discovery.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
