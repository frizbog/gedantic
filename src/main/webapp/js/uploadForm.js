$(document).ready(function() {

	$('#fileupload').fileupload({
	  url : 'uploadGedcom',
	  dataType : 'string',
	  done : function(e, data) {
		  $.each(data.result.files, function(index, file) {
			  $('<p/>').text(file.name).appendTo(document.body);
		  });
	  },
	  progressall : function(e, data) {
		  var progress = parseInt(data.loaded / data.total * 100, 10);
		  $('#progress .progress-bar').css('width', progress + '%');
	  },
	  fail: function(e, data) {
	    var errorHtml = uploadFailedHtml + "<p>" + data.textStatus + "</p>";
	    showModal(uploadFailedTitle, errorHtml);
	  },
	  add: function(e, data) {
	    data.submit();
	    return true;
	  }
	});

});
