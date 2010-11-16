$(document).ready(function(){
	$('#login_form .format').formatter();
	$('#show_login').click(function(){
		$('#show_login').fadeOut('fast', function(){
			$('#login_form').fadeIn('slow');
		});
	});
});
