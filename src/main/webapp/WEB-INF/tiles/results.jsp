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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<div class="container">
	<h1>${analysisName}<br /> <small>${analysisDescription}</small>
	</h1>
	<c:if test="${empty results}">
		<div id="resultsPanel" class="alert alert-success">No <tiles:getAsString name="whatsInResults" /> match the analysis criteria.</div>
	</c:if>
	<c:if test="${not empty results}">
		<nav class="navbar">
			<a href="analyzeMenu.tiles"><button class="btn btn-primary">
					<span class="glyphicon glyphicon-chevron-left"></span>Perform another analysis
				</button></a>
		</nav>

		<div id="resultsPanel" class="panel panel-primary">
			<div class="panel-heading">${fn:length(results)} finding(s).</div>
			<div class="panel-body">
				<ol class="list-group">
					<c:forEach items="${results}" var="r">
						<c:set var="result" value="${r}" scope="request" />
						<tiles:insertAttribute name="resultListItem" />
					</c:forEach>
				</ol>
			</div>
		</div>
	</c:if>

	<nav class="navbar">
		<a href="analyzeMenu.tiles"><button class="btn btn-primary">
				<span class="glyphicon glyphicon-chevron-left"></span>Perform another analysis
			</button></a>
	</nav>
</div>