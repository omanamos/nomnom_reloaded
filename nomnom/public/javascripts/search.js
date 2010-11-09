$(document).ready(function(){
        $('input.viewtoggle').click(viewRecipeBody);
        $('input.get_ingredients').click(chooseIngredients);
        $('input.page_top_search').click(clearSearchBox);
});



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

function chooseIngredients() {
	$(this).fancybox();
}

function clearSearchBox() {
	this.value = "";
}
