var default_values = {
	email: 'Email',
	password: 'Password'
};
$(document).ready(function(){
	$("body").randombg({
            directory: "../../images/backgrounds/", //directory to image folder
            howmany: 2 //how many images are in this folder     
    });
	$('#login_form .format').formatter(default_values);
	$('#show_login').click(showLogin);
	$('#login_form').submit(login);
	$('#cancel_login').click(hideLogin);
	$('#logout').click(logout);
	if($('#flash').html() != ''){
		$('#flash').show();
	}

});

function showLogin(){
	$('#flash').hide();
	$('#login_links').fadeOut('fast', function(){
		$('#login_form').fadeIn('slow');
	});
}

function hideLogin(){
	$('#login_form').fadeOut('fast', function(){
		$('#login_links').fadeIn('slow');
	});
}

function login(){
	$.post('/login', $(this).serialize(), loginCallback, 'json');
	return false;
}

function loginCallback(data){
	if(data.success){
		$('#user_name').html(data.name);
		$('#login_form').fadeOut('fast', function(){
			$('#logged_in').fadeIn('slow');
			clear($('#login_form'));
		});
	}else{
		alert("Login Failed");
	}
}

function logout(){
	$.post('/logout', logoutCallback, 'json');
}

function logoutCallback(data){
	if(data.success){
		$('#user_name').html("Guest");
		$('#logged_in').fadeOut('fast', function(){
			$('#login_links').fadeIn('slow');
		});
	}else{
		alert("Logout Failed");
	}
}

function clear(ele) {
    ele.find(':input').each(function() {
    	if(!$(this).hasClass('default_value')){
		    switch(this.type) {
		        case 'password':
		        case 'select-multiple':
		        case 'select-one':
		        case 'text':
		        case 'textarea':
		            $(this).val('');
		            $(this).blur();
		            break;
		        case 'checkbox':
		        case 'radio':
		            this.checked = false;
		    }
		}
    });
}