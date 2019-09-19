<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: 83780
  Date: 2019/9/16
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/jquery-3.4.0.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/editjs.js" type="text/javascript"></script>
    <script src="js/userpage.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="css/indexHeader.css"/>
    <link rel="stylesheet" type="text/css" href="css/userpage.css"/>
    <title>豆瓣</title>
</head>
<body>
<div id="container">
    <div id="main">
        <%@ include file="header.jsp" %>
        <div id="content" class="content">

        </div>
        <div id="" class="aside">
            <div class="user-info-wrap" >
                <div id="user-info" class="user-info">
                    <div id="info-image">
                        <img src="/douban/getUserImage?userId=${pageUser.userId}" alt="头像" />
                    </div>
                    <div id="info-text">
                        <ul type="none">
                            <li>
                                <span>昵称: </span><span>${pageUser.nickname}</span>
                            </li>
                            <li><span><fmt:formatDate type="date" value="${pageUser.userRegistTime }"/> 加入</span></li>
                        </ul>

                    </div>
                </div>
                <c:choose>
                    <c:when test="${isMyPage==true}">
                        <div id="edit-user">
                            <a href="#" onclick="show();return false;">编辑信息</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="follow-user">
                            <button id="follow" class="btn" onclick="follow();return false;" data-is-followed="${isMyFriend}">关注</button>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div id="user-desc" class="user-desc">
                    <div id="desc" class="desc">
                        <span>个人简介：</span><br />
                        <blockquote>${pageUser.userDesc}</blockquote><br>
                    </div>
                </div>
            </div>
            <div id="collect-wrap" class="collect-wrap">
                <div id="collect" class="my-collect">
                    <c:choose>
                        <c:when test="${isMyPage==true}">
                            <h2>${user.user.userName}的关注&nbsp;&nbsp;&nbsp;-------------------</h2>
                        </c:when>
                        <c:otherwise>
                            <h2>关注Ta的人&nbsp;&nbsp;&nbsp;-------------------</h2>
                        </c:otherwise>
                    </c:choose>
                    <ul>
                        <c:forEach var="i" begin="${1}" end="${size}" step="${1}">
                            <li>
                                <a href="/douban/userpage?userId=${friendIdList[i-1].userId}"><img src="/douban/getUserImage?userId=${friendIdList[i-1].userId}" class="friend-img" ></a>
                                <a href=""><span class="friend-name">${friendIdList[i-1].nickname}</span></a>
                            </li>
                            </br>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    </div>
</div>
</body>
</html>
