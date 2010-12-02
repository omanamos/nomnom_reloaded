class RecipeController < ApplicationController
  protect_from_forgery
  
  def new
  
  end
  
  def create
	@title = params[:recipe_title]
	@description = params[:description_text]
	@directions = params[:directions_text]
	if @title != "" and @directions != ""
		recipe = Recipe.create(:title => @title, :description => @description, :directions => @directions)
		for @i in (0..15)
			@amount = params["amount_#{@i}"]
			@item = params["item_#{@i}"]
			if @amount != "" and @item != ""
				ingredient = Ingredient.create(:recipe_id => recipe.id, :item => @item, :amount => @amount)
			end
		end
		redirect_to url_for :controller => 'search', :action => 'index', :query => @title
	else
		redirect_to url_for :controller => 'recipe', :action => 'new'
	end
  end
end
