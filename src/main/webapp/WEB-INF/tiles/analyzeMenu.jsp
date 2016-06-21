<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="analyses" value="<%= org.g_lint.analyzer.AnalyzerList.instance %>" />
<div class="container">
	<form name="analysis" action="analyze">
		<legend>Select an analysis to perform on the loaded GEDCOM
			file</legend>

		<c:forEach items="analyses" var="analysis" varStatus="counter">
			<div class="radio">
				<label> <input type="radio" name="analysisRadios"
					id="optionsRadios${counter}" value="analysis.class.name" />${analysis.name}
					- ${analysis.description}
				</label>
			</div>
		</c:forEach>

		<a href="upload.tiles"><button class="btn btn-default">
				<span class="glyphicon glyphicon-chevron-left"></span>Upload
				anotherfile
			</button></a>
		<button type="submit" id="submit" class="btn btn-primary">
			Analyze<span class="glyphicon glyphicon-chevron-right"></span>
		</button>
	</form>

</div>