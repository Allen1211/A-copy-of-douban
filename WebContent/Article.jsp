<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/indexHeader.css" />
<link rel="stylesheet" type="text/css" href="css/article.css" />
<link rel="stylesheet" type="text/css" href="css/comment.css">
<script src="js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/nav.js" type="text/javascript" charset="utf-8"></script>
<script src="js/action.js" type="text/javascript"></script>
<script src="js/comment.js" type="text/javascript"></script>
<title>豆瓣文章</title>
</head>
<body>
	<div id="container">
		<%@ include file="header.jsp" %>
		<div id="main">
			<div id="article-wrap">
				<div id="article-header">
					<div id="title">
						<h1>${articleData.article.articleTitle }</h1>
					</div>
					<div id="user-info">
						<ul>
							<li><img
								src="/douban/getUserImage?userId=${articleData.article.userId }"
								id="user-img"> <a href="/douban/userpage?userId=${articleData.article.userId}" id="name">${articleData.nickname }</a>
							</li>
							<li><span id="date"> 发表于<fmt:formatDate type="date" value="${articleData.article.createdTime } " />&nbsp;&nbsp;&nbsp;
							最后修改于<fmt:formatDate type="date" value="${articleData.article.updateTime }"/>
							</span></li>
						</ul>
					</div>
				</div>
				<div id="article-text">
					<p>${articleData.article.articleText }</p>
				</div>
				<div id="article-footer">
					<div id="article-type">
						<ul>
							<li>文章分类：</li>
							<c:forEach var="type" items="${articleData.type }">
								<li><a
									href="/douban/discovery?typeId=${type.typeId }">${type.typeName }</a></li>
							</c:forEach>
						</ul>
					</div>
					<div id="article-action">
						<ul>
							<li><input id="like-data" type="hidden"
								data-articleId="${ articleData.article.articleId}"
								data-userid="${ user.user.userId}"> <a id="like"
								onclick="like();return false;" href=""
								data-isLiked=${articleData.isLiked }>点赞</a>(<strong
								id="likeCount">${articleData.article.articleLikes }</strong>)</li>
							<li><a id="forward"
								href="/douban/forward?articleId=${articleData.article.articleId }&userId=${user.user.userId}"
								data-isForwarded=${articleData.isForwarded }>转发</a>(<strong
								id="forwardCount">${articleData.forwardCount }</strong>)</li>
							<li><a id="collect"
								href="/douban/collect?articleId=${articleData.article.articleId }&userId=${user.user.userId}"
								data-isCollected=${articleData.isCollected }>收藏</a>(<strong
								id="collectCount">${articleData.collectCount }</strong>)</li>
						</ul>
					</div>
				</div>
				<input type="hidden" id="article-data"
					data-articleId="${articleData.article.articleId }" />
			</div>
			<hr />
			<div id="comment-wrap">
				<div>
					<span id="comment-span-1">评论</span><br />
					<textarea id="comment-editor"></textarea>
					<a href="" onclick="addComment();return false;">提交评论</a>
				</div>
				<div class="comment">
					<c:forEach var="comment" items="${commentList }">
						<div class="comment-row">
							<div  class="comment-user-img-wrap">
								<img src="/douban/getUserImage?userId=${comment.userId }"
									class="comment-user-img">
							</div>
							<div  class="comment-user-content-wrap">
								<div  class="comment-user-header">
									<ul>
										<li><a href="/douban/userpage?userId=${comment.userId}">${comment.nickname }</a></li>
										<li><span > ${comment.createdTime } </span></li>
									</ul>
								</div>
								<div  class="comment-content">
									<p>${comment.commentText }</p>
								</div>
								<div  class="commnet-action">
									<input type="hidden" id="comment-${comment.commentId }"
										class="reply-data" data-commentId="${comment.commentId }"
										data-toReplyId="${comment.commentId }"
										data-toUserId="${comment.userId }" data-replyType="1">
									<a href="" class="comment-like"
										onclick="commentLike(this);return false;"
										data-isLiked="${comment.isLiked }">赞</a> (<span
										class="comment-likeCount">${comment.likeCount }</span>) <a
										href="#" class="edit-reply-btn"
										onclick="showReplyEditor(this);return false;")>回复</a> <a
										href="" class="show-reply-btn"
										onclick="showReply(this);return false;">查看回复</a>
								</div>
							</div>
							<div  class="reply-wrap">
								<c:forEach var="reply" items="${comment.replyList }">
									<div  class="reply-row">
										<div  class="reply-user-img-wrap">
											<img
												src="/douban/getUserImage?userId=${reply.fromUserId }"
												class="reply-user-img">
										</div>
										<div  class="reply-target-wrap">
											<a href="/douban/userpage?userId=${reply.fromUserId}">${reply.fromUserNickname }</a>&nbsp;回复&nbsp;<a
												href="/douban/userpage?userId=${reply.toUserId}">${reply.toUserNickname }</a>：
										</div>
										<div  class="reply-content">
											<p>${reply.replyText }</p>
										</div>
										<div  class="reply-reply">
											<input type="hidden" id="reply-${reply.replyId }"
												class="reply-data" data-commentId="${comment.commentId }"
												data-toReplyId="${reply.replyId }"
												data-toUserId="${reply.fromUserId }" data-replyType="2">
											<a href="#" onclick="showReplyEditor(this);return false;">回复</a>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
					</c:forEach>
				</div>
				<div id="pagination" class="pagination">
					<c:choose>
						<c:when test="${commentPageBean.currentPage!=1 }">
							<a
								href="/douban/article?articleId=${articleData.article.articleId }&page=${commentPageBean.currentPage-1}">上一页</a>
						</c:when>
						<c:otherwise>
							<span class="prePage">上一页</span>
						</c:otherwise>
					</c:choose>
					<c:forEach var="i" begin="${commentPageBean.begin }"
						end="${commentPageBean.end }" step="1">
						<c:choose>
							<c:when test="${commentPageBean.currentPage==i }">
								<span class="currentPage">${i }</span>
							</c:when>
							<c:otherwise>
								<a
									href="/douban/article?articleId=${articleData.article.articleId }&page=${i }">${i }</a>
							</c:otherwise>
						</c:choose>
					</c:forEach>
					<c:choose>
						<c:when
							test="${commentPageBean.totalPage>commentPageBean.currentPage }">
							<a
								href="/douban/article?articleId=${articleData.article.articleId }&page=${commentPageBean.currentPage+1}">下一页</a>
						</c:when>
						<c:otherwise>
							<span class="nextPage">下一页</span>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
		<div id="reply-edit-wrap">
			<h2>回复</h2>
			<div id="reply-edit">
				<textarea id="reply-text" placeholder="在此输入你的回复"></textarea>
				<a href="" onclick="addReply();return false;">提交</a>
				<button type="button" onclick="hideReplyEditor();return false;">返回</button>
			</div>
			<input type="hidden" id="reply-data-edit" data-id="">
		</div>
		<div id="hidebg"></div>
	</div>

</body>
</html>