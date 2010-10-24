class Ingredient < ActiveRecord::Base
	validates_presence_of :recipe, :content
	acts_as_solr
	belongs_to :recipe
end
