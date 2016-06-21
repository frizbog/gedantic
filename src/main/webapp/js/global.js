$(document).ready(function(){
    $('[data-toggle="popover"]').popover();
});

function updateAnalysisDescription() {
	var sel = $('#analysisSelection');
	window.alert(sel.selectedItem.value);
}
