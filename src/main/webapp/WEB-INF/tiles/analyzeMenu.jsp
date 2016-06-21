<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<form name="analysis" action="analyze">
		<legend>Select an analysis to perform on the loaded GEDCOM
			file</legend>

		<c:forEach items="${analyzers}" var="analyzer" varStatus="counter">
			<div class="radio">
				<label> 
					<c:if test="${counter.index == 0}">
						<input type="radio" name="analysisRadios" checked="checked"
							id="optionsRadios${counter.index}" value="${analyzer.value.id}" /><strong>${analyzer.value.name}</strong> - ${analyzer.value.description}
					</c:if>
					<c:if test="${counter.index != 0}">
						<input type="radio" name="analysisRadios"
							id="optionsRadios${counter.index}" value="${analyzer.value.id}" /><strong>${analyzer.value.name}</strong> - ${analyzer.value.description}
					</c:if>
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