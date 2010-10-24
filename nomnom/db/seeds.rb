# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
Recipe.create(:title => "Cat Stew", :directions => "Find an alleycat.\nBoil it in salty, salty water.\nThen bake until golden brown.\nMmm…cat!")
Ingredient.create(:recipe => Recipe.all[0], :content => "cat")
Ingredient.create(:recipe => Recipe.all[0], :content => "salt")
Ingredient.create(:recipe => Recipe.all[0], :content => "water")