<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav id="header" class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-brand">
			<a href="analyzeMenu.tiles">G-Lint</a>
		</div>

		<div class="navbar-text navbar-right">
			<c:if test="${empty gedcomName || empty gedcom}">No GEDCOM loaded</c:if>
			<c:if test="${not empty gedcomName && not empty gedcom}">
				<p>
					<a href="#" data-toggle="popover" data-placement="left"
						data-content="${numIndividuals} individuals, ${numFamilies} famil(ies)">
						<button class="btn btn-default btn-lg"
							title="Click for information about the uplaoded file">
							<span class="glyphicon glyphicon-info-sign"></span>
							${gedcomName}
						</button>
					</a> <a href="upload.tiles">
						<button class="btn btn-default btn-lg"
							title="Click to load a different GEDCOM file for analysis">
							<span class="glyphicon glyphicon-upload"></span> Upload another
							file
						</button>
					</a>
				</p>
			</c:if>
		</div>
	</div>
</nav>
