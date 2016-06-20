<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<div class="col-med-6">
			<a class="navbar-brand" href="upload.tiles">G-Lint</a>
		</div>
		<p class="navbar-text navbar-right">
			<c:if test="${empty gedcomName}">No GEDCOM loaded</c:if>
			<c:if test="${not empty gedcomName}">${gedcomName} <span
					class="glyphicon glyphicon-info-sign" />
			</c:if>
		</p>
	</div>
</div>
</div>
