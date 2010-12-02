class ConflictController < ApplicationController
  protect_from_forgery
  
  
  def index
  	@current = 0
  	@query = params[:recipeID]
  	@recipe = Recipe.find(@query)
  	
  	#solr = RSolr.connect :url=>'http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr'
  	#@item = get_item_results(@recipe.ingredients[@current])
  	#for ingr in @recipe.ingredients
  	#	ingr.item.gsub!(/([\+\-\(\)\{\}\[\]\*])/) {|match| "\\" + match.to_s }
  	#	queryResults = solr.select(:q=> ingr.item, :wt=>'ruby', :start=> 0, :rows=> 10)
  	#	@results[ingr.item] = eval(queryResults)
  	#end
  end
  
  def show
  	ingr_id = params[:id]
  	@ingr = Ingredient.find(ingr_id)
  	
  	solr = RSolr.connect :url=>'http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr'
  	@results = eval(solr.select(:q=> @ingr.item.gsub(/([\+\-\(\)\{\}\[\]\*])/) {|match| "\\" + match.to_s }, :wt=>'ruby', :start=> 0, :rows=> 10))
  	render :partial => 'ingredient'
  end
  
  private
  
  def get_item_results ingr
  	ingr.item.gsub!(/([\+\-\(\)\{\}\[\]\*])/) {|match| "\\" + match.to_s }
  	queryResults = solr.select(:q=> ingr.item, :wt=>'ruby', :start=> 0, :rows=> 10)
  	@item = {ingr.item => queryResults}
  end
  
  def nextItem
  	@current = (@current + 1) % @recipe.ingredients.length
  	@item = get_item_results(@recipe.ingredients[@current])
  end
  
  def lastItem
  	@current = (@current - 1) % @recipe.ingredients.length
  	@item = get_item_results(@recipe.ingredients[@current])
  end
end
