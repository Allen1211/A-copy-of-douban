<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/indexHeader.css" />
<link rel="stylesheet" type="text/css" href="css/discovery.css" />
<script src="js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
<title>浏览发现</title>
</head>
<body>
	<div id="container">
		<%@ include file="header.jsp" %>
		<div id="main">
			<div id="">
				<span id="show">文章分类</span>
			</div>
			<div id="all-type-wrap">
				<div id="type-nav">
					<ul>
						<li><a href="" class="type-btn">热门</a></li>
						<c:forEach var="type" items="${TypeList }">
							<li><a
								href="/douban/discovery?typeId=${type.typeId }"
								class="type-btn">${type.typeName }</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="article-list-wrap">
				<div class="article-list">
					<c:forEach var="data" items="${ArticleDataList }">
						<div class="article-row">
							<div class="author-info">
								<img src="/douban/getUserImage?userId=${data.authorId }"
									alt="头像" class="user-img" /> <a href="">${data.authorName }</a>
							</div>
							<div class="row-title">
								<a href="/douban/article?articleId=${data.articleId }">
									<h2>${data.title }</h2>
								</a>
							</div>
							<div class="row-text">
								<div class="text">
									<blockquote>${data.text }</blockquote>
								</div>
							</div>
							<div class="row-footer">
								<span>${data.createdTime }</span> <span>赞(${data.likeCount })</span>
								<span>转发(${data.forwardCount })</span> <span>收藏(${data.collectCount })</span>
							</div>
							<hr />
						</div>
					</c:forEach>
				</div>
				<div id="pagination" class="pagination">
					<c:choose>
						<c:when test="${pageBean.currentPage!=1 }">
							<a
								href="/douban/discovery?typeId=${param.typeId }&page=${pageBean.currentPage-1}">上一页</a>
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
								<a
									href="/douban/discovery?typeId=${param.typeId }&page=${i }">${i }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when test="${pageBean.totalPage>pageBean.currentPage }">
							<a
								href="/douban/discovery?typeId=${param.typeId }&page=${pageBean.currentPage+1}">下一页</a>
						</c:when>
						<c:otherwise>
							<span class="nextPage">下一页</span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
