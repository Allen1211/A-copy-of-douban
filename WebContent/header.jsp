<%--
  Created by IntelliJ IDEA.
  User: 83780
  Date: 2019/9/16
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<div id="header" class="nav">
    <div id="nav-wrap">
        <div class="nav-logo">
            <img src="img/header/logo.jpg"/>
        </div>
        <div class="nav-items">
            <ul>
                <li><a href="/douban/index">首页</a></li>
                <li><a href="/douban/index">我的豆瓣</a></li>
                <li><a href="/douban/discovery">浏览发现</a></li>
            </ul>
        </div>
        <div class="nav-search">
            <form action="/douban/search" method="get" onsubmit="js">
                <input type="text" name="searchText" id="searchText"
                       placeholder="搜索你感兴趣的文章和人..."/> <input type="submit" value="搜索"
                                                             id="searchButton"/>
            </form>
        </div>
        <div class="nav-user">
            <ul>
                <li class="nav-user-doumail"><a href="/douban/doumail" rel="">豆邮</a>
                </li>
                <li class="nav-user-account" onclick="showMoreItems()"><a
                        href="" class="button-more" onclick="return false;">
                    <div id="name-account-scan">${user.user.userName}</div>
                    的账号
                </a>
                    <div class="more-items">
                        <table cellspacing="0" cellpadding="0" id="more-table">
                            <tbody>
                            <tr>
                                <td><a href="/douban/userpage">个人主页</a></td>
                            </tr>
                            <tr>
                                <td><a href="/douban/accountManage">账号管理</a></td>
                            </tr>
                            <tr>
                                <td><a href="/douban/logout.do">退出登录</a></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</div>
</html>
