package com.allen.douban.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.allen.douban.util.VertifyCodeUtil;

/**
 * Servlet implementation class VertifyCodeController
 */
@WebServlet(name="getCodeImage" ,urlPatterns="/getCodeImage")
public class VertifyCodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VertifyCodeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,Object> codeImage = VertifyCodeUtil.getCodeImage();
		String code = (String) codeImage.get("code");
		BufferedImage image = (BufferedImage) codeImage.get("image");
		HttpSession session = request.getSession();
		session.setAttribute("code", code);
		//浏览器不要缓存，防止验证码图片不能刷新
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma","no-cache");
		//通知浏览器应该以图片形式打开
		response.setHeader("Content-Type", "image/jpeg");
		//写出到客户端
		ImageIO.write(image, "jpg", response.getOutputStream());	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
