class SearchController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:query]
  	@results = Recipe.search(@query)
  end
  
  def show
  	render :text => ["one\n", "two\n", "three\n"]
  end
end
