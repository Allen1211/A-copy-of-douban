<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/account.css" />
<script type="text/javascript" src="js/jquery-3.4.0.min.js"></script>
<script type="text/javascript" src="js/valid.js"></script>
<script type="text/javascript" src="js/changeCodeImage.js"></script>
<title>注册豆瓣</title>
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
				<span>注册豆瓣</span>
			</h2>
			<div class="account-form">
				<form id="regist-form">
					<input type="text" name="username" id="username"
						class="input-field" placeholder="用户名: 6-20位 英文字母,数字:"
						maxlength="20" /><br /> <input type="password" name="password"
						id="password" class="input-field"
						placeholder="密码 6-12位 必须包含大小写字母和数字" maxlength="12" /><br /> <input
						type="password" name="confirmPassword" id="confirmPassword"
						class="input-field" placeholder="再次输入密码" maxlength="12" /><br /> <input
						type="email" name="email" id="email" class="input-field"
						placeholder="您的邮箱" maxlength="30" /><br /> <input type="text"
						name="nickname" id="nickname" class="input-field"
						placeholder="用户昵称" maxlength="10" /><br /> <input type="text"
						name="code" id="code" class="input-field" placeholder="验证码"
						maxlength="4" /> <br />
					<ul type="none" class="hrlist" id="code-change">
						<li><img src="/douban/getCodeImage" id="codeImage" alt="验证码" />
						</li>
						<li><button type="button" id="change" onclick="changeImage()">看不清？</button></li>
					</ul>
					<input type="button" value="注册" id="regist" />
				</form>
			</div>
		</div>
	</div>
</body>
</html>