var cur_ind = -1;

$(document).ready(function(){
	$('#amazonFreshFrame').css({
		'height': $('body').height() - 82
	});
	
	$("#next").click(shiftIngr);
	$("#prev").click(shiftIngr);
	
	$("#next").click();
});

function shiftIngr(){
	var shiftRight = this.id == "next"
	
	if(cur_ind <= 0){
		$("#prev").attr("disabled", true);
		$("#next").attr("disabled", false);
	}else if((cur_ind + 1) == ingredients.length){
		$("#prev").attr("disabled", false);
		$("#next").attr("disabled", true);
	}else{
		$("#prev").attr("disabled", false);
		$("#next").attr("disabled", false);
	}
	cur_ind += shiftRight ? 1 : -1;
	
	loadIngr(ingredients[cur_ind]);
}

function loadIngr(id){
	if($('#' + id).length){
		renderIngr($('#' + id));
		$('#' + element.attr("id") + '_suggestions').change();
	}else{
		$.get("/conflict/show", { 'id': id }, 
			function(data){
				$(data).appendTo(".ingredients");
				
				var element = $('#' + id);
				var options = $('#' + element.attr("id") + '_suggestions');
				
				options.change(changeASIN);
				renderIngr(element);
				options.change();
		});
	}
}

function renderIngr(element){
	$(".ingredient").hide();
	element.show();
}

function changeASIN(event){
	$('#amazonFreshFrame').attr('src', "http://fresh.amazon.com/product?asin=" + $(":selected", $(this)).attr('id'));
}
