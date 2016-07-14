<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<tiles:importAttribute name="javascripts" />
<tiles:importAttribute name="cssfiles" />

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, width=device-width" />
<c:forEach var="css" items="${cssfiles}">
	<link rel='stylesheet'
		href='<c:url value="${css}"/>'/>
	</c:forEach>

<title><tiles:getAsString name="title" /></title>
<style>
/* Icon when the collapsible content is shown */
.btn-collapse:after {
	font-family: "Glyphicons Halflings";
	content: "\e159";
	float: left;
	margin-right: 15px;
}
/* Icon when the collapsible content is hidden */
.btn-collapse.collapsed:after {
	content: "\e158";
}
</style>
</head>

<body>
	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="splash" />
	<tiles:insertAttribute name="messages" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" />

	<c:forEach var="script" items="${javascripts}">
		<script src="<c:url value="${script}"/>"></script>
	</c:forEach>

</body>
</html>