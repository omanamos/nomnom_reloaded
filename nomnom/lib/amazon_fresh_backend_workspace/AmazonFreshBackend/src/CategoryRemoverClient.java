import java.io.IOException;
import java.util.Scanner;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;

/**
 * CategoryRemoverClient is a command-line utility for removing user-specified
 * categories from the Solr index.
 * 
 * @author Aryan Naraghi (aryan@cs.washington.edu)
 */
public class CategoryRemoverClient {

	public static void main(String[] args) throws SolrServerException,
			IOException {
		String solrUrl = args[0];
		SolrServer solrServer = new CommonsHttpSolrServer(solrUrl);

		Scanner input = new Scanner(System.in);

		for (;;) {
			System.out.print("Type in the category you would like to remove: ");
			String category = input.nextLine();
			solrServer.deleteByQuery("similarItems:\"" + category + "\"");

			if (category.isEmpty()) {
				break;
			}

		}

		System.out.print("Type cha-cha-slide to commit: ");
		if (input.nextLine().equals("cha-cha-slide")) {
			solrServer.commit();
		} else {
			solrServer.rollback();
		}
	}

}
