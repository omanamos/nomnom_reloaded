class UsersController < ApplicationController

  # GET /users/new
  # GET /users/new.xml
  def new
    @user = User.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @user }
    end
  end

  # GET /users/1/edit
  def edit
  	if(logged_in?)
	    @user = User.find(current_user.id)
	else
		flash[:user_auth] = "You are not authorized to access that area"
		redirect_to("/");
	end
  end

  # POST /users
  # POST /users.xml
  def create
    @user = User.new(params[:user])
    @user.role = "customer"

    respond_to do |format|
      if @user.save
        format.html { redirect_to("/") }
      else
        format.html { render :action => "new" }
      end
    end
  end

  # PUT /users/1
  # PUT /users/1.xml
  def update
    @user = User.find(params[:id])

    respond_to do |format|
      if @user.update_attributes(params[:user])
        format.html { redirect_to("/") }
      else
        format.html { render :action => "edit" }
      end
    end
  end

  # DELETE /users/1
  # DELETE /users/1.xml
  def destroy
    @user = User.find(params[:id])
    @user.destroy

    respond_to do |format|
      format.html { redirect_to(users_url) }
    end
  end
end
