class SearchController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:query]
  	@results = Recipe.search(@query).docs
  end
end
