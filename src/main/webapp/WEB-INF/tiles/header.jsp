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

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="analyzeMenu.tiles">gedantic</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
			<c:choose>
			<c:when test="${empty gedcomName || empty gedcom}">
			<li class="active"><a href="upload.tiles">No GEDCOM loaded</a></li>
			</c:when>
			<c:when test="${gedcomName == 'gedantic sample.ged' }">
				<li><a href="analyzeMenu.tiles">Select Analysis</a></li>
				<li><a href="#" data-toggle="popover" data-placement="bottom" tabindex="0" role="button" data-trigger="focus" 
						data-content="&lt;div&gt;Individuals: ${numIndividuals}&lt;/div&gt;&lt;div&gt;Families: ${numFamilies}&lt;/div&gt;
						&lt;div&gt;&lt;a href=&quot;downloadSample&quot;&gt;&lt;span class=&quot;glyphicon glyphicon-download&quot;&gt;
						&lt;/span&gt;Download&lt;/a&gt;&lt;/div;&gt;" data-html="true">
							<span class="glyphicon glyphicon-info-sign"></span> ${gedcomName}
					</a></li>
				<li class="active"><a href="upload.tiles">
							<span class="glyphicon glyphicon-upload"></span> Upload another file
					</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="analyzeMenu.tiles">Select Analysis</a></li>
				<li><a href="#" data-toggle="popover" data-placement="bottom" tabindex="0" role="button"  
						data-content="&lt;div&gt;Individuals: ${numIndividuals}&lt;/div&gt;&lt;div&gt;Families: ${numFamilies}&lt;/div&gt;" data-html="true">
							<span class="glyphicon glyphicon-info-sign"></span> ${gedcomName}
					</a></li>
				<li class="active"><a href="upload.tiles">
							<span class="glyphicon glyphicon-upload"></span> Upload another file
					</a></li>
			</c:otherwise>
			</c:choose>
			</ul>
		</div>
	</div>
</nav>

