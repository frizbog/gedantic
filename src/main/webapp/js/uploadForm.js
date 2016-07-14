$(document).ready(function() {

	$('#fileupload').fileupload({
	  url : 'uploadGedcom',
	  dataType : 'string',
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
