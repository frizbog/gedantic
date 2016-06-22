<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="container">
	<h1>${analysisName}<br /> <small>${analysisDescription}</small>
	</h1>
	<c:if test="${empty results}">
		<div id="resultsPanel" class="panel panel-success">No
			individuals match the analysis criteria.</div>
	</c:if>
	<c:if test="${not empty results}">
		<nav class="navbar">
			<a href="analyzeMenu.tiles"><button class="btn btn-default">
					<span class="glyphicon glyphicon-chevron-left"></span>Perform
					another analysis
				</button></a>
		</nav>

		<div id="resultsPanel" class="panel panel-primary">
			<div class="panel-heading">${fn:length(results)} finding(s).</div>
			<div class="panel-body">
				<c:forEach items="${results}" var="result">
					<dl>
						<dt>${result.individual}</dt>
						<dd>
							<dl class="dl-horizontal">
								<c:if test="${not empty result.factType}">
									<dt>Fact type</dt>
									<dd>${result.factType}</dd>
								</c:if>
								<c:if test="${not empty result.value}">
									<dt>Value</dt>
									<dd>${result.value}</dd>
								</c:if>
								<c:if test="${not empty result.problem}">
									<dt>Problem</dt>
									<dd>${result.problem}</dd>
								</c:if>
							</dl>
						</dd>
					</dl>
				</c:forEach>
			</div>
		</div>
	</c:if>

	<nav class="navbar">
		<a href="analyzeMenu.tiles"><button class="btn btn-default">
				<span class="glyphicon glyphicon-chevron-left"></span>Perform
				another analysis
			</button></a>
	</nav>
</div>