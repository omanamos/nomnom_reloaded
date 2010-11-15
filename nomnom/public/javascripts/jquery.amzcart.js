(function( $ ){
	$.fn.addToCart = function(){
		$('body').append('<div id="amzcart"><a id="fb_iframe" href="https://fresh.amazon.com/SignIn">Sign In</a></div>');
		$('#fb_iframe').fancybox({
			width: '75%',
			height: '75%'
		});
		//$('#fb_iframe').trigger('click');
	}
})( jQuery );
