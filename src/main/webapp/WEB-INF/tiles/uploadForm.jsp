<div class="container">
	<legend>Upload a GEDCOM file for analysis</legend>
	<!-- The upload button -->
	<div id="terms" class="panel panel-warning">
		<div class="panel-heading">
			<h3 class="panel-title">Please read</h3>
		</div>
		<div class="panel-body">
			<ul>
				<li>File will <strong>upload immediately</strong> upon selection
				</li>
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
		<button class="btn btn-primary fileinput-button">
			<i class="glyphicon glyphicon-plus"></i> <span>Select file</span>
			<!-- The file input field used as target for the file upload widget -->
			<input id="fileupload" type="file" name="gedcomfile" data-url="uploadGedcom">
		</button>
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
		<!-- The container for the uploaded files -->
	</div>
	<br>

</div>

