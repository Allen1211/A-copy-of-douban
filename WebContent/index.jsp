<%@ page language="java" contentType="text/html;charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <script src="js/jquery-3.4.0.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
    <script src="js/editjs.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="css/indexHeader.css"/>
    <link rel="stylesheet" type="text/css" href="css/index-main.css"/>
    <title>豆瓣</title>
</head>
<body>
<div id="container">
	<%@ include file="header.jsp" %>
    <div id="main" class="main">
        <div id="content" class="content">
            <div class="fri-mine-wrap">
                <div class="fri-mine">
                    <ul>
                        <li><a href="">好友动态</a></li>
                        <li><a href="/douban/Editor?mode=NEW">写文章</a></li>
                        <li><a href="/douban/myArticle">我的文章</a></li>
                    </ul>
                </div>
            </div>
            <div id="artitle-pre" class="artitle-pre">
                <!-- loop-->
                <c:forEach var="data" items="${preData }">
                    <div class="pre-wrap">
                        <div class="pre" data-uid="${data.userId }"
                             data-aid="${data.articleId }" data-type="${data.type }">
                            <!-- 用户信息 -->
                            <div class="pre-user">
                                <div class="pre-user-img">
                                    <img src="/douban/getUserImage?userId=${data.userId }"
                                         alt="头像"/>
                                </div>
                                <div class="pre-user-nameNdo">
                                    <a href="/douban/userpage?userId=${data.userId }">${data.nickname }</a>
                                    <c:choose>
                                        <c:when test="${data.type==1}">
                                            <span> 发表文章：</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span> 转发：</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- if 发表  -->
                            <c:choose>
                                <c:when test="${data.type == 1 }">
                                    <div class="pre-article">
                                        <a href="/douban/article?articleId=${data.articleId }">
                                            <h3>${data.title}</h3></a>

                                        <div class="pre-text">
                                            <blockquote>
                                                <p class="pre-p">${data.text}</p>
                                            </blockquote>
                                        </div>

                                        <div class="pre-img">
                                            <img/> <img/>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="pre-forw-artitle">
                                        <blockquote>
                                            <div class="pre-forw-user">
                                                <div class="pre-user-nameNdo">
                                                    <a href="/douban/user?userId=?">${data.authorName }</a>
                                                    发表文章
                                                </div>
                                            </div>
                                            <div class="pre-forw-text">
                                                <a
                                                        href="/douban/article?articleId=${data.articleId }">
                                                    <h3>${data.title}</h3></a>

                                                <div class="pre-text">
                                                    <blockquote>
                                                        <p class="pre-p">${data.text }</p>
                                                    </blockquote>
                                                </div>

                                                <div class="pre-img">
                                                    <img/> <img/>
                                                </div>

                                            </div>
                                        </blockquote>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            <!-- 发表时间 点赞 转发 -->
                            <br/>
                            <div class="pre-action">
									<span class="created" title=""> <a href="article url">${data.createdTime}</a>
									</span>

                                <!--                                     <a href="like">赞</a> -->
                                    <%--                                     <span class="like-count" data-likes="5">(${data.likes })</span> --%>

                                <!--                                     <a href="forward">转发</a> -->
                                <!--                                     <span id="" class="forward-count" data-forward = "2">(9)</span> -->
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <br/>
                </c:forEach>
                <!-- end loop -->
                <div id="pagination" class="pagination">
                    <c:choose>
                        <c:when test="${pageBean.currentPage!=1 }">
                            <a href="?page=${pageBean.currentPage-1}">上一页</a>
                        </c:when>
                        <c:otherwise>
                            <span class="prePage">上一页</span>
                        </c:otherwise>
                    </c:choose>
                    <c:forEach var="i" begin="${pageBean.begin }"
                               end="${pageBean.end }" step="1">
                        <c:choose>
                            <c:when test="${pageBean.currentPage==i }">
                                <span class="currentPage">${i }</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i }">${i }</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:choose>
                        <c:when test="${pageBean.totalPage>pageBean.currentPage }">
                            <a href="?page=${pageBean.currentPage+1}">下一页</a>
                        </c:when>
                        <c:otherwise>
                            <span class="nextPage">下一页</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div id="aside" class="aside">
            <div id="user-info" class="user-info">
                <div id="info-image">
                    <img src="/douban/getUserImage?userId=${user.user.userId }"
                         alt="头像" id="user-head-img"/>
                </div>
                <div id="info-text">
                    <ul type="none">
                        <li><span>昵称: </span>${user.user.nickname }</li>
                        <li><span><fmt:formatDate type="date" value="${user.user.userRegistTime }"/> 加入</span></li>
                    </ul>
                </div>
            </div>
            <div id="edit-user">
                <a href="#" onclick="show();return false;">编辑信息</a>
            </div>
            <div id="user-desc" class="user-desc">
                <div id="desc" class="desc">
                    <span>个人简介：</span><br/>
                    <blockquote>${user.user.userDesc }</blockquote>
                    <br>
                </div>
            </div>
            <div id="collect-wrap" class="collect-wrap">
                <div id="collect" class="my-collect">
                    <h2>我的收藏&nbsp;&nbsp;&nbsp;--------------------</h2>
                    <a href="">查看所有</a>
                    <ul>
                        <li><a href=""> <span class="collect-title">
										11111111111111111111 </span>
                        </a> <br/> <span>4月27日 20:23</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原作者：<span>哲学家</span>
                        </li>
                        <hr>
                        <li><a href=""> <span class="collect-title">
										111111111111111 </span>
                        </a> <br/> <span>4月27日 20:23</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原作者：<span>哲学家</span>
                        </li>
                        <hr>
                        <li><a href=""> <span class="collect-title">
										1111111111111111111 </span>
                        </a> <br/> <span>4月27日 20:23</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原作者：<span>哲学家</span>
                        </li>
                        <hr>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="info-edit-wrap" class="info-edit-wrap">
    <div id="info-edit" class="info-edit">
        <form id="form" enctype="multipart/form-data">
            <div class="edit-form-row">
                <label for="file-upload" class="edit-desc-label">头像：</label>
                <div id="edit-user-img">
                    <img src="/douban/getUserImage?userId=${user.user.userId }"
                         id="head-img"/> <input type="file" name="" id="file-upload"
                                                value="更换头像" onchange="showImg(this)" accept="image/jpeg"/> <input
                        type="hidden" id="is-file-chosen" value="false"/>
                </div>
            </div>
            <div class="edit-form-row">
                <label class="edit-desc-label">昵称：</label>
                <div class="edit-text-field">
                    <input type="text" name="nickname" id="edit-nickname"
                           value="${user.user.nickname }" placeholder="用户昵称" maxlength="10"/>
                </div>
            </div>
            <div class="edit-form-row">
                <label class="edit-desc-label">密码：</label> <a href="">修改密码</a>
            </div>
            <div class="edit-form-row">
                <label class="edit-desc-label">邮箱：</label>
                <div class="edit-text-field">
                    <input type="text" name="email" id="edit-email"
                           value="${user.user.userEmail }" placeholder="用户邮箱" maxlength="30"/>
                </div>
            </div>
            <div class="edit-form-row">
                <label class="edit-desc-label">简介：</label>
                <div class="edit-text-field">
						<textarea rows="5" cols="20" id="edit-desc"
                                  value="${user.user.userDesc }" maxlength="100">${user.user.userDesc }</textarea>
                </div>
            </div>
            <div class="edit-form-row">
                <input type="button" name="" id="edit-submit" onclick="save()"
                       value="保存修改"/>
            </div>
        </form>
    </div>
</div>
<div id="hidebg"></div>
</body>
</html>
