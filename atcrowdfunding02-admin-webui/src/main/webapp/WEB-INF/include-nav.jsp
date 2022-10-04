<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div>
				<a class="navbar-brand" style="font-size: 32px;" href="admin/to/main/page.html">眾籌平台——控制台</a>
			</div>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li style="padding-top: 8px;">
					<div class="btn-group">
						<button type="button"
							class="btn btn-danger dropdown-toggle"
							data-toggle="dropdown">
							<i class="glyphicon glyphicon-user"></i>${sessionScope.loginAdmin.userName}
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><i class="glyphicon glyphicon-cog"></i>
									用戶設置</a></li>
							<li><a href="#"><i class="glyphicon glyphicon-comment"></i>
									消息</a></li>
							<li class="divider"></li>
							<li><a href="security/do/logout.html"><i
									class="glyphicon glyphicon-off"></i>退出登錄</a></li>
						</ul>
					</div>
				</li>
				<li style="margin-left: 10px; padding-top: 8px;">
					<button type="button" class="btn btn-primary">
						<span class="glyphicon glyphicon-question-sign"></span>幫助
					</button>
				</li>
			</ul>
			<form class="navbar-form navbar-right">
				<input type="text" class="form-control" placeholder="查詢">
			</form>
		</div>
	</div>
</nav>