import java.io.*;
import java.text.*;
import java.util.*;


public class Recipe {
	
	private static final String FILE_PATH = "recipes_xml/";
	
	private String title;
	private String description;
	private String directions;
	private Date timestamp;
	private int id;
	private List<Ingredient> ingredients;
	
	public Recipe(String title, String description, String directions, int id) {
		this.title = title;
		this.id = id;
		this.description = description;
		this.directions = "";
		ingredients = new ArrayList<Ingredient>();
	}
	
	public Recipe(String title) {
		this(title, "", "", 0);
	}
	
	public Recipe() {
		this("", "", "", 0);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public boolean setTimestamp(String s) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		try {
			this.timestamp = formatter.parse(s, new ParsePosition(0));
		} catch (Exception e) {
			System.out.println("couldn't parse Date: " + s);
			return false;
		}
		return true;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public String getDescription() {
		return cleanData(description);
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDirections() {
		return directions;
	}
	
	public void setDirections(String directions) {
		this.directions = cleanData(directions);
	}
	private String cleanData(String s) {
		return s.replaceAll("\\[\\[|\\]\\]", "").replaceAll("\\s{1,}", " ").trim();
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public boolean addIngredient(String item) {
		Ingredient ingr = Ingredient.parseIngredient(item);
		if (ingr != null) {
			ingredients.add(Ingredient.parseIngredient(item));
			return true;
		}
		return false;
	}
	
	public String toString() {
		String ingredientsList = "";
		for (Ingredient s : ingredients) {
			ingredientsList += s;
		}
		return "<recipe>\n" +
					"\t<title>" + title + "</title>\n" + 
					"\t<id>" + id + "</id>\n" + 
					"\t<timestamp>" + timestamp.toString() + "</timestamp>\n" + 
					"\t<description>" + description + "</description>\n" + 
					"\t<ingredients>\n" + ingredientsList + "\t</ingredients>\n" + 
					"\t<directions>" + directions + "</directions>\n" +
				"</recipe>";
	}
	
	public void writeToIdFile() {
		writeToFile(FILE_PATH + this.id + ".xml");
	}
	
	public void writeToFile(String fileName) {
		try {
		PrintStream out = new PrintStream(new File(fileName));
		out.print(this.toXML());
		} catch(IOException e) {
			System.out.println("Could not print \"" + title + "\" to file " + fileName);
		}
	}
	
	public String toXML() {
		return "<?xml version=\"1.0\"?>\n" + toString();
	}
	
	public void writeToDatabase() {
		
	}
}