class Recipe < ActiveRecord::Base
	validates_presence_of :title, :directions
	acts_as_solr :fields => [:title]
	has_many :ingredients
end
