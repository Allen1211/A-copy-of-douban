package com.allen.douban.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.ArticleDetailBean;
import com.allen.douban.bean.DiscoveryArticleBean;
import com.allen.douban.bean.MyArticleBean;
import com.allen.douban.bean.PageBean;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.Type;
import com.allen.douban.entity.Msg;
import com.allen.douban.service.ArticleService;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.TypeService;
import com.allen.douban.util.JSONUtil;
import com.allen.douban.util.PageUtil;
import com.allen.douban.util.ResultUtil;
import com.allen.douban.util.SessionUtil;
import com.allen.douban.util.XSSUtil;

/**
 * Servlet implementation class ArticleController
 */
@WebServlet("/ArticleController")
@SuppressWarnings(value = {"all"})
public class ArticleController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ArticleService articleService
            = (ArticleService) ServiceFactory.getInstance().getService("ArticleService");
    private TypeService typeService
            = (TypeService) ServiceFactory.getInstance().getService("TypeService");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArticleController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getRequestURI();
        // 截取请求的xx.do方法名
        String methodName = "";
        if (url.contains(".do")) {
            methodName = url.substring(url.lastIndexOf("/") + 1, url.indexOf(".do"));
        } else if (url.contains("?")) {
            methodName = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
        } else {
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

    private void saveArticleImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JSONObject jsonData = JSONUtil.getResquestJSONObjectNoEscape(request);
        JSONArray jsonArr = jsonData.getJSONArray("img");
        List<String> imgList = jsonArr.toJavaList(String.class);


        /**
         * 为-1表示是新文章上传的文件，这时候并不确定最终文章是否会保存
         */
        int articleId = Integer.parseInt(jsonData.getString("id"));
        if (articleId == -1) {
            articleId = articleService.getNextId();
        }


        List<String> imgURLS = articleService.editArticleImage(imgList, articleId);

        if (imgURLS == null) {
            ResultUtil.failResponse(response, 500, "图片上传失败");
            return;
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("id", articleId);
        resultMap.put("img", imgURLS);
        ResultUtil.successResponse(response, resultMap);
    }

    private void getArticleImage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int articleId = Integer.parseInt(request.getParameter("articleId"));
        String imgId = request.getParameter("imgId");
        BufferedImage image = articleService.getImage(articleId, imgId);
        // 通知浏览器应该以图片形式打开
        response.setHeader("Content-Type", "image/jpeg");
        // 写出到客户端
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private void addArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 获得用户登录信息
         */
        UserBean userBean = null;
        try {
            userBean = SessionUtil.getUserBeanFromSession(request);
        } catch (Exception e) {
            ResultUtil.failResponse(response, 404, "您还未登录，请先登录");
            return;
        }
        int userId = userBean.getUser().getUserId();

        /**
         * 取得请求信息
         */
        JSONObject jsonData = JSONUtil.getResquestJSONObjectNoEscape(request);
        JSONArray jsonArr = jsonData.getJSONArray("typeId");
        List<String> typeId = jsonArr.toJavaList(String.class);
        String title = XSSUtil.escapeXSS(jsonData.getString("title"));
        String text = jsonData.getString("text");

        /**
         * 新增文章
         */
        Msg msg = articleService.addNewArticle(userId, title, text, typeId);

        /**
         * 结果处理
         */
        if (msg.isSuccess()) {
            ResultUtil.successResponse(response);
        } else {
            ResultUtil.failResponse(response, 400, "添加文章失败");
        }
    }


    private void editArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 获得用户登录信息
         */
        UserBean userBean = null;
        try {
            userBean = SessionUtil.getUserBeanFromSession(request);
        } catch (Exception e) {
            ResultUtil.failResponse(response, 404, "您还未登录，请先登录");
            return;
        }
        int userId = userBean.getUser().getUserId();
        /**
         * 取得请求信息
         */
        JSONObject jsonData = JSONUtil.getResquestJSONObjectNoEscape(request);
        JSONArray jsonArr = jsonData.getJSONArray("typeId");
        List<String> typeId = jsonArr.toJavaList(String.class);
        String title = XSSUtil.escapeXSS(jsonData.getString("title"));
        String text = XSSUtil.escapeScript(jsonData.getString("text"));
        String articleId = jsonData.getString("id");
        /**
         * 编辑文章
         */
        Msg msg = articleService.editArticle(articleId, title, text, typeId);

        /**
         * 结果处理
         */
        if (msg.isSuccess()) {
            ResultUtil.successResponse(response);
        } else {
            ResultUtil.failResponse(response, 400, "添加文章失败");
        }
    }

    private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserBean userBean = null;
        try {
            userBean = SessionUtil.getUserBeanFromSession(request);
        } catch (Exception e) {
            ResultUtil.failResponse(response, 404, "您还未登录，请先登录");
            return;
        }
        int userId = userBean.getUser().getUserId();
        String articleId = request.getParameter("articleId");
        Msg msg = articleService.deleteArticle(userId, articleId);

        if (msg.isSuccess()) {
            response.sendRedirect("getMyArticle.do");
        } else {
            response.sendRedirect("error.jsp");
        }
    }

    private void article(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = (String) request.getParameter("articleId");
        int userId = 0;
        try {
            userId = SessionUtil.getUserBeanFromSession(request).getUser().getUserId();
        } catch (Exception e1) {
            response.sendRedirect("login.jsp");
        }
        int articleId;
        try {
            articleId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("error.jsp");
            return;
        }
        Msg msg = articleService.findArticleDetail(articleId, userId);
        if (!msg.isSuccess()) {
            response.sendRedirect("error.jsp");
            return;
        }
        ArticleDetailBean article = (ArticleDetailBean) msg.getData();
        request.setAttribute("articleData", article);
        request.getRequestDispatcher("getComment?articleId=" + articleId).forward(request, response);
    }

    private void myArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int userId = ((UserBean) session.getAttribute("user")).getUser().getUserId();
        PageBean pageBean = PageUtil.getPageBean(request);
        List<MyArticleBean> dataList = articleService.findMyArticle(userId, pageBean);
        if (dataList != null) {
            request.setAttribute("dataList", dataList);
            request.setAttribute("pageBean", pageBean);
        }
        request.getRequestDispatcher("MyArticle.jsp").forward(request, response);
    }

    private void getClassifyArticle(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String typeIdStr = request.getParameter("typeId");
        PageBean pageBean = PageUtil.getPageBean(request);
        List<DiscoveryArticleBean> data = articleService.findArticleByType(typeIdStr, pageBean);
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

    private void getArticleSelectedType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int articleId = 0;
        try {
            articleId = Integer.parseInt(request.getParameter("articleId"));
        } catch (Exception e) {
            e.printStackTrace();
            ResultUtil.failJSON(404, "文章不存在");
            return;
        }
        List<Type> selectedType = typeService.findSelectedType(articleId);

        ResultUtil.successResponse(response,selectedType);
    }
}
