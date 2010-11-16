$(document).ready(function(){
	//var amountWidth = ensureWidths(".amount", 0);
    //ensureWidths(".item", amountWidth, 10);
    $('#addToCart').click(addToCart);
    $('tr.ingredient').click(toggleChecked);
    $('.include_item input').click(function(e) {
    	e.stopPropagation();	
    });
    $('tr.ingredient').hover(mouseOver, mouseOut);
});

function mouseOver(event){
	$(this).css({
		backgroundColor: '#CCCCCC',
		cursor: 'pointer'
	});
}

function mouseOut(event){
	$(this).css('background', 'none');
}

function toggleChecked(event){
	$('td.include_item input[type="checkbox"]', this).toggleChecked();
}

function addToCart(event){
	$([{
		asin: 'B000NSH21E', 
		quantity: 1
	}, {
		asin: 'B0017U8GT4', 
		quantity: 1
	}]).addToCart();
}

//Legacy function, keep it for kicks
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
