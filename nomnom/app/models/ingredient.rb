class Ingredient < ActiveRecord::Base
	validates_presence_of :recipe, :item, :amount
	acts_as_solr
	belongs_to :recipe
end
