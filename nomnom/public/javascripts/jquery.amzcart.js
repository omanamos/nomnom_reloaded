(function( $ ){
	$.fn.addToCart = function(items){
		$('body').append('<div id="amzcart" style="display:none;"><a id="fb_iframe" href="https://fresh.amazon.com/SignIn"></a></div>"');
		$('#fb_iframe').fancybox({
			width: '75%',
			height: '75%'
		});
		$('#fb_iframe').trigger('click');
	}
})( jQuery );
