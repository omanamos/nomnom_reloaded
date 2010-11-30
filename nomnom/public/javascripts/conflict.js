//Using window.onload fixes a javascript clash with existing 
//libraries that prevented this file from being executed properly
/*window.onload = function() {
	$(".ingredient:first").removeClass("hide").addClass("show");
	$("#next").click(function() { 
		shift_ingredient(1);	
	});
	$("#last").click(function() { 
		shift_ingredient(-1);	
	});
}*/

function shift_ingredient(n) {
	var ingredients = $(".ingredient");
	var current_ingredient = $(".ingredient.show")[0];
	var idx = ingredients.toArray().indexOf(current_ingredient);
	var newIndex = (idx + n) % ingredients.length;
	if (newIndex < 0) newIndex += ingredients.length;

	$(current_ingredient).removeClass("show").addClass("hide");
	$(ingredients[newIndex]).removeClass("hide").addClass("show");
}

$(document).ready(function(){
	var body_width = $('body').width();
	var body_height = $('body').height();
	$('#amazonFreshFrame').css({
		'height': body_height - 82
	});
	$(".ingredient:first").removeClass("hide").addClass("show");
	$(".suggestions").change(changeASIN);
	
	$("#next").click(function() { 
		shift_ingredient(1);	
	});
	$("#prev").click(function() { 
		shift_ingredient(-1);	
	});
});

function changeASIN(event){
	$('#amazonFreshFrame').attr('src', "http://fresh.amazon.com/product?asin=" + $(":selected", $(this)).attr('id'));
}
