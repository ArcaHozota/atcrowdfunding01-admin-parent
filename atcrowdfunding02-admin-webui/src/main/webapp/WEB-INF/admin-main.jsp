<%@ page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html lang="zh-TW">
<%@ include file="/WEB-INF/include-head.jsp"%>
<body>
	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<h1 class="page-header">コントロールパネル</h1>
				<div class="row placeholders">
					<security:authorize access="hasAnyRole('代表取締役社長','会長')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</security:authorize>
					<security:authorize access="hasAnyRole('本店長','代表取締役社長')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</security:authorize>
					<security:authorize access="hasAnyRole('支店長','本店長','代表取締役社長')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</security:authorize>
					<security:authorize access="hasAnyRole('支店長','本店長','代表取締役社長')">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</security:authorize>
				</div>
			</div>
		</div>
	</div>
</body>
</html>