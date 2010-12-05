var cur_ind = -1;

window.onload = function() {
	$('#amazonFreshFrame').css({
		'height': $('body').height() - 88
	});
	if(sign_in){
		setupConf();
	}else{
		$('#signed_in').click(function(){
			$('#signin_header').hide();
			setupConf();
		});
	}
}

function setupConf(){
	$('#ingr_header').show();
	
	$("#next").click(shiftIngr);
	$("#prev").click(shiftIngr);
	
	$("#next").click();
}

function shiftIngr(){
	var shiftRight = this.id == "next";
	
	if($(this).html() != "Finish"){
		cur_ind += shiftRight ? 1 : -1;
		if(cur_ind <= 0){
			$("#prev").attr("disabled", true);
			$("#next").attr("disabled", false);
		}else if((cur_ind + 1) == ingredients.length){
			$("#prev").attr("disabled", false);
			$("#next").html("Finish");
		}else{
			$("#prev").attr("disabled", false);
			$("#next").attr("disabled", false);
		}
	
		loadIngr(ingredients[cur_ind]);
	}else{
		$('#ingr_header').hide();
		$('#amazonFreshFrame').hide();
		$('#finish_main').show();
		$('#continue_shopping').click(function(){ $.fancybox.close(); });
		$('#checkout').click(function(){
			$('#finish_main').html("<h1>Thanks for using NomNom.</h1><h1>We are redirecting you to Amazon Fresh...</h1>");
			window.setTimeout(function(){
				parent.window.location = "http://fresh.amazon.com/";
			}, 1500);
		});
	}
}

function loadIngr(id){
	if($('#' + id).length){
		renderIngr($('#' + id));
		$('#' + id + '_suggestions').change();
	}else{
		$.get("/conflict/show", { 'id': id }, 
			function(data){
				$(data).appendTo(".ingredients");
				
				var element = $('#' + id);
				var options = $('#' + id + '_suggestions');
				
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
