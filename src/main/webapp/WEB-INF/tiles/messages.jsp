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
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div id="messages" class="container">
	<c:if test="${not empty message}">
		<p class="${messageType}">${message}</p>
	</c:if>
	<c:if test="${not empty errors}">
		<div id="errorsPanel" class="panel panel-danger">
			<div class="panel-heading">
				<h4 class="panel-title">
					<button type="button" data-toggle="collapse" data-target="#errors"
						class="btn btn-danger collapsed btn-collapse">
						${fn:length(errors)} error(s) found in uploaded file.</button>
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
					<button type="button" data-toggle="collapse"
						data-target="#warnings"
						class="btn btn-warning collapsed btn-collapse">
						${fn:length(warnings)} warning(s) found in uploaded file.</button>
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
</div>
