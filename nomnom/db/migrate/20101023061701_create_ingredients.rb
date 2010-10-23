class CreateIngredients < ActiveRecord::Migration
  def self.up
    create_table :ingredients do |t|
   		t.references :recipe, :null => false
    	t.text :content, :null => false

     	t.timestamps
    end
  end

  def self.down
    drop_table :ingredients
  end
end
