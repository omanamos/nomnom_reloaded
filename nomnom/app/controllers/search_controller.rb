class SearchController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:query]
  end
end
