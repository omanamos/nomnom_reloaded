class SearchController < ApplicationController
  protect_from_forgery
  
  def index
  	@query = params[:query]
  	escaped_query = @query.gsub(/([\+\-\(\)\{\}\[\]\*])/) {|match| "\\" + match.to_s }
  	if @query == ""
  		redirect_to url_for :controller => 'home', :action => 'index'
  	else
  		@results = Recipe.search(escaped_query)
  	end
  end
  
  def show
    prefix = params[:q].gsub(/[ \t]+/, '').gsub
    solr = RSolr.connect :url => 'http://localhost:8982/solr'
    
    response = solr.terms({'terms.fl' => 'title_t', 'terms.prefix' => prefix, 'terms.limit' => -1, 'omitHeader' => true})
    response = sort response['terms'][1]

    response = response.map {|term| Recipe.search(term).docs[0].title + "\n"}
    
  	render :text => response
  end
  
  private
  
    def sort(array)
      term_counts = {}
      index = 0
      while index < array.size
        term_counts[array[index]] = array[index + 1]
        index += 2
      end
      
      term_counts = term_counts.sort_by {|term, count| -count}
      array = []
      for term_count_pair in term_counts
        array.push term_count_pair[0]
      end
      
      return array[0, 5]
    end
  
end
