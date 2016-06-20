<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, width=device-width">
<link rel='stylesheet'
	href='webjars/bootstrap/3.2.0/css/bootstrap.min.css'>
<title><tiles:getAsString name="title"/></title>
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
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="splash" />
	<tiles:insertAttribute name="messages" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />

	<script type="text/javascript" src="webjars/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="webjars/bootstrap-filestyle/1.1.2/bootstrap-filestyle.js"></script>
		
	<script type="text/javascript" src="js/uploadForm.js"></script>
</body>
</html>