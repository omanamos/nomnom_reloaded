class SearchController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:query]
  	if @query == ""
  		redirect_to url_for :controller => 'home', :action => 'index'
  	else
  		@results = Recipe.search(@query)
  	end
  end
  
  def show
  	render :text => ["one\n", "two\n", "three\n"]
  end
end
