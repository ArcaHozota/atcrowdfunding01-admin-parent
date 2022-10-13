<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="confirmRoleModal" class="modal fade" tabindex="-1"
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
				<h5>以下のロールを削除するとよろしいでしょうか：</h5>
				<div id="roleNameSpan" style="text-align: center;"></div>
			</div>
			<div class="modal-footer">
				<button id="removeRoleBtn" type="button" class="btn btn-warning">削除</button>
			</div>
		</div>
	</div>
</div>