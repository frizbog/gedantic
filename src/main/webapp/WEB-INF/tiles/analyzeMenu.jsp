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
<div class="container">
	<form id="analysisForm" name="analysis" action="analyze" method="post">
		<legend>Select an analysis to perform on the loaded GEDCOM file</legend>
		<input name="analyzerId" type="hidden" id="analysisKey"/>
	</form>

	<ul id="filterbar" class="nav nav-gallery">
		<li data-multifltr="all" class="active">All</li>
		<c:forEach items="${analyzerTags}" var="tag">
			<li data-multifltr="${tag.id}" data-toggle="tooltip"  title="${tag.description}">${tag.name}</li>
		</c:forEach>
	</ul>

	<div class="filtr-container row-fluid">
		<c:forEach items="${analyzers}" var="analyzer" varStatus="counter">
			<div class="filtr-item panel panel-primary" data-category="${analyzer.value.tagIds}" data-sort="value" data-analysiskey="${analyzer.key}">
				<div class="panel-heading">
					<h3 class="panel-title" data-toggle="tooltip" title="${analyzer.value.name}">${analyzer.value.name}</h3>
				</div>
				<div class="panel-body">${analyzer.value.description}</div>
			</div>
		</c:forEach>
	</div>
</div>