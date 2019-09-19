package com.allen.douban.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.allen.douban.bean.PageBean;



public class PageUtil {
	private static final int STARTPAGE = 1;
	private static final int ROWPERPAGE = 3;
	
	public static PageBean getPageBean(HttpServletRequest request)
			throws ServletException {
		
		int page = STARTPAGE;
		int rowPerPage = ROWPERPAGE;	
		String requestPage = (String) request.getParameter("page");
		String requestRowPerPage = (String) request.getParameter("rowPerPage");
		try {
			rowPerPage = Integer.parseInt(requestRowPerPage);
		} catch (NumberFormatException e) {
			rowPerPage = ROWPERPAGE;
		}
		
		//判断是否是第一次访问
		if(requestPage == null ) {
			page = STARTPAGE;
		} else {
			try {
				page = Integer.parseInt(requestPage);
			} catch (NumberFormatException e) {
				page = STARTPAGE;
			}
		}
		PageBean pageBean = new PageBean(rowPerPage,page);
		return pageBean;

	}
}
