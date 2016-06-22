<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav id="header" class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-brand">
		<a href="analyzeMenu.tiles">G-Lint</a>
		</div>
		
			
		
		<p class="navbar-text navbar-right">
			<c:if test="${empty gedcomName || empty gedcom}">No GEDCOM loaded</c:if>
			<c:if test="${not empty gedcomName && not empty gedcom}">
				<a href="#" title="${gedcomName}" data-toggle="popover"
					data-placement="left"
					data-content="${numIndividuals} individuals, ${numFamilies} famil(ies)">
					<span class="glyphicon glyphicon-info-sign" />
				</a>
			${gedcomName} 
			</c:if>
		</p>
	</div>
</nav>
