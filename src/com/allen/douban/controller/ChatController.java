package com.allen.douban.controller;

import com.alibaba.fastjson.JSONObject;
import com.allen.douban.bean.ChatLock;
import com.allen.douban.bean.UserBean;
import com.allen.douban.entity.User;
import com.allen.douban.factory.ServiceFactory;
import com.allen.douban.service.UserService;
import com.allen.douban.util.JSONUtil;
import com.allen.douban.util.ResultUtil;
import com.allen.douban.util.SessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatController extends HttpServlet {

    private UserService userService = (UserService) ServiceFactory.getInstance().getService("UserService");
    private static Map<Integer, ChatLock> lockMap = new ConcurrentHashMap<>();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
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

    /**
     * 某个用户发起对另一个用户的私聊
     * 传入 参数 receiverId， 被私聊用户Id
     * @param request
     * @param response
     * @throws Exception
     */
    private void requireChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserBean userBean = SessionUtil.getUserBeanFromSession(request);
        String receiverIdStr = (String) request.getParameter("receiverId");
        // 参数错误
        if(userBean==null || receiverIdStr == null || receiverIdStr.length()==0){
            response.sendRedirect("error.jsp");
            return;
        }
        int callId = userBean.getUser().getUserId();    // caller， 当前会话发起私聊的用户
        int receiverId = Integer.parseInt(receiverIdStr);   // receiver , 被私聊的用户
        ChatLock lock = lockMap.get(receiverId);            // 获得被私聊者的锁
        synchronized (lock){
//            System.out.println(callId + " require connect to "+receiverId + " notify its lock");
            lock.setCallerId(callId);
            lock.setCallerName(userBean.getUser().getNickname());
            lock.notify();  // 唤醒被私聊者线程， 通知被私聊者
        }

        User receiver = userService.findUserById(receiverId);
        request.setAttribute("receiver",receiver);
        request.getRequestDispatcher("chat.jsp").forward(request,response);
    }

    /**
     * 前台ajax轮询等待方法， 该请求线程wait等待进入阻塞状态
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     * @throws ClassNotFoundException
     */
    private void waitForChat(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, ClassNotFoundException {
        JSONObject jsonObject = JSONUtil.getResquestJSONObjectNoEscape(request);
        Integer userId = jsonObject.getIntValue("userId");
        Integer timeLimit = jsonObject.getIntValue("timeLimit");
        if(!lockMap.containsKey(userId)){   // 如果该用户的锁没有被加入到lockMap
            lockMap.put(userId,new ChatLock(userId));	// 给这个user新建一把锁
        }
        ChatLock lock = lockMap.get(userId);	// 获得这把锁
        synchronized (lock){
            try {
//                System.out.println(Thread.currentThread().getName() +" "+userId+ " waiting");
                lock.wait(timeLimit-500);   // 超时时间
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
                以下为被唤醒或者等待时间结束
             */
            if(lock.getCallerId().equals(0)){   // callerId为零，意味着没有被私聊，返回400
                ResultUtil.failResponse(response,400, "timeout");
            }else{  //被私聊，返回200
//                System.out.println(lock.getCallerId() + " notify "+userId);
                Map<String,Object> result = new HashMap<>();
                result.put("callerId",lock.getCallerId());
                result.put("callerName",lock.getCallerName());
                ResultUtil.successResponse(response,result);
                lock.reset();   // 必须把锁重新初始化，清除caller的信息
            }
        }
    }

    /**
     * 前台离开页面的时候，调用该接口， 将对于用户的线程唤醒（解除阻塞状态），释放资源。
     * 为了防止前台发出ajax长连接后，离开页面，但后台线程还在阻塞的情况
     * @param request
     * @param response
     * @throws Exception
     */
    private void endWaitForChat(HttpServletRequest request, HttpServletResponse response) throws Exception{
        UserBean userBean = SessionUtil.getUserBeanFromSession(request);
        if(userBean == null){
            response.sendRedirect("error.jsp");
            return;
        }
        Integer userId = userBean.getUser().getUserId();
        ChatLock lock = lockMap.get(userId);
        synchronized (lock){
            lock.notify();
            lock.reset();
//            System.out.println(userId + " left the page");
        }
        ResultUtil.successResponse(response);
    }
}
