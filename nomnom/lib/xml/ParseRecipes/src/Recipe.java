import java.io.*;
import java.text.*;
import java.util.*;


public class Recipe {
	
	private static final String FILE_PATH = "/Users/royman/Code/cse454/nomnom_reloaded/nomnom/lib/xml/ParseRecipes/recipes_xml/";
	
	private String title;
	private String description;
	private String directions;
	private Date timestamp;
	private int id;
	private List<Ingredient> ingredients;
	
	public Recipe(String title, String description, String directions, int id) {
		this.title = cleanData(title);
		if (title.isEmpty() || title == null) {
			System.out.println("issue: " + id);
		}
		this.id = id;
		setDescription(description);
		setDirections(directions);
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
	public boolean setDescription(String description) {
		String cleanDescription = cleanData(description);
		if (cleanDescription != null) {
			this.description = cleanDescription.split("Image:")[0].replaceAll("Category:.*(\\n|$)|category:.*(\\n|$)", "").replaceAll("\\*+#*\\s*|#+\\**\\s*", "");
			return true;
		}
		return false;
	}
	public String getDirections() {
		return directions;
	}
	
	public boolean setDirections(String directions) {
		String cleanDirections = cleanData(directions.replace("\n", "*"));
		if (cleanDirections != null) {
			String result = "";
			String[] temp = cleanDirections.replaceAll("\\*+#*\\s*|#+\\**\\s*", "\n").replaceAll("Category:.*(\\n|$)|category:.*(\\n|$)", "").split("\n");
			for (int i = 0; i < temp.length; i++) {
				if (!temp[i].trim().isEmpty()) {
					result += temp[i].trim() + "\n";
				}
			}
			this.directions = result.trim();
			return true;
		}
		return false;
	}
	private String cleanData(String s) {
		String result = "";
		for (char c : s.toCharArray()) {
			if (c < 128) {
				result += c;
			} else {
				return null;
			}
		}
		return result.replaceAll("<br>|<BR>", "\n").replace("&", "&amp;").replaceAll("<.{0,10}>", "").replace("<", "&lt;").replace(">", "&gt;").replace("&nbsp;", " ").replace("[[", "").replace("]]", "").replaceAll("\\s{1,}", " ").replace("\n", "").replace("\t", "").trim();
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
					"\t<timestamp>" + timestamp + "</timestamp>\n" + 
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
			System.out.println(e);
			e.printStackTrace();
			//System.out.println(System.getProperties());
		}
	}
	
	public String toXML() {
		return "<?xml version=\"1.0\"?>\n" + toString();
	}
	
	public String printDirections() {
		return title + "\n" + directions + "\n\n";
	}
	
	//public void writeToDatabase() {
		
	//}
}