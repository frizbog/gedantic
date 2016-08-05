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
	<div id="terms" class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">Upload a GEDCOM file for analysis</h3>
		</div>
		<div class="panel-body">
			<ul>
				<li>File will <strong>upload immediately</strong> upon selection
				</li>
				<li>By uploading a file, <strong>you agree</strong> to our <a href="#" data-toggle="popover" data-trigger="focus"
					title="Terms of Use" data-html="true"
					data-content="You are welcome to use the results from this service in anyway you see fit. Software is provided under the terms of the <a href='licenses.html'>MIT license</a>: 'as is', no warranty, and no author liability.">terms
						of use</a> and <a href="#" data-toggle="popover" title="Privacy Statement" data-trigger="focus" data-html="true"
					data-content="<ul><li>We don't share your data with anyone.</li><li>We delete your uploaded file immediately after loading it into memory.</li><li>This is not an HTTPS server so data transmitted is <strong>not encrypted</strong>.</li><li>Please think carefully about uploading private data about living individuals and protect their privacy.</li></ul>">privacy
						statement</a>.
				<li>The maximum file size for uploads is <strong>8 MB</strong>.
				</li>
				<li>Only GEDCOM files (<strong>GED, GEDCOM</strong>) are allowed.
				</li>
				<li>Uploaded files are <strong>deleted from the server immediately after parsing</strong> and stored in memory.
				</li>
				<li>You can <strong>drag &amp; drop</strong> files from your desktop on this webpage.
				</li>
			</ul>
		</div>
		<div class="container">
			<div id="fileselection">
				<form action="uploadGedcom" method="POST">
					<button class="btn btn-primary fileinput-button">
						<i class="glyphicon glyphicon-file"></i> <span>Select file</span>
						<!-- The file input field used as target for the file upload widget -->
						<input id="fileupload" type="file" name="gedcomfile" data-url="uploadGedcom">
					</button>
					<input type="submit" class="btn btn-default" name="useSample" value="Use our sample file"/>
				</form>
			</div>
			<p></p>
		</div>
	</div>
	<div id="progressection" class="container hidden">
		<!-- The global progress bar -->
		Upload:
		<div id="progress" class="progress  ">
			<div class="progress-bar progress-normal" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
				style="min-width: 2em;">0%</div>
		</div>
		Parse:
		<div id="parseprogress" class="progress  ">
			<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
				style="min-width: 2em;">0%</div>
		</div>
	</div>


	<div class="modal fade" id="uploadFailure" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Upload Failed</h4>
				</div>
				<div class="modal-body">
					Your file upload failed. Please double-check a few things before retrying:
					<ul>
						<li><strong>Only GEDCOM files are allowed.</strong> They need to have a .ged or .gedcom extension.</li>
						<li><strong>Files must be 8MB or smaller.</strong> You will need to reduce the size of your GEDCOM file (remove
							individuals, families, notes, multimedia, etc.) in order to submit it.</li>
					</ul>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>


</div>

