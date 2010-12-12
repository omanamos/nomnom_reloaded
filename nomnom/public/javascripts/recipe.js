$(document).ready(function() {
	$("#add_ingredient").click(function(e) {
		e.stopPropagation();
		var i = $(".recipe_ingredients tr").length;
		$(".recipe_ingredients tbody:first").append(buildRow(i));
		return false;
	});
});

function buildRow(i) {
	function buildInput(kind, label) {
		return $("<td>" + label + ": " + "</td>").append($("<input></input>").attr({
			name: kind + "_" + i,
			class:  "ingredient_text",
			type: "text",
			maxlength: "40",
			value: ""
		}));
	}
	var amount = buildInput("amount", "Amount");
	var item = buildInput("item", "Ingredient");
	var row = $("<tr></tr>").append(amount).append(item);
	return row;
}