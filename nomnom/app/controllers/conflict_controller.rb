class ConflictController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:recipeID]
  	@recipe = Recipe.find(@query)
  end
end
