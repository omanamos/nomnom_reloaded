class CreateRecipes < ActiveRecord::Migration
  def self.up
    create_table :recipes do |t|
      t.string :title, :null => false
      t.text :cooking_time
      t.text :directions, :null => false
      t.text :description

      t.timestamps
    end
  end

  def self.down
    drop_table :recipes
  end
end
