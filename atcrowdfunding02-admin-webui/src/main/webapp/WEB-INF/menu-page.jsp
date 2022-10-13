<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="ztree/zTreeStyle.css">
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/menu-page.js"></script>
<script type="text/javascript">
	$(function() {
		// 調用封裝好的生成樹形結構的函數；
		generateTree();
		// 給添加子節點按鈕綁定單擊響應函數；
		$("#treeDemo").on("click", ".addBtn", function() {
			// 將當前節點的變量作為新節點的pid儲存到全局變量中；
			window.pid = this.id;
			// 打開模態框；
			$("#menuAddModal").modal("show");
			return false;
		});
		// 給子節點的模態框中的儲存按鈕綁定單擊響應函數；
		$("#menuSaveBtn").click(function() {
			// 收集表單項中用戶輸入的數據；
			var name = $.trim($("#menuAddModal [name=name]").val());
			var url = $.trim($("#menuAddModal [name=url]").val());
			var icon = $.trim($("#menuAddModal [name=icon]:checked").val());
			// 發送AJAX請求；
			$.ajax({
				"url" : "menu/save.json",
				"type" : "POST",
				"data" : {
					"pid" : window.pid,
					"name" : name,
					"url" : url,
					"icon" : icon
				},
				"dataType" : "JSON",
				"success" : function(response) {
					var result = response.result;
					if (result == "SUCCESS") {
						layer.msg("操作成功！");
						// 刷新樹形結構；
						generateTree();
					} else if (result == "FAILED") {
						layer.msg("操作失敗！" + response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			// 關閉模態框；
			$("#menuAddModal").modal("hide");
			// 清空表單；
			$("#menuResetBtn").click();
		});
		// 給編輯節點按鈕綁定單擊響應函數；
		$("#treeDemo").on("click", ".editBtn", function() {
			// 將當前節點的id儲存到全局變量中；
			window.id = this.id;
			// 打開模態框；
			$("#menuEditModal").modal("show");
			// 獲取zTreeObj對象；
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var key = "id";
			var value = window.id;
			// 根據id屬性查詢節點對象；
			var currentNode = zTreeObj.getNodeByParam(key, value);
			// 回顯表單數據；
			$("#menuEditModal [name=name]").val(currentNode.name);
			$("#menuEditModal [name=url]").val(currentNode.url);
			$("#menuEditModal [name=icon]").val([ currentNode.icon ]);
			return false;
		});
		// 給更新模態框中的更新按鈕綁定單擊響應函數；
		$("#menuEditBtn").click(function() {
			// 收集表單數據；
			var name = $("#menuEditModal [name=name]").val();
			var url = $("#menuEditModal [name=url]").val();
			var icon = $("#menuEditModal [name=icon]:checked").val();
			// 發送AJAX請求；
			$.ajax({
				"url" : "menu/update.json",
				"type" : "POST",
				"data" : {
					"id" : window.id,
					"name" : name,
					"url" : url,
					"icon" : icon
				},
				"dataType" : "JSON",
				"success" : function(response) {
					var result = response.result;
					if (result == "SUCCESS") {
						layer.msg("操作成功！");
						// 刷新樹形結構；
						generateTree();
					} else if (result == "FAILED") {
						layer.msg("操作失敗！" + response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			// 關閉模態框；
			$("#menuEditModal").modal("hide");
		});
		// 給刪除節點按鈕綁定單擊響應函數；
		$("#treeDemo").on("click", ".removeBtn", function() {
			// 將當前節點的id儲存到全局變量中；
			window.id = this.id;
			// 打開模態框；
			$("#menuConfirmModal").modal("show");
			// 獲取zTreeObj對象；
			var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var key = "id";
			var value = window.id;
			// 根據id屬性查詢節點對象；
			var currentNode = zTreeObj.getNodeByParam(key, value);
			$("#removeNodeSpan").html("【<i class='"+currentNode.icon+"'></i>"+currentNode.name+"】");
			return false;
		});
		// 給確認模態框中的OK按鈕綁定單擊響應函數；
		$("#confirmBtn").click(function() {
			// 發送AJAX請求；
			$.ajax({
				"url" : "menu/remove.json",
				"type" : "POST",
				"data" : {
					"id" : window.id
				},
				"dataType" : "JSON",
				"success" : function(response) {
					var result = response.result;
					if (result == "SUCCESS") {
						layer.msg("操作成功！");
						// 刷新樹形結構；
						generateTree();
					} else if (result == "FAILED") {
						layer.msg("操作失敗！" + response.message);
					}
				},
				"error" : function(response) {
					layer.msg(response.status + " " + response.statusText);
				}
			});
			// 關閉模態框；
			$("#menuConfirmModal").modal("hide");
		});
	});
</script>
<body>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="glyphicon glyphicon-th-list"></i>権限一覧
						<div style="float: right; cursor: pointer;" data-toggle="modal"
							data-target="#myModal">
							<i class="glyphicon glyphicon-question-sign"></i>
						</div>
					</div>
					<div class="panel-body">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/modal-menu-add.jsp"%>
	<%@include file="/WEB-INF/modal-menu-confirm.jsp"%>
	<%@include file="/WEB-INF/modal-menu-edit.jsp"%>
</body>
</html>