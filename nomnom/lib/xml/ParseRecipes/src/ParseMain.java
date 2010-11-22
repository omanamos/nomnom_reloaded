import java.io.*;

import javax.xml.parsers.*;

import org.xml.sax.helpers.*;
import org.xml.sax.*;


public class ParseMain {
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		FileInputStream input = new FileInputStream("pages_current.xml");
		
		SAXParser xmlParser = SAXParserFactory.newInstance().newSAXParser();
		
		DefaultHandler handler = new RecipeParseHandler();
		
		xmlParser.parse(input, handler);
	}
}