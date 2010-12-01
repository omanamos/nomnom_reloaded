$(document).ready(function() {
	$("#main_search").submit(function(e) {
		e.stopImmediatePropagation();
		if ($("#query").val().trim() != "") {
			$("#main_search").submit();
		}
		return false;
	})
})