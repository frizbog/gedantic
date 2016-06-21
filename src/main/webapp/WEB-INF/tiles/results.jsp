<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">

		<c:if test="${empty results}">
			<div id="resultsPanel" class="panel panel-success">No problems
				found.</div>
		</c:if>
		<c:if test="${empty results}">
			<div id="resultsPanel" class="panel">
				<ul>
					<c:forEach items="${results}" var="result">
						<dl>
							<dt>${result.individual}</dt>
							<dd>
								<dl class="dl-horizontal">
								<dt>Fact type</dt>
								<dd>${result.factType}</dd>
								<dt>Value</dt>
								<dd>${result.value}</dd>
								<dt>Problem</dt>
								<dd>${result.problem}</dd>
								</dl>
							</dd>
						</dl>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<a href="upload.tiles"><button class="btn btn-default">
				<span class="glyphicon glyphicon-chevron-left"></span>Upload
				anotherfile
			</button></a>
		<a href="analyzeMenu.tiles"><button class="btn btn-default">
				<span class="glyphicon glyphicon-chevron-left"></span>Perform another analysis
			</button></a>

</div>