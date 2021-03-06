import java.util.*;
import java.util.regex.*;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class RecipeParseHandler extends DefaultHandler {

	public enum Tag {
		PAGE, TITLE, ID, TIMESTAMP, TEXT, DONT_CARE;

		public static Tag val(String s) {
			try {
				return valueOf(s.toUpperCase());
			} catch(Exception e) {
				return DONT_CARE;
			}
		}
	}

	private final static int DEFAULT_BUILDER_CAPACITY = 1000;
	private final static int ID_DEPTH = 3;
	private final static int TIMESTAMP_DEPTH = 4;
	private final static int TEXT_DEPTH = 4;

	private int depth = 0;
	private boolean isPage = false;
	private boolean validPage = false;
	private boolean isTitle = false;
	private boolean isTimestamp = false;
	private boolean isText = false;
	private boolean isID = false;
	private int recipeCount = 0;
	private int totalPages = 0;

	private Recipe doc = null;
	private List<Recipe> recipes = new ArrayList<Recipe>();

	private StringBuilder tagContents = new StringBuilder(DEFAULT_BUILDER_CAPACITY);

	public void startElement(String uri, String localName, String tagName, Attributes attributes) {
		depth++;
		switch (Tag.val(tagName)) {
			case PAGE:
				isPage = true;
			break;
			case TITLE:
				if (isPage) isTitle = true;
			break;
			case ID:
				if (validPage && depth == ID_DEPTH) isID = true;
			break;
			case TIMESTAMP:
				if (validPage && depth == TIMESTAMP_DEPTH) isTimestamp = true;
			break;
			case TEXT:
				if (validPage && depth == TEXT_DEPTH) isText = true;
			break;
			default: break;
		}
	}

	public void characters(char[] ch, int startIndex, int length) {
		if (isTitle || isID || isTimestamp || isText) {
			tagContents.append(ch, startIndex, length);
		}
	}

	public void endElement(String uri, String localName, String tagName) {
		depth--;
		switch (Tag.val(tagName)) {
			case PAGE:
				isPage = false;
				if (validPage) {
					//System.out.println(doc.printDirections());
					if (doc.getIngredients().size() > 0) {
						recipes.add(doc);
						//System.out.println(doc);
						doc.writeToIdFile();
					}
				}
				totalPages++;
				validPage = false;
			break;
			case TITLE:
				isTitle = false;
				validPage = parseTitle();
			break;
			case ID:
				if (validPage && isID) parseID();
				isID = false;
			break;
			case TIMESTAMP:
				if (validPage && isTimestamp) parseTimestamp();
				isTimestamp = false;
			break;
			case TEXT:
				if (validPage && isText) parseText();
				isText = false;
			break;
			default: break;
		}
	}

	public void endDocument() {
		System.out.println("Total pages found: " + totalPages);		
		System.out.println("Total recognized recipes: " + recipeCount);
		System.out.println("Total recipes cleaned and saved: " + recipes.size());
	}

	private List<String> parseList(String s) {
		List<String> list = new ArrayList<String>();
		Scanner lines = new Scanner(s);
		while (lines.hasNextLine()) {
			String item = lines.nextLine();
			int position = item.lastIndexOf("*");
			if (position != -1 && position < item.length() - 1) {
				item = item.split("\\*")[1].trim();
			}
			if (!item.isEmpty()) {
				list.add(item);
			}
		}
		return list;
	}

	private boolean parseText() {
		String text = tagContents.toString();

		//===([^=]).+?===
		//Grab the Ingredients Data
		Pattern splitter = Pattern.compile("[=]{2,3}Ingredients[=]{2,3}", Pattern.MULTILINE);
		String[] peices = splitter.split(text);

		if (peices.length > 1) {
			String ingredientData = peices[1].split("[=]{2,3}")[0];
			List<String> ingredients = parseList(ingredientData);
			for (String ingredient : ingredients) {
				if (validPage && !doc.addIngredient(ingredient)) {
					invalidate();
				}
			}
		} else {
			invalidate();
		}

		//Grab the description data
		splitter = Pattern.compile("[=]{2,3}Description[=]{2,3}", Pattern.MULTILINE);
		peices = splitter.split(text);

		if (peices.length > 1 && validPage) {
			String description = peices[1].split("[=]{2,3}")[0].trim();
			if (validPage && !doc.setDescription(description)) {
				invalidate();
			}
		}

		//Grab the description data
		splitter = Pattern.compile("[=]{2,3}Directions[=]{2,3}", Pattern.MULTILINE);
		peices = splitter.split(text);

		if (peices.length > 1 && validPage) {
			/*
			String ingredientData = peices[1].split("===")[0];
			List<String> ingredients = parseList(ingredientData);
			for (String ingredient : ingredients) {
				doc.addDirection(ingredient);
			}
			*/
			String directions = peices[1].split("[=]{2,3}")[0].trim();
			if (validPage && !doc.setDirections(directions)) {
				invalidate();
			}
		}

		resetBuilder();
		return false;
	}

	private boolean parseTimestamp() {
		String timestamp = tagContents.toString();
		boolean success = doc.setTimestamp(timestamp);
		if (!success) {
			invalidate();
		}
		resetBuilder();
		return success;
	}

	private boolean parseTitle() {
		String title = tagContents.toString();
		boolean result = false;
		if (title.indexOf(":") == -1) {
			recipeCount++;
			doc = new Recipe(title);
			result = true;
		}
		resetBuilder();
		return result;
	}

	private void parseID() {
		String id = tagContents.toString();
		try {
			if (doc.getID() == 0) {
				doc.setID(Integer.parseInt(id.trim()));
			} else {
				System.out.println("Caught other level id");
			}
		} catch (NumberFormatException e) {
			System.out.println(id);
			invalidate();
		}
		resetBuilder();
	}

	private void invalidate() {
		validPage = false;
		doc = null;
	}

	private void resetBuilder() {
		tagContents = new StringBuilder(DEFAULT_BUILDER_CAPACITY);
	}

}