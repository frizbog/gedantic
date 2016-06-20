<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, width=device-width">
<link rel='stylesheet'
	href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<title>G-Lint</title>
<style>
/* Icon when the collapsible content is shown */
.btn-collapse:after {
	font-family: "Glyphicons Halflings";
	content: "\e114";
	float: left;
	margin-right: 15px;
}
/* Icon when the collapsible content is hidden */
.btn-collapse.collapsed:after {
	content: "\e080";
}
</style>
</head>

<body>

	<div class="navbar navbar-inverse navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="upload.jsp">G-Lint</a>
			</div>
		</div>
	</div>

	<div class="container">
		<c:if test="${not empty message}">
			<p class="${messageType}">${message}</p>
		</c:if>
		<c:if test="${not empty errors}">
			<div id="errorsPanel" class="panel panel-danger">
				<div class="panel-heading">
					<h4 class="panel-title">
						<button type="button" data-toggle="collapse" data-target="#errors"
							class="btn btn-danger collapsed btn-collapse">
							${fn:length(errors)} error(s) found.
						</button>
					</h4>
				</div>
				<div id="errors" class="panel-collapse collapse">
					<ul>
						<c:forEach var="error" items="${errors}">
							<li>${error}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty warnings}">
			<div id="warningsPanel" class="panel panel-warning">
				<div class="panel-heading">
					<h4 class="panel-title">
						<button type="button" data-toggle="collapse" data-target="#warnings"
							class="btn btn-warning collapsed btn-collapse">
							${fn:length(warnings)} Warning(s) found.
						</button>
					</h4>
				</div>
				<div id="warnings" class="panel-collapse collapse">
					<ul>
						<c:forEach var="warning" items="${warnings}">
							<li>${warning}</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</c:if>
		<p class="alert alert-success">${parseResults}</p>
	</div>


	<div class="container">
		<a href="upload.jsp"><button class="btn btn-default"><span class="glyphicon glyphicon-chevron-left"></span>Upload another file</button></a>
	</div>

	<div class="container">
		<footer class="footer navbar-fixed-bottom">
			<div class="row text-muted">
				<div class="col-sm-8">&copy; 2016 Matthew R. Harrah</div>
				<div class="col-sm-4 text-right">
					Powered by <a href="http://gedcom4j.org">gedcom4j</a>
				</div>
			</div>
		</footer>
	</div>


	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap-filestyle/1.1.2/bootstrap-filestyle.js"></script>
	</script>
	<script type="text/javascript" src="js/result.js"></script>
</body>
</html>