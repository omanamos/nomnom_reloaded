import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.solr.common.SolrInputDocument;

/**
 * ProductInfoExtractor accepts a path to a directory containing HTML files from
 * AmazonFresh product pages and extracts pertinent attributes from each HTML
 * file. The attributes include ASIN's, product names, and prices. The output is
 * presented in a List of SolrInputDocuments which allows for easy indexing into
 * a Solr index.
 * 
 * @author Aryan naraghi (aryan@cs.washington.edu)
 */
public class ProductInfoExtractor {

	private File[] productFiles;

	/**
	 * Constructs a new ProductInfoExtractor.
	 * 
	 * @param pathToData
	 */
	public ProductInfoExtractor(String pathToData) {
		File dataDir = new File(pathToData);
		if (!dataDir.isDirectory()) {
			throw new IllegalArgumentException(
					"The provided path does not correspond to a folder!");
		}

		UuidFileFilter uuidFilter = new UuidFileFilter();
		this.productFiles = dataDir.listFiles(uuidFilter);
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
			int priceInCents) {

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

		return doc;

	}

	/**
	 * Processes every file in the given folder and extracts the necessary
	 * information (ASIN, product title, price, etc...).
	 * 
	 * @return A List of SolrInputDocuments.
	 * @throws IOException
	 */
	public List<SolrInputDocument> getSolrDocs() throws IOException {

		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

		Pattern asinPattern = Pattern.compile(".*<strong>ASIN:</strong>.*");
		Pattern productNamePattern = Pattern.compile(".*<h1>.*</h1>.*");
		Pattern pricePattern = Pattern.compile(".*class=\"value\".*");

		Matcher asinMatcher = asinPattern.matcher("");
		Matcher productNameMatcher = productNamePattern.matcher("");

		for (File file : this.productFiles) {
			FileInputStream fis = new FileInputStream(file);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			Matcher priceMatcher = pricePattern.matcher("");

			String line = null;

			String asin = null;
			String simpleProductName = null;
			String[] additionalProductInfo = null;
			int priceInCents = -1;

			while ((line = in.readLine()) != null) {

				asinMatcher.reset(line);
				productNameMatcher.reset(line);

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
				}

			}

			SolrInputDocument doc = preapreSolrDoc(asin, simpleProductName,
					additionalProductInfo, priceInCents);

			// Ensures that documents that are ill-formatted are not added to
			// the final list of input documents.
			if (doc != null) {
				docs.add(doc);
			}

		}

		return docs;
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
