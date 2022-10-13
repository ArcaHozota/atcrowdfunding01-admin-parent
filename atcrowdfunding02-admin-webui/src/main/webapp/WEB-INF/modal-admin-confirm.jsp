<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/10/12
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="confirmAdminModal" class="modal fade" tabindex="-1"
	role="dialog">
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
				<h5>以下のアカウントを削除するとよろしいでしょうか：</h5>
				<div id="adminNameSpan" style="text-align: center;"></div>
			</div>
			<div class="modal-footer">
				<button id="removeAdminBtn" type="button" class="btn btn-warning">削除</button>
			</div>
		</div>
	</div>
</div>
