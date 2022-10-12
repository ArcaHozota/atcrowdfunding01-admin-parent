<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jQuery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/admin-page.js"></script>
<script type="text/javascript">
    $(function () {
        // 1.為分頁操作準備初始化數據；
        window.pageNum = 1;
        window.pageSize = 7;
        window.keyword = "";
        // 2.調用分頁函數；
        generateAdminPages();
        // 3.給查詢按鈕綁定單擊響應函數；
        $("#searchBtn").click(function() {
            //獲取輸入的屬性值賦予全局變量；
            window.keyword = $("#keywordInput").val();
            //調用分頁函數；
            generateAdminPages();
        });
        // 4.給頁面上的鉛筆圖標綁定單擊響應函數；
        $("#adminPageBody").on("click", ".pencilBtn", function() {
            //打開模態框；
            $("#editAdminModal").modal("show");
            //獲取表格中當前行的角色名稱；
            var adminName = $(this).parent().prev().text();
            //獲取當前角色的ID值；
            window.roleId = this.id;
            //使用roleName的值來設置模態框中的文本框；
            $("#editAdminModal [name=roleName]").val(adminName);
        });
        // 7.給更新模態框中的更新按鈕綁定單擊響應函數；
        $("#updateRoleBtn").click(function() {
            //從文本框獲取新的角色名稱；
            var roleName = $("#editRoleModal [name=roleName]").val();
            $.ajax({
                "url" : "role/update.json",
                "type" : "POST",
                "data" : {
                    "id" : window.roleId,
                    "name" : roleName
                },
                "dataType" : "json",
                "success" : function(response) {
                    var result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("操作成功！");
                        //重新加載分頁；
                        generatePage();
                    } else if (result === "FAILED") {
                        layer.msg("操作失敗！" + response.message);
                    }
                },
                "error" : function(response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            //關閉模態框；
            $("#editRoleModal").modal("hide");
        });
        // 8.給模態框中的刪除按鈕綁定單擊事件；
        $("#removeAdminBtn").click(function () {
            let requestBody = JSON.stringify(window.adminIdArray);
            $.ajax({
                "url": "admin/remove/by/role/id/array.json",
                "type": "POST",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "JSON",
                "success": function (response) {
                    var result = response.result;
                    if (result === "SUCCESS") {
                        layer.msg("Succeeded!");
                        //重新加載分頁；
                        generatePage();
                        //取消全選框的選中狀態；
                        $("#summaryBox").prop("checked", false);
                    } else if (result === "FAILED") {
                        layer.msg("Failed！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });
            //關閉模態框；
            $("#confirmAdminModal").modal("hide");
        });
        // 9.給單個刪除按鈕綁定單擊響應函數；
        $("#adminPageBody").on("click", ".removeBtn", function () {
            //獲取表格中當前行的角色名稱；
            var adminName = $(this).parent().prev().text();
            //創建admin對象存入數組；
            var adminArray = [{
                id: this.id,
                name: adminName
            }];
            //打開模態框；
            showConfirmModal(adminArray);
        });
        // 10.給全選框綁定單擊響應函數；
        $("#summaryBox").click(function () {
            //獲取當前全選框自身狀態；
            var currentStatus = this.checked;
            //用當前狀態設置其他的選擇框；
            $(".itemBox").prop("checked", currentStatus);
        });
        // 11.全選，全不選的反向操作；
        $("#adminPageBody").on("click", ".itemBox", function () {
            //獲取當前已被選中的checkbox的數量；
            var checkedBoxCount = $(".itemBox:checked").length;
            //獲取全部checkBox的數量；
            var totalBoxCount = $(".itemBox").length;
            //使用兩者的比較結果設置全選框的狀態；
            $("#summaryBox").prop("checked", checkedBoxCount === totalBoxCount);
        });
        // 12.給批量刪除按鈕綁定單擊響應函數；
        $("#batchRemoveBtn").click(function () {
            //定義role對象數組；
            var adminArray = [];
            //遍歷當前選中的多選框；
            $(".itemBox:checked").each(function () {
                //使用this來引用當前遍歷得到的多選框；
                var adminId = this.id;
                //通過DOM操作來獲取角色的名稱；
                var adminName = $(this).parent().next().text();
                roleArray.push({
                    id: adminId,
                    name: adminName
                });
            });
            //檢查roleArray的長度是否為零；
            if (adminArray.length === 0) {
                layer.msg("請至少選擇一個賬號！");
                return null;
            } else {
                //調用函數，打開確認模態框；
                showConfirmModal(adminArray);
            }
        });
        // // 13.給權限分配按鈕綁定單擊響應函數；
        // $("#rolePageBody").on("click", ".checkBtn", function() {
        //     //把當前id存入全局變量；
        //     window.roleId = this.id;
        //     //打開模態框；
        //     $("#assignRoleModal").modal("show");
        //     //在模態框中加載權限的樹形結構；
        //     fillAuthTree();
        // });
        // // 14.給權限分配按鈕綁定單擊響應函數；
        // $("#assignRoleBtn").click(function() {
        //     //聲明一個數組，用來存放authId；
        //     var authIdArray = [];
        //     //獲取zTreeObj對象；
        //     var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
        //     //獲取全部被勾選的節點；
        //     var checkedNodes = zTreeObj.getCheckedNodes();
        //     //遍歷checkedNodes；
        //     for (var i = 0; i < checkedNodes.length; i++) {
        //         var checkedNode = checkedNodes[i];
        //         var authId = checkedNode.id;
        //         authIdArray.push(authId);
        //     }
        //     //發送請求執行分配；
        //     var requestBody = {
        //         "authIdArray" : authIdArray,
        //         "roleId" : [ window.roleId ]
        //     };
        //     requestBody = JSON.stringify(requestBody);
        //     $.ajax({
        //         "url" : "assign/do/role/assign/auth.json",
        //         "type" : "POST",
        //         "data" : requestBody,
        //         "contentType" : "application/json;charset=UTF-8",
        //         "dataType" : "JSON",
        //         "success" : function(response) {
        //             var result = response.result;
        //             if (result == "SUCCESS") {
        //                 layer.msg("操作成功！");
        //             } else {
        //                 layer.msg("操作失敗！" + response.message);
        //             }
        //         },
        //         "error" : function(response) {
        //             layer.msg(response.status + " " + response.statusText);
        //         }
        //     });
        //     //關閉模態框；
        //     $("#assignRoleModal").modal("hide");
        // });
    });
</script>
<body>
<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 數據列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page.html" method="post"
                          class="form-inline" role="form" style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">檢索條件</div>
                                <input name="keyword" class="form-control has-success"
                                       type="text" placeholder="請輸入檢索條件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-info">
                            <i class="glyphicon glyphicon-search"></i> 檢索
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 刪除
                    </button>
                    <a style="float: right;" href="admin/to/add/page.html"
                       class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>新增</a>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>賬號</th>
                                <th>名稱</th>
                                <th>郵箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="adminPageBody"></tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/modal-admin-confirm.jsp" %>
</body>
</html>