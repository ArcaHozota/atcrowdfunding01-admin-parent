<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/10/12
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div id="saveAdminModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">ヤマクラウド系統彈窗</h4>
            </div>
            <div class="modal-body">
                <form class="form-group" role="form">
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="loginAccount" class="form-control"
                               id="inputSuccess1" placeholder="請輸入賬號" autofocus>
                    </div>
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="userName" class="form-control"
                               id="inputSuccess2" placeholder="請輸入用戶名稱" autofocus>
                    </div>
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="userPassword" class="form-control"
                               id="inputSuccess3" placeholder="請輸入密碼" autofocus>
                    </div>
                    <div class="form-group has-success has-feedback">
                        <input type="text" name="email" class="form-control"
                               id="inputSuccess4" placeholder="請輸入郵箱" autofocus>
                        <p class="help-block label label-warning">請輸入合法的郵箱地址, 格式為：xxx@xxxx.com</p>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button id="saveAdminBtn" type="button" class="btn btn-success">儲存</button>
            </div>
        </div>
    </div>
</div>