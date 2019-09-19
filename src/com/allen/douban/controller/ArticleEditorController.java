package com.allen.douban.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.allen.douban.bean.ArticleEditBean;
import com.allen.douban.entity.Type;
import com.allen.douban.service.ArticleService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.TypeService;

/**
 * Servlet implementation class ArticleEditorController
 */
@WebServlet("/ArticleEditorController")
public class ArticleEditorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArticleService articleService
			= (ArticleService) ServiceFactory.getInstance().getService("ArticleService");
	private TypeService typeService
			= (TypeService) ServiceFactory.getInstance().getService("TypeService");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleEditorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mode = request.getParameter("mode");
		String id = request.getParameter("articleId");
		if(mode == null) {
			response.sendRedirect("error.jsp");
			return;
		}else if(mode.equals("NEW")) {
			List<Type> allType = typeService.findAllType();
			request.setAttribute("type", allType);
			request.getRequestDispatcher("ArticleEditor.jsp").forward(request, response);
			return;
		}
		//编辑模式，获取要编辑的文章
		int articleId;
		try {
			articleId = Integer.parseInt(id);
		} catch(NumberFormatException e) {
			response.sendRedirect("error.jsp");
			return;
		}
		ArticleEditBean articleEditBean = articleService.findArticleEdit(articleId);
		List<Type> allType = typeService.findAllType();
		if(articleEditBean == null) {
			response.sendRedirect("error.jsp");
			return;
		}
		request.setAttribute("type", allType);
		request.setAttribute("data",articleEditBean);
		request.getRequestDispatcher("ArticleEditor.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
