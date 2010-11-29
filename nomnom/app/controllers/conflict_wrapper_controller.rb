class ConflictWrapperController < ApplicationController
  protect_from_forgery
  
  def index
  	@recipeID = params[:recipeID]
  end
end
