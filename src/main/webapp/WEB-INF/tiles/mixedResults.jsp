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
<%@ taglib uri="http://classtagfunctions" prefix="class"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<li class="list-group-item"><span class="list-group-item-heading"> 
	<c:choose>
	<c:when test="${class:hasProperty(result, 'individual')}">
		<%-- individual result --%>
		Individual: ${result.individual.formattedName} 
		<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(result.individual)}" ></span> 
	</c:when> 
	<c:when test="${class:hasProperty(result, 'family')}">
		<%-- family result --%>
		Family: 
		<c:choose>
			<c:when test="${not empty result.family.husband}">${fn:escapeXml(result.family.husband.formattedName)} 
				<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(result.family.husband)}" ></span> 
				and
				<c:choose>
					<c:when test="${not empty result.family.wife}">${fn:escapeXml(result.family.wife.formattedName)} 
						<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(result.family.wife)}" ></span>
					</c:when>
					<c:otherwise>unknown wife</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>${fn:escapeXml(result.family.wife.formattedName)} 
				<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(result.family.wife)}" ></span> and unknown husband
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${class:hasProperty(result, 'repository')}">
		<%-- Repository result --%>
		Repository: ${result.repository.name} 
	</c:when> 
	<c:when test="${class:hasProperty(result, 'submitter')}">
		<%-- Submitter result --%>
		Submitter: ${result.submitter.name} 
	</c:when> 
	<c:when test="${class:hasProperty(result, 'item')}">
		<%-- Unrleated result --%>
		${result.item} 
	</c:when> 
	<c:otherwise>
	</c:otherwise>
	</c:choose> 
	
	<tiles:insertAttribute name="factsValuesProblems" />

</li>
