


	<div class="container">
		<form id="uploadform" action="uploadGedcom" method="post"
			enctype="multipart/form-data">
			<legend>Upload a GEDCOM file for analysis</legend>
			<fieldset class="form-group">
				<label for="gedcomfile">GEDCOM file to analyze</label> <input
					name="gedcomfile" type="file" id="gedcomfile" class="filestyle"
					data-buttonName="btn-primary"> <small class="text-muted">We'll
					never share your GEDCOM file with anyone else. GEDCOM 5.5 and 5.5.1
					files are supported.</small>
			</fieldset>
			<fieldset class="form-group">
				<div class="checkbox">
					<label> <input type="checkbox" name="touandprivacy"
						id="touandprivacy">I accept the <a href="termsofuse.tiles">terms
							of use</a> and <a href="privacy.tiles">privacy statement</a>
					</label>
				</div>
			</fieldset>
			<button type="submit" id="submit" class="btn btn-primary"
				disabled="disabled"">Upload</button>
		</form>
	</div>
	
