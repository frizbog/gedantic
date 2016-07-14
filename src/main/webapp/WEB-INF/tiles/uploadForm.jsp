<div class="container">
	<legend>Upload a GEDCOM file for analysis</legend>
	<!-- The upload button -->
	<div id="terms" class="panel panel-warning">
		<div class="panel-heading">
			<h3 class="panel-title">Please read</h3>
		</div>
		<div class="panel-body">
			<ul>
				<li>File will <strong>upload immediately</strong> upon selection</li>
				<li>By uploading a file, <strong>you agree</strong> to our <a href="termsofuse.tiles">terms of use</a> and <a
					href="privacy.tiles">privacy statement</a>.
				<li>The maximum file size for uploads in this demo is <strong>8 MB</strong>.
				</li>
				<li>Only GEDCOM files (<strong>GED, GEDCOM</strong>) are allowed.
				</li>
				<li>Uploaded files are <strong>deleted from the server immediately after parsing</strong> and stored in memory.
				</li>
				<li>You can <strong>drag &amp; drop</strong> files from your desktop on this webpage.
				</li>
			</ul>
		</div>
	</div>
	<div id="fileselection">
		<div class="btn btn-primary fileinput-button"> 
			<i class="glyphicon glyphicon-plus"></i> <span>Select file</span> <!-- The file input field used as target for the file upload widget -->
			<input id="fileupload" type="file" name="gedcomfile" data-url="uploadGedcom">
		</div>
	</div>
	<!-- The global progress bar -->
	<div id="progress" class="progress">
		<div class="progress-bar progress-bar-success"></div>
	</div>
	<!-- The container for the uploaded files -->
	<div id="files" class="files"></div>
	<br>

</div>

