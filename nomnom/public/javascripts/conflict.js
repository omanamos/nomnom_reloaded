$(document).ready(function(){
	//var amountWidth = ensureWidths(".amount", 0);
    //ensureWidths(".item", amountWidth, 10);
    $('#addToCart').click(addToCart);
    $('tr.ingredient').click(toggleChecked);
    $('.include_item input, tr.ingredient .amount, tr.ingredient .item').click(function(e) {
    	e.stopPropagation();	
    });
    $('td.expand').toggle(showIngredient, hideIngredient);
    $('tr.ingredient').hover(mouseOver, mouseOut);
    $('select.item').each(function(idx, elem) {
    	queryAsinSolr($(elem));
    });
});

function queryAsinSolr(element) {
	
	function populateSelect(elem, xml) {
		/*
		var sel = elem.parent();
		$(xml).find("doc").each(function(idx, item) {
			var simpleName = item.find("str[name=simpleProductName]").text();
			var asin = item.find("str[name=asin]").text();
			sel.append($.create("option").text(simpleName).attr("value", asin));
		})
		*/
		alert(xml);
	}
	
	$.ajax({
		type: "get",
		url: 	"http://0.0.0.0:8983/solr/select/",
		data: {
			q: element.text().trim(),
			version: 2.2,
			start: 0,
			rows: 10,
			indent: "on"	
		},
		dataType: "xml",
		success: function(xml) {
			populateSelect(elem, xml);	
		},
		error: function() {
			//alert("Crap! We failed.");	
		}
	});	
}

function showIngredient(e) {
   	e.stopPropagation();
   	var num = parseInt($(this).parent().attr("id").split("_")[1]);
   	$("#ingr_expand_" + num).slideDown(200);
}

function hideIngredient(e) {
	e.stopPropagation();
   	var num = parseInt($(this).parent().attr("id").split("_")[1]);
   	$("#ingr_expand_" + num).slideUp(100);	
}

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
	$([{asin: 'B0017U4SNW', quantity: 1}, {asin: 'B0017U8GT4', quantity: 1}]).addToCart();
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
