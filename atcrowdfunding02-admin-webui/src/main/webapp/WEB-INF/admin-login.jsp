<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-TW">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="keys" content="">
<meta name="author" content="">
<base
	href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/login.css">
<script src="jQuery/jQuery-3.6.0.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" href="index.html" style="font-size: 32px;">尚籌網-創意產品眾籌平台</a>
				</div>
			</div>
		</div>
	</nav>
	<div class="container">
		<form action="security/do/login.html" method="post" class="form-signin"
			role="form">
			<h2 class="form-signin-heading">
				<i class="glyphicon glyphicon-log-in"></i>管理員登錄
			</h2>
			<p>${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
			<p>${requestScope.exception.message}</p>
			<div class="form-group has-success has-feedback">
				<input type="text" name="loginAccount" class="form-control"
					id="inputSuccess4" placeholder="請輸入賬號" autofocus> <span
					class="glyphicon glyphicon-user form-control-feedback"></span>
			</div>
			<div class="form-group has-success has-feedback">
				<input type="text" name="userPassword" class="form-control"
					id="inputSuccess4" placeholder="請輸入密碼" style="margin-top: 10px;">
				<span class="glyphicon glyphicon-lock form-control-feedback"></span>
			</div>
			<button type="submit" class="btn btn-lg btn-success btn-block">登錄</button>
		</form>
	</div>
</body>
</html>