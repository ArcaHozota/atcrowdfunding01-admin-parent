<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/10/12
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="editAdminModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">ヤマクラウドポップアップ</h4>
			</div>
			<div class="modal-body">
				<form class="form-group" role="form">
					<div class="form-group has-success has-feedback">
						<input type="text" name="loginAccount" class="form-control"
							id="inputSuccess1" placeholder="ログインアカウント" autofocus>
					</div>
					<div class="form-group has-success has-feedback">
						<input type="text" name="userName" class="form-control"
							id="inputSuccess2" placeholder="ユーザー名" autofocus>
					</div>
					<div class="form-group has-success has-feedback">
						<input type="text" name="email" class="form-control"
							id="inputSuccess3" placeholder="メールアドレス" autofocus>
						<p class="help-block label label-warning">格式xxx@xxxx.comに合わせるメールアドレスを入力してください。</p>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="updateAdminBtn" type="button" class="btn btn-success">更新</button>
			</div>
		</div>
	</div>
</div>
