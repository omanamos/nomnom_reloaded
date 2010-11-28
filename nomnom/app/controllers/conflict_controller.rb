class ConflictController < ApplicationController
  protect_from_forgery
  
  def index
  	solr = RSolr.connect :url=>'http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr'
  	@query = params[:recipeID]
  	@recipe = Recipe.find(@query)
  	@results = {}
  	for ingr in @recipe.ingredients
  		item = ingr.item
  		queryResults = solr.select(:q=> item, :wt=>'ruby', :start=> 0, :rows=> 5)
  		@results[item] = eval(queryResults)
  	end
  end
end
