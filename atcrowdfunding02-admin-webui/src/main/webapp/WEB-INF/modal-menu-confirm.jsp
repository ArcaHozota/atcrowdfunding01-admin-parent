<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="menuConfirmModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">ヤマクラウドポップアップ</h4>
			</div>
			<form>
				<div class="modal-body">
					このノド<span id="removeNodeSpan"></span>を削除するとよろしいでしょうか。
				</div>
				<div class="modal-footer">
					<button id="confirmBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-ok"></i>Okay</button>
				</div>
			</form>
		</div>
	</div>
</div>