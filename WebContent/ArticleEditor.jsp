<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/indexHeader.css" />
<link rel="stylesheet" type="text/css" href="css/ArticleEditor.css" />
<script src="js/jquery-3.4.0.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src="js/editor.js" type="text/javascript" charset="utf-8"></script>
<title>文章编辑器</title>
</head>
<body>
	<div id="container">
		<div id="header" class="nav">
			<div id="nav-wrap">
				<div class="nav-logo">
					<img src="img/header/logo.jpg"  />
				</div>
			</div>
		</div>
		<div id="main">
			<div id="wrapper">
				<input type="hidden" id="hidden-data" data-id="${data.articleId }" />
				<div id="edit-title">
					<input type="text" name="title" id="title" value="${data.title }"
						placeholder="文章标题" />
				</div>
				<div id="edit-button">
					<ul id="btn-ul">
						<li><span>字体大小:</span> <select name="" id="font-size-select"
							onchange="setFontSize()">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
						</select></li>
						<li>
							<button type="button" onclick="setBold()">加粗</button>
						</li>
						<li>
							<button type="button" onclick="setItalic()">斜体</button>
						</li>
						<li><span>字体:</span> <select name="" id="font-name-select"
							onchange="setFontName()">
								<option value="Times">Times</option>
								<option value="Verdana">Verdana</option>
								<option value="Helvetica ">Helvetica</option>
								<option value="Arial">Arial</option>
								<option value="Serif ">Serif</option>
								<option value="Sans-serif">Sans-serif</option>
								<option value="Monospace">Monospace</option>
								<option value="Cursive ">Cursive</option>
						</select></li>
						<li><input type="button" name="image-upload"
							id="image-upload" value="插入图片" onclick="addImage()" /> <input
							type="file" name="" id="file-upload" display="none"
							onchange="showImg(this)" /></li>
					</ul>
				</div>
				<div id="editor-wrap">
					<div id="editor" contenteditable="true">${data.text }</div>
				</div>
			</div>
			<div id="edit-submit">
				<div id="">
					<span>文章类型（多选）：</span>
					<c:forEach var="type" items="${type}">
						<input type="checkbox" name="type" id="typeSelection"
							value="${type.typeId }" />${type.typeName }
                       </c:forEach>
					<input type="hidden">
				</div>
				<c:choose>
					<c:when test="${data == null }">
						<div id="">
							<input type="button" name="submit" id="submit" value="提交文章"
								onclick="submit()" />
						</div>
					</c:when>
					<c:otherwise>
						<div id="">
							<input type="button" name="submit" id="submit" value="提交文章"
								onclick="submit()" />
						</div>
					</c:otherwise>
				</c:choose>
			</div>
			<p id="preview"></p>
		</div>
	</div>

</body>
</html>