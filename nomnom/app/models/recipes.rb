class Recipes < ActiveRecord::Base
	validates_presence_of :title, :directions
	acts_as_solr
end
