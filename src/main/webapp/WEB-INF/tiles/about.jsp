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
<div class="container">
	<h1>About gedantic</h1>
	<p>
		gedantic is written using <a href="http://gedcom4j.org">gedcom4j</a>, a java-based library for parsing and writing genealogy data in GEDCOM
		format. It's intended to be both a useful tool in its own right, and to be a demonstration of what gedcom4j can do.
	</p>
	<p>The name is a portmanteau of "gedcom" and "pedantic" - since it's job is to nitpick through your gedcom file and look for anomalies and
		things.</p>
	<p>
		Source is available at <a href="http://github.com/frizbog/gedantic">http://github.com/frizbog/gedantic</a>.
	</p>
	<h1>Have an issue or a suggestion?</h1>
	<p>
		Please <a href="https://github.com/frizbog/gedantic/issues/new">create an issue at Github</a>.
	</p>
	<h1>Notable Technologies Used</h1>
	<dl class="dl-horizontal">
		<dt>JSP 2.3.1 / Servlet 3.1</dt>
		<dd>Makes for easy consumption of the gedcom4j library - since it's a java library, it makes sense to use java server-side technology to demo it</dd>
		<dt>
			<a href="http://tiles.apache.org">Apache Tiles</a>
		</dt>
		<dd>Used for JSP templates to make pages consistent</dd>
		<dt>
			<a href="http://getbootstrap.com">Bootstrap 3</a>
		</dt>
		<dd>HTML, CSS, and JS framework</dd>
		<dt>
			<a href="http://jquery.org">jQuery</a> and <a href="http://jqueryui.com">jQuery-ui</a>
		</dt>
		<dd>Javascript library and widgets</dd>
		<dt>
			<a href="https://blueimp.github.io/jQuery-File-Upload/">jQuery File Upload</a>
		</dt>
		<dd>jQuery-based file upload widget</dd>
		<dt>
			<a href="http://commons.apache.org">Apache Commons</a>
		</dt>
		<dd>File upload processing, JavaBean processing, HTML escaping, and more</dd>
		<dt>
			<a href="http://yiotis.net/filterizr">Filterizr</a>
		</dt>
		<dd>jQuery-based filtering widget (used on analysis selection page)</dd>
	</dl>
	<h4>Licenses</h4>
	<p>
		Licenses for the open source tools above can be found <a href="licenses.html">here</a>.
	</p>
</div>