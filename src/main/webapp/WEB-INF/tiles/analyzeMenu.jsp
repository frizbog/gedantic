<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
	<form name="analysis" action="analyze" method="post">
		<legend>Select an analysis to perform on the loaded GEDCOM
			file</legend>

		<c:forEach items="${analyzers}" var="analyzer" varStatus="counter">
			<div class="radio">
				<label> 
					<c:if test="${counter.index == 0}">
						<input type="radio" name="analyzerId" checked="checked"
							id="optionsRadios${counter.index}" value="${analyzer.key}" /><strong>${analyzer.value.name}</strong> - ${analyzer.value.description}
					</c:if>
					<c:if test="${counter.index != 0}">
						<input type="radio" name="analyzerId"
							id="optionsRadios${counter.index}" value="${analyzer.key}" /><strong>${analyzer.value.name}</strong> - ${analyzer.value.description}
					</c:if>
				</label>
			</div>
		</c:forEach>

		<a href="upload.tiles"><button class="btn btn-default">
				<span class="glyphicon glyphicon-chevron-left"></span>Upload
				another file
			</button></a>
			
		<button type="submit" id="submit" class="btn btn-primary">
			Analyze<span class="glyphicon glyphicon-chevron-right"></span>
		</button>
	</form>

</div>