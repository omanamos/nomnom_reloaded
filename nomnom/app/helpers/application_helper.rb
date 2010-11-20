module ApplicationHelper
	def render_taskbar
		rtn = '
				<div id="taskbar">
					<div id="welcome">
						Welcome, <span id="user_name">' + (logged_in? ?  current_user.fl_name : "Guest") + '</span>
					</div>
					<div id="flash">' +	(flash[:user_auth] ? flash[:user_auth] : '') + '</div>
					<div id="login">
						<div id="login_links" ' + (logged_in? ? 'style="display:none;"' : '') + '>
							<a id="show_login" href="#">Login</a> &bull; 
							<a id="sign_up" href="/signup">Sign Up</a>
						</div>
						<form id="login_form">
							<input id="email" class="format" name="user_session[email]" type="text" value="Email" maxlength="50" />
							<input id="password" class="format" name="user_session[password]" type="password" value="Password" maxlength="50" />
							<input id="submit_login" type="submit" value="Login" />
							<img id="cancel_login" src="' + image_path('login_cancel.jpg') + '">
						</form>
						<div id="logged_in" ' + (!logged_in? ? 'style="display:none;"' : '') + '>
							<a id="account" href="/account">Account</a> &bull;
							<a id="logout" href="#">Logout</a>
						</div>
					</div>
				</div>'
		return rtn.html_safe
	end
end
