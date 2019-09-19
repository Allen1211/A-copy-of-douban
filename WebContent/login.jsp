<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="js/jquery-3.4.0.min.js"></script>
<script type="text/javascript" src="js/valid.js"></script>
<script type="text/javascript" src="js/changeCodeImage.js"></script>
<link rel="stylesheet" type="text/css" href="css/account.css" />
<title>登陆豆瓣</title>
</head>
<body>
	<div id="main">
		<div id="nav-wrap" class="nav">
			<div class="header-nav">
				<a href="/douban/login.jsp"> <img src="img/login/logo.jpg"
					id="logo" alt="豆瓣">
				</a>
			</div>
		</div>
		<div id="left-wrap" class="plane">
			<img src="img/login/left.jpg" id="left" alt="豆瓣">
		</div>
		<div id="login-wrap" class="plane">
			<h2 id="login-text">
				<span>登录豆瓣</span>
			</h2>
			<div class="account-form">
				<form id="login-form" method="post">
					<input type="text" name="username" id="username"
						class="input-field" placeholder="用户名" maxlength="20" /><br /> 
						<input type="password" name="password" id="password" class="input-field" placeholder="密码" maxlength="12" />&nbsp;&nbsp;
						<a href="douban/findBackPassword.do">找回密码</a> <br /> 
						<input type="text" name="code" id="code" class="input-field" placeholder="验证码" maxlength="4" /> <br />
						<label><input id="auto-login" name="autoLogin" type="radio" value="1" />24小时内自动登录 </label> 
					<ul type="none" class="hrlist" id="code-change">
						<li><img src="/douban/getCodeImage" id="codeImage" alt="验证码" />
						</li>
						<li><button type="button" id="change" onclick="changeImage()">看不清？</button></li>
					</ul>
					<ul type="none" class="hrlist" id="submit-rigist">
						<li><input type="button" value="登录" id="submit" /></li>
						<li><a href="regist.jsp">没有账号？前往注册</a></li>
					</ul>
				</form>
			</div>
		</div>
	</div>
</body>
</html>