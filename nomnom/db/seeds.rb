# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ :name => 'Chicago' }, { :name => 'Copenhagen' }])
#   Mayor.create(:name => 'Daley', :city => cities.first)
#Ingredient.create(:recipe => x, :item => "cat", :amount => "12 huge ones")
#Ingredient.create(:recipe => x, :item => "salt", :amount => "a pinch")
#Ingredient.create(:recipe => x, :item => "water", :amount => "1 cup")
#x = Recipe.create(:title => "Eggplant and Roasted Garlic Babakanoosh", :description => "A Middle Eastern dip for crudites, pita crisps or [[romaine lettuce]] leaves!", :directions => "#On a gas or preferably charcoal barbecue, roast the whole Eggplant evenly on all sides until the skin is charred or the Eggplant is soft. Set aside and let cool. Peel off charred skin,or scoop out the soft insides of the Eggplant and place in a large bowl. #Add garlic, olive oil, Onion, parsley, basil,  Sauce, salt and pepper to taste. #Serve as a canape', or serve with Armenian cracker bread, as a vegetable dip, or as a vegetable side dish. Can be served hot or cold.")
#Ingredient.create(:recipe => x, :item => "large head garlic", :amount => "1")
#Ingredient.create(:recipe => x, :item => "large eggplants", :amount => "3")
#Ingredient.create(:recipe => x, :item => "medium vidalia", :amount => "1")

require "xmlsimple"
require "pp"

RECIPES_PATH = "lib/xml/ParseRecipes/recipes_xml"

Dir.foreach(RECIPES_PATH) do |file|
	next if file == "." or file == ".." or file == ".DS_Store"
	# CGI::unescapeHTML(xml.elements[1].to_s)
	filepath = RECIPES_PATH + "/" + file
	#puts filepath
	xml = XmlSimple.xml_in(filepath)
	
	if(xml['ingredients'][0]['ingredient'] != nil)
		title = xml['title']
		description = xml['description']
		directions = xml['directions']
		puts xml['id']
		recipe = Recipe.create(:title => title, :description => description, :directions => directions)
		if (recipe) then
			ingredients = xml['ingredients']
			ingredients[0]['ingredient'].each do |ingr|
				puts ingr.inspect
				item = ingr['item']
				amount = ingr['amount']
				ingredient = Ingredient.create(:recipe => recipe, :item => item, :amount => amount)
			end
		end
	end
end

