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
<nav id="header" class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-brand">
			<a href="analyzeMenu.tiles">gedantic</a>
		</div>

		<div class="navbar-text navbar-right">
			<c:if test="${empty gedcomName || empty gedcom}">
			<p class="well">No GEDCOM loaded</p>
			</c:if>
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
