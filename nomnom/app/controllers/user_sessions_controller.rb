class UserSessionsController < ApplicationController

  def create
    @user_session = UserSession.new(params[:user_session])
    @user_session.save() ? successful(@user_session) : unsuccessful;
  end

  def destroy
    current_user_session.destroy if current_user_session
    render :json => { :success => true }
  end

  private

  def successful(session)
  	@current_user_session = session
    render :json => { :name => session.user.fl_name, :success => true }
  end

  def unsuccessful
    render :json => { :success => false }
  end
end
