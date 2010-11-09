class CartController < ApplicationController
	def index
		
	end
	
	def show
		
	end
	
	def create
		items = params[:items]
		i = 0
		while i < items.length
			url = 'http://ecs.amazonaws.com/onca/xml?Service=AWSECommerceService&AWSAccessKeyId=AKIAIUHG5WK4HL2CUWUA&Operation=CartCreate'
		
			for n in i..([i + 9, i - items.length].min)
				url += '&Item.#{i+1}.ASIN=#{item[i]["asin"]}&Item.#{i+1}.Quantity=#{item[i]["quantity"]}'
			end
			response = Net::HTTP.get(URI.parse(url))
		end
	end
	
	def edit
		
	end
	
	def update
		
	end
	
	def destroy
		
	end
end
