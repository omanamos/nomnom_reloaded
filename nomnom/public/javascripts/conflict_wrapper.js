//Using window.onload fixes a javascript clash with existing 
//libraries that prevented this file from being executed properly
window.onload = function() {
	//This functionality is here because of the need to communicate between the frames in this iframe
	$($('#conflictFrame')[0].contentDocument).delegate('.asinLink', "click", function(e) {
		e.stopImmediatePropagation();
		$('#amazonFreshFrame').attr('src', this.id);
	});
}