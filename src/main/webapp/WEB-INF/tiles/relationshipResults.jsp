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
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<li class="list-group-item"><span class="list-group-item-heading">${fn:escapeXml(result.individual.formattedName)} 
	<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(result.individual)}" ></span> 
	
	<dl class="dl-horizontal">
		<c:if test="${not empty result.factType}">
			<dt>Fact type</dt>
			<dd>${fn:escapeXml(result.factType)}</dd>
		</c:if>
		<c:if test="${not empty result.value}">
			<dt>Value</dt>
			<dd>
			<c:forEach items="${result.value}" var="relChain">
				<div>
				<c:forEach items="${relChain}" var="simpleRel">
					<div>
						${fn:escapeXml(simpleRel.individual1.formattedName)} 
						<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(simpleRel.individual1)}" ></span>
						<span class="glyphicon glyphicon-chevron-right"></span> HAS 
						<c:choose>
						<c:when test="${empty simpleRel.name}">
						UNSPECIFIED
						</c:when>
						<c:otherwise>
						${fn:escapeXml(simpleRel.name)}
						</c:otherwise>
						</c:choose>
						<span class="glyphicon glyphicon-chevron-right"></span>
						${fn:escapeXml(simpleRel.individual2.formattedName)} 
						<span class="glyphicon glyphicon-info-sign" data-toggle="tooltip" title="${fn:escapeXml(simpleRel.individual2)}" ></span>
					</div>
				</c:forEach>
				</div>
			</c:forEach>
			</dd>
		</c:if>
		<c:if test="${not empty result.problem}">
			<dt>Problem</dt>
			<dd>${fn:escapeXml(result.problem)}</dd>
		</c:if>
	</dl>	
</li>
