class Ingredients < ActiveRecord::Base
	validates_presence_of :recipe, :content
	acts_as_solr
end
