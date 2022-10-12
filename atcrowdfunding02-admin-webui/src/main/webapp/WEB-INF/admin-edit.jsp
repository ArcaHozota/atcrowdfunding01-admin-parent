<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp"%>
<body>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="admin/to/main/page.html">首頁</a></li>
					<li><a href="admin/get/page.html">數據列表</a></li>
					<li class="active">更新</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-heading">
						表單數據
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<form action="admin/update.html" method="post" role="form">
							<input type="hidden" name="id" value="${requestScope.admin.id}">
							<input type="hidden" name="pageNum" value="${param.pageNum}">
							<input type="hidden" name="keyword" value="${param.keyword}">
							<p>${requestScope.exception.message}</p>
							<div class="form-group">
								<label for="exampleInputPassword1">登錄賬號</label> <input
									name="loginAccount" value="${requestScope.admin.loginAccount}"
									type="text" class="form-control" id="exampleInputPassword1"
									placeholder="請輸入賬號">
							</div>
							<div class="form-group">
								<label for="exampleInputPassword1">用戶名</label> <input
									name="userName" value="${requestScope.admin.userName}"
									type="text" class="form-control" id="exampleInputPassword2"
									placeholder="請輸入用戶名">
							</div>
							<div class="form-group">
								<label for="exampleInputEmail1">郵箱地址</label> <input type="email"
									name="email" value="${requestScope.admin.email}"
									class="form-control" id="exampleInputEmail1"
									placeholder="請輸入電郵">
								<p class="help-block label label-warning">請輸入合法的郵箱地址, 格式為：
									xxxx@xxxx.com</p>
							</div>
							<button type="submit" class="btn btn-success">
								<i class="glyphicon glyphicon-edit"></i> 更新
							</button>
							<button type="reset" class="btn btn-danger">
								<i class="glyphicon glyphicon-refresh"></i> 重置
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>