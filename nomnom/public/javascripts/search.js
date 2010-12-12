$(document).ready(function(){
    $('input.get_ingredients').click(chooseIngredients);
    $('input.page_top_search').click(clearSearchBox);

	//$('input.viewtoggle').click(viewRecipeBody);
	$('.dropdown').click(viewRecipeBody);
	
	$('a.get_ingredients').fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'elastic',
		'type'				: 'iframe'
	});
	
	$('a.original_recipe').fancybox({
		'width'				: '100%',
		'height'			: '100%',
		'autoScale'			: false,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'elastic',
		'type'				: 'iframe'
	});
	
	/*
	$("input#query").autocomplete({
		source: getRecipeNames,
		delay: 0
	});
	*/
});

function getRecipeNames(request, callback) {
	callback(["cookies", "ham", "spaghetti", "peanut butter cookie"]);	
}

function viewRecipeBody() {
	var toggle = $("#toggle_" + this.id.split("_")[1]);
    var text = $("#body_" + this.id.split("_")[1]);
    if(text.css("display") == "none") {
        text.slideDown(500);
        //this.value = "<<<...";
        $(toggle).attr('src', '../images/minus.jpg')
    }
    else {
        text.slideUp(500);
        //this.value = "...>>>";
        $(toggle).attr('src', '../../images/plus.jpg')
    }
}


function chooseIngredients() {
	$(this).fancybox();
}

function clearSearchBox() {
	this.value = "";
}

