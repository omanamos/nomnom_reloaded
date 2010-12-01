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
    solr = RSolr.connect :url => 'http://0.0.0.0:8982/solr'
    response = solr.get 'select', :params => {:q => '*:*'}
    #response = solr.get 'select', :params => {:q=>'roses', :fq=>['red', 'violet']}
    
    #solr.get 'terms', :params => {'terms.fl' => 'title_t', 
    #  'terms.prefix' => 'pi',
    #    'terms.limit' => -1, 'omitHeader' => true}
        
        
    #results = eval(solr.terms("terms.fl" => "title_t", "terms.prefix" => "pi",
    #    "terms.limit" => -1, "omitHeader" => true))
    #http://0.0.0.0:8982/solr/terms?terms.fl=title_t&terms.prefix=pi&terms.limit=-1&omitHeader=true
  	render :text => ["onethee\n", "two\n", "three\n"]
  end
end
