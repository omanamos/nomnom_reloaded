$(document).ready(function() {
	$("#banner").randompic({
            directory: "../../images/banners/", //directory to image folder
            howmany: 2 //how many images are in this folder     
    });
	$("#main_search").submit(function(e) {
		e.stopImmediatePropagation();
		if ($("#query").val().trim() != "") {
			$("#main_search").submit();
		}
		return false;
	});
})