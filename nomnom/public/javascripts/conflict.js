$(document).ready(function(){
	//var amountWidth = ensureWidths(".amount", 0);
    //ensureWidths(".item", amountWidth, 10);
    $('#addToCart').click(addToCart);
});

function addToCart(){
	
}

function ensureWidths(class, leftMargin, offset) {
	var maxWidth = 0;
	$(class).each(function(idx, element) {
		maxWidth = Math.max(parseInt($(element).css("width")), maxWidth);	
	});
	$(class).each(function(idx, element) {
		$(element).css("width", Math.min(maxWidth, 200) + "px");
		if ($(element).hasClass("amountLess")) {
			$(element).css("marginLeft", leftMargin + offset + 117 + "px");	
		} else {
			$(element).css("marginLeft", offset + "px");	
		}
	});
	return maxWidth;
}
