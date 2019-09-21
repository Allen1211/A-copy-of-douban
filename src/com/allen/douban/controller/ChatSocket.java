package com.allen.douban.controller;
import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chatting/{userId}")
public class ChatSocket {

    private SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Map<Integer,ChatSocket> map = new ConcurrentHashMap<>();
    private Session session;
    private Integer callerId;
    private Integer receiverId;

    @OnOpen
    public void open(@PathParam("userId")String userIdStr, Session session){
        String[] strs = userIdStr.split("_");
        Integer callerId = Integer.valueOf(strs[0]);
        Integer receiverId = Integer.valueOf(strs[1]);
        this.session = session;
        this.callerId = callerId;
        this.receiverId = receiverId;
        map.put(callerId,this);
    }

    @OnMessage
    public void getMessage(String json, Session session1){
        JSONObject requestJSON = JSONObject.parseObject(json);
        String msg = (String) requestJSON.get("msg");
        Integer receiverId = Integer.valueOf((String)requestJSON.get("receiverId"));
        for(Integer id:map.keySet()){
            if(id.equals(receiverId)){
                JSONObject responseJSON = new JSONObject();
                responseJSON.put("msg",msg);
                responseJSON.put("time",sim.format(new Date()));
                map.get(id).sendMessage(responseJSON.toJSONString());
                break;
            }
        }

    }

    public synchronized void sendMessage(String msg){
        this.session.getAsyncRemote().sendText(msg);
    }

    @OnClose
    public void close(){
        map.remove(this.callerId);
    }

    @OnError
    public void error(Throwable throwable){
        close();
        throwable.printStackTrace();
    }
}
