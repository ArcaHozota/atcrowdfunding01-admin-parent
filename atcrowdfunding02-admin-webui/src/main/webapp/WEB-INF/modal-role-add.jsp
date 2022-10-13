<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="addRoleModal" class="modal fade" tabindex="-1" role="dialog">
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
						<input 
							type="text" name="roleName" class="form-control"
							id="inputSuccess4" placeholder="ロール名を入力してください。" autofocus>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button id="saveRoleBtn" type="button" class="btn btn-primary">保存</button>
			</div>
		</div>
	</div>
</div>