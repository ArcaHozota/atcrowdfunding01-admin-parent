<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jQuery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        initPagination();
    });

    //1.生成導航條；
    function initPagination() {
        //1.獲取總記錄數；
        var totalRecord = ${requestScope.pageInfo.total};
        //2.聲明一個JSON對象；
        var properties = {
            num_edge_entries: 1,
            num_display_entries: 3,
            callback: pageSelectCallBack,
            items_per_page: ${requestScope.pageInfo.pageSize},
            current_page: ${requestScope.pageInfo.pageNum -1}
        };
        //3.生成頁碼導航條；
        $("#Pagination").pagination(totalRecord, properties);
    }

    //2.實現頁面跳轉；
    function pageSelectCallBack(pageIndex, jQuery) {
        //1.根據pageIndex計算得到pageNum；
        var pageNum = pageIndex + 1;
        //2.跳轉頁面；
        window.location.href = "admin/get/page.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
        //3.取消超鏈接的默認行為；
        return false;
    }
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
                    <button type="button" class="btn btn-danger"
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
                                <th width="30"><input type="checkbox"></th>
                                <th>賬號</th>
                                <th>名稱</th>
                                <th>郵箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center">抱歉，沒有相應的結果。</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin"
                                           varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAccount}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <a href="assign/to/assignment/role/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"
                                               class="btn btn-success btn-xs"><i class="glyphicon glyphicon-check"></i>
                                            </a>
                                            <a href="admin/to/edit/page.html?adminId=${admin.id}&pageNum=${requestScope.pageInfo.pageNum}&keyword=${param.keyword}"
                                               class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i>
                                            </a>
                                            <a href="admin/remove/${admin.id}/${requestScope.pageInfo.pageNum}/${param.keyword}.html"
                                               class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i>
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
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
</body>
</html>