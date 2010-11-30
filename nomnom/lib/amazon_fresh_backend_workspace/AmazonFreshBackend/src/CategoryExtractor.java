import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.common.SolrInputDocument;

/**
 * Scans serialized SolrInputDocument objects for the similarItems attributes
 * and writes all categories found into a file sorted in decreasing frequency.
 * 
 * @author Aryan Naraghi (aryan@cs.washington.edu)
 */
public class CategoryExtractor {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {

		String serializedDirectory = args[0];
		File serializedDocsDirectory = new File(serializedDirectory);
		String[] serializedDocs = serializedDocsDirectory.list();

		Map<String, Integer> similarItems = new HashMap<String, Integer>();

		for (int i = 0; i < serializedDocs.length; i++) {
			FileInputStream fis = new FileInputStream(serializedDirectory + "/"
					+ serializedDocs[i]);
			ObjectInputStream in = new ObjectInputStream(fis);
			SolrInputDocument doc = (SolrInputDocument) in.readObject();
			System.out.println(i + ": Unserialized " + doc);

			Collection<Object> docSimilarItems = doc
					.getFieldValues("similarItems");

			if (docSimilarItems != null) {
				for (Object similarItemObj : docSimilarItems) {
					String similarItem = (String) similarItemObj;

					if (similarItems.containsKey(similarItem)) {
						similarItems.put(similarItem,
								similarItems.get(similarItem) + 1);
					} else {
						similarItems.put(similarItem, 1);
					}
				}
			}

			in.close();
		}

		writeMappings(sortMappings(similarItems), args[1]);

	}

	private static void writeMappings(List<Entry<String, Integer>> mappings,
			String filePath) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));

		for (Map.Entry<String, Integer> mapping : mappings) {
			out.write(mapping.getKey());
			out.write('\n');
			out.write(mapping.getValue().toString());
			out.write('\n');
		}
		out.close();
	}

	/**
	 * Sorts the given map based on its values.
	 * 
	 * @param similarItems
	 *            A map of Strings to Integers that is to be sorted by values
	 * @return A List containing Map.Entry's, sorted in non-decreasing order by
	 *         values
	 */
	private static List<Entry<String, Integer>> sortMappings(
			Map<String, Integer> similarItems) {
		List<Entry<String, Integer>> mappings = new ArrayList<Entry<String, Integer>>(
				similarItems.entrySet());

		Collections.sort(mappings, new Comparator<Object>() {

			@SuppressWarnings("unchecked")
			@Override
			public int compare(Object o1, Object o2) {
				return ((Comparable<Integer>) ((Map.Entry<String, Integer>) o2)
						.getValue())
						.compareTo(((Map.Entry<String, Integer>) o1)
								.getValue());
			}

		});

		return mappings;

	}
}
