<%--
  Created by IntelliJ IDEA.
  User: 83780
  Date: 2019/9/20
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery-3.4.0.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/chat.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="css/indexHeader.css"/>
    <link rel="stylesheet" type="text/css" href="css/chat.css"/>
    <title>Title</title>
</head>
<body>
    <%@ include file="header.jsp" %>
    <input type="hidden" id="user-id" data-caller-id="${user.user.userId}" data-receiver-id="${receiver.userId}" />
    <div id="main">
        <div id="chat-wrap">
            <div id="head-box">
                <ul id="title-ul">
                    <li><span id="title">与${receiver.nickname}的聊天</span></li>
                    <li><button id="quit-btn" type="button" onclick="quit()">结束聊天</button></li>
                </ul>
                <hr/>
            </div>
            <div id="message-box">
                <ul id="message-ul">

                </ul>
            </div>
            <div id="output-box">
                <div id="edit-area" contenteditable="true"></div>
                <button id="send-btn" onclick="send();return false;">发送</button>
            </div>
        </div>
    </div>
</body>
</html>
