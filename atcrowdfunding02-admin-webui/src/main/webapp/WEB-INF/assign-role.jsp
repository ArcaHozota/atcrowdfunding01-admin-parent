<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#toRightBtn").click(function() {
			$("select:eq(0)>option:selected").appendTo("select:eq(1)");
		});
		$("#toLeftBtn").click(function() {
			$("select:eq(1)>option:selected").appendTo("select:eq(0)");
		});
		$("#saveRelationChangesBtn").click(function() {
			$("select:eq(1)>option").prop("selected", "selected");
		});
	});
</script>
<body>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
					<li><a href="admin/to/main/page.html">ホームページ</a></li>
					<li><a href="admin/get/page.html">データ一覧</a></li>
					<li class="active">ロールに授権をする</li>
				</ol>
				<div class="panel panel-default">
					<div class="panel-body">
						<form action="assign/do/role/assign.html" method="POST"
							role="form" class="form-inline">
							<input type="hidden" name="adminId" value="${param.adminId}" />
							<input type="hidden" name="pageNum" value="${param.pageNum}" />
							<input type="hidden" name="keyword" value="${param.keyword}" />
							<div class="form-group">
								<label for="exampleInputPassword1">授権されないロール</label><br/>
								<select class="form-control" multiple="multiple" size="10"
									style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.unassignedRoleList}"
										var="unRole">
										<option value="${unRole.id}">${unRole.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<ul>
									<li id="toRightBtn"
										class="btn btn-success glyphicon glyphicon-chevron-right"></li>
									<br/>
									<li id="toLeftBtn"
										class="btn btn-warning glyphicon glyphicon-chevron-left"
										style="margin-top: 20px;"></li>
								</ul>
							</div>
							<div class="form-group" style="margin-left: 40px;">
								<label for="exampleInputPassword1">授権されたロール</label><br> <select
									name="roleIdList" class="form-control" multiple="multiple"
									size="10" style="width: 100px; overflow-y: auto;">
									<c:forEach items="${requestScope.assignedRoleList}" var="role">
										<option value="${role.id}">${role.name}</option>
									</c:forEach>
								</select>
							</div>
							<button id="saveRelationChangesBtn" type="submit"
								style="width: 120px;" class="btn btn-sm btn-info btn-block">保存</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>