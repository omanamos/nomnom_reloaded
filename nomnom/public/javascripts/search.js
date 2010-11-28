$(document).ready(function(){
    $('input.get_ingredients').click(chooseIngredients);
    $('input.page_top_search').click(clearSearchBox);

	$('input.viewtoggle').click(viewRecipeBody);
	
	$('a.get_ingredients').fancybox({
		'width'				: '80%',
		'height'			: '100%',
		'autoScale'			: true,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'elastic',
		'type'				: 'iframe'
	});
	
	$('a.original_recipe').fancybox({
		'width'				: '75%',
		'height'			: '90%',
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
    var text = $("#body_" + this.id.split("_")[1]);
    if(text.css("display") == "none") {
        text.slideDown(500);
        //this.value = "Hide Recipe";
        this.value = "<<<...";
    }
    else {
        text.slideUp(500);
        //this.value = "View Recipe";
        this.value = "...>>>";
    }
}


function chooseIngredients() {
	$(this).fancybox();
}

function clearSearchBox() {
	this.value = "";
}

