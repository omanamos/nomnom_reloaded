class User < ActiveRecord::Base
	acts_as_authentic
	
	def fl_name
		self.first_name + " " + self.last_name
	end
	
	def lf_name
		self.last_name + ", " + self.first_name
	end
end
