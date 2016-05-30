/**
 * Javascript that goes with index.jsp 
 */
$(document).ready(
    function(){
    	
    	// Start with submit button disabled
    	$("#submit").attr('disabled', 'disabled');

    	// Enable submit button when file selected and terms of use checked
    	$("form").change(
            function(){
            	var fileval = $('#gedcomfile').val();
            	var touval = $('#touandprivacy').is(':checked');
                if (fileval && touval) {
                    $('#submit').removeAttr('disabled');
                } 
                else {
                    $('#submit').attr('disabled','disabled');
                } 
            }
            );
        
        
        // On click of submit button, disable button
        $("#uploadform").submit(function(){
        	$("#submit").text("Uploading...");
        	$("#submit").attr('disabled','disabled');
        	});
    });
