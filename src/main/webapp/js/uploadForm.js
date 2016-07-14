/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
$(document).ready(function() {

	$('#fileupload').fileupload({
	  options : {
	    acceptFileTypes : /(\.|\/)(ged|gedcom)$/i,
	    processQueue : {
	      action : 'validate',
	      acceptFileTypes : '@',
	      disabled : '@disableValidation'
	    }
	  },

	  processActions : {

		  validate : function(data, options) {
			  if (options.disabled) {
				  return data;
			  }
			  var dfd = $.Deferred(), file = data.files[data.index];
			  if (!options.acceptFileTypes.test(file.type)) {
				  file.error = 'Invalid file type.';
				  dfd.rejectWith(this, [ data ]);
			  } else {
				  dfd.resolveWith(this, [ data ]);
			  }
			  return dfd.promise();
		  }

	  },
	  url : 'uploadGedcom',
	  dataType : 'text',
	  fail : function(e, data) {
		  $('#progressection').toggleClass('hidden');
		  $('#fileupload').prop('disabled', false);
		  $('#fileselection .btn').removeClass('disabled');
		  $('#uploadFailure').modal('show');
	  },
	  progressall : function(e, data) {
		  var progress = parseInt(data.loaded / data.total * 100, 10);
		  $('#progress .progress-bar').css('width', progress + '%');
		  $('#progress .progress-bar').html(progress + '%');
	  },
	  add : function(e, data) {

		  $('#progressection').toggleClass('hidden');
		  $('#fileupload').prop('disabled', true);
		  $('#fileselection .btn').addClass('disabled');

		  var progresspump = setInterval(function() {
			  /* query the completion percentage from the server */
			  $.get("update-progress", function(data) {
				  /* update the progress bar width */
				  $("#parseprogress .progress-bar").css('width', data + '%');
				  /* and display the numeric value */
				  $("#parseprogress .progress-bar").html(data + '%');

				  /* test to see if the job has completed */
				  if (data > 99.999) {
					  clearInterval(progresspump);
					  $("#parseprogress .progress-bar").css('width', '100%');
					  setTimeout(function() {
						  window.location.replace('analyzeMenu.tiles');
					  }, 500);

				  }
			  })
		  }, 500);

		  data.submit();
		  return true;
	  }
	});

});
