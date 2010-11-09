$(document).ready(function(){
	$('input.viewtoggle').click(viewRecipeBody);
	
	$('a.get_ingredients').fancybox({
		'width'				: '75%',
		'height'			: '75%',
		'autoScale'			: false,
		'transitionIn'		: 'elastic',
		'transitionOut'		: 'elastic',
		'type'				: 'iframe'
	}).addClass("blah");
	
	$("input#query").autocomplete({
		source: getRecipeNames,
		delay: 0
	});
});

function getRecipeNames(request, callback) {
	callback(["cookies", "ham", "spaghetti", "peanut butter cookie"]);	
}

function viewRecipeBody() {
    // access the JS DOM objects for the two text boxes on the page
    var text = $("#body_" + this.id.split("_")[1]);
    if(text.css("display") == "none") {
        text.slideDown(500);
        this.value = "Hide Recipe";
    }
    else {
        text.slideUp(500);
        this.value = "View Recipe";
    }
}

