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
<script type="text/javascript" src="jQuery/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("button").click(function() {
			window.history.back();
		});
	});
</script>
<style>
</style>
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
	<div class="container" style="text-align: center;">
		<h2 class="form-signin-heading">
			<i class="glyphicon glyphicon-log-in"></i>尚籌網系統消息
		</h2>

		<h3>${requestScope.exception.message}</h3>

		<button style="width: 150px; margin: 50px auto 0px auto;"
			class="btn btn-lg btn-warning btn-block">點我返回上一步</button>
	</div>
</body>
</html>