<%--

    Copyright (c) 2016 Matthew R. Harrah

    MIT License

    Permission is hereby granted, free of charge, to any person
    obtaining a copy of this software and associated documentation
    files (the "Software"), to deal in the Software without
    restriction, including without limitation the rights to use,
    copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the
    Software is furnished to do so, subject to the following
    conditions:

    The above copyright notice and this permission notice shall be
    included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
    OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
    OTHER DEALINGS IN THE SOFTWARE.

--%>
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
<meta name="description" content="Analyze your GEDCOM file. Find inconsistencies, missing data to go look for, things to clean up, etc. by uploading your genealogy data and performing an analysis." />
<meta name="keywords" content="gedcom, genealogy, analysis, analyze, gedcom4j" />
<meta name="author" content="Matthew R. Harrah">
<meta name="robots" content="index, follow">
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