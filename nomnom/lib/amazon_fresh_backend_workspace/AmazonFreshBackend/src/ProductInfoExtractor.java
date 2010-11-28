import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.common.SolrInputDocument;

/**
 * ProductInfoExtractor accepts a path to a directory containing HTML files from
 * AmazonFresh product pages and extracts pertinent attributes from each HTML
 * file. The attributes include ASIN's, product names, and prices. The output is
 * is stored in SolrInputDocument objects which are then serialized for later
 * use.
 * 
 * @author Aryan naraghi (aryan@cs.washington.edu)
 */
public class ProductInfoExtractor {

	private File[] productFiles;
	private String serializationPath;

	/**
	 * Constructs a new ProductInfoExtractor.
	 * 
	 * @param pathToData
	 *            The directory containing the HTML files
	 * @param serializationPath
	 *            The directory where the serialized objects will be stored
	 */
	public ProductInfoExtractor(String pathToData, String serializationPath) {
		File dataDir = new File(pathToData);
		if (!dataDir.isDirectory()) {
			throw new IllegalArgumentException(
					"The provided path does not correspond to a folder!");
		}

		this.productFiles = dataDir.listFiles();
		this.serializationPath = serializationPath;
	}

	/**
	 * Returns a new SolrInputDocument that contains the given attributes. If
	 * any of the attributes are null (or -1 for price), then a null value is
	 * returned. It is up to the client of this function to check for null
	 * SolrInputDocuments.
	 * 
	 * @param asin
	 *            The ASIN associated with the item
	 * @param simpleProductName
	 *            The item's simple name
	 * @param additionalProductInfo
	 *            Any additional information contained in the item's title
	 * @param priceInCents
	 *            The price of the item in cents
	 * @return A new SolrInputDocument
	 */
	private SolrInputDocument preapreSolrDoc(String asin,
			String simpleProductName, String[] additionalProductInfo,
			int priceInCents, List<String> similarItems) {

		// Check the input and if there is anything wrong, return null.
		if (asin == null || simpleProductName == null
				|| additionalProductInfo == null || priceInCents == -1) {
			return null;
		}

		SolrInputDocument doc = new SolrInputDocument();

		doc.addField("asin", asin);
		doc.addField("simpleProductName", simpleProductName);
		doc.addField("priceInCents", priceInCents);

		for (String productInfo : additionalProductInfo) {
			doc.addField("additionalProductInfo", productInfo);

		}

		for (String similarItem : similarItems) {
			doc.addField("similarItems", similarItem);
		}

		return doc;

	}

	/**
	 * Processes every file in the given folder and extracts the necessary
	 * information (ASIN, product title, price, etc...). This information is
	 * serialized for later retrieval.
	 * 
	 * @throws IOException
	 */
	public void getSolrDocs() throws IOException {

		Pattern asinPattern = Pattern.compile(".*<strong>ASIN:</strong>.*");
		Pattern productNamePattern = Pattern.compile(".*<h1>.*</h1>.*");
		Pattern pricePattern = Pattern.compile(".*class=\"value\".*");
		Pattern similarItemsPattern = Pattern.compile(".*browseNode.*");

		Matcher asinMatcher = asinPattern.matcher("");
		Matcher productNameMatcher = productNamePattern.matcher("");
		Matcher similarItemsMatcher = similarItemsPattern.matcher("");

		int count = 0;

		for (File file : this.productFiles) {

			FileInputStream fis = new FileInputStream(file);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Matcher priceMatcher = pricePattern.matcher("");

			String line = null;

			String asin = null;
			String simpleProductName = null;
			String[] additionalProductInfo = null;
			List<String> similarItems = new ArrayList<String>();
			int priceInCents = -1;

			while ((line = in.readLine()) != null) {

				asinMatcher.reset(line);
				productNameMatcher.reset(line);
				similarItemsMatcher.reset(line);

				if (asinMatcher.matches()) {
					asin = getAsin(line);
				} else if (productNameMatcher.matches()) {
					String productName = getProductName(line);

					String[] parts = productName.split(",[ \t]*");
					simpleProductName = parts[0];

					additionalProductInfo = new String[parts.length - 1];
					for (int i = 0; i < additionalProductInfo.length; i++) {
						additionalProductInfo[i] = parts[i + 1];
					}

				} else if (priceMatcher != null) {
					priceMatcher.reset(line);

					if (priceMatcher.matches()) {

						priceInCents = getPrice(line);

						// Set price matcher to null to avoid extracting price
						// information for other products listed at the bottom
						// of the page.
						priceMatcher = null;
					}
				} else if (similarItemsMatcher.matches()) {
					similarItems.add(stripHtml(line));
				}

			}

			SolrInputDocument doc = preapreSolrDoc(asin, simpleProductName,
					additionalProductInfo, priceInCents, similarItems);

			System.out.println(++count + ": Extracted " + doc);
			if (doc != null) {
				serialize(doc);
				System.out.println(count + ": Serialized!");
			}
		}
	}

	/**
	 * Serializes the given SolrInputDocument.
	 */
	private void serialize(SolrInputDocument doc) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;

		try {
			fos = new FileOutputStream(serializationPath + "/"
					+ doc.getFieldValue("asin") + ".ser");
			out = new ObjectOutputStream(fos);
			out.writeObject(doc);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return The ASIN from the line containing it
	 */
	private String getAsin(String line) {
		return stripHtml(line).replaceFirst("ASIN:", "").trim();
	}

	/**
	 * @return The product name from the line containing it
	 */
	private String getProductName(String line) {
		return stripHtml(line);
	}

	/**
	 * @return The price in cents from the line containing it
	 */
	private int getPrice(String line) {
		return (int) (Float.parseFloat(stripHtml(line).replaceFirst("\\$", "")) * 100);
	}

	/**
	 * Strips out the HTML and all leading and trailing white space from the
	 * given input.
	 * 
	 * @param input
	 *            The input to sanitize
	 * @return The result of sanitizing the input
	 */
	private String stripHtml(String input) {
		return input.replaceAll("\\<.*?\\>", "").trim();
	}

}
