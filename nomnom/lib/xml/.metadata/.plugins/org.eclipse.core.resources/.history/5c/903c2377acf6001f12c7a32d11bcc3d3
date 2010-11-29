import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;

public class CrawledDataExpanderClient {
	
	//public static final String SOLR_URL = "http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr/";

	public static void main(String[] args) throws IOException,
			SolrServerException {

		if (args.length != 2) {
			System.err.print("Usage: CrawledDataExpanderClient ");
			System.err.println("<archive folder> <destination folder>");
			System.exit(1);
		}
		
		CrawledDataExpander expander = new CrawledDataExpander(args[0], args[1]);
		
		while (expander.hasNext()) {
			expander.expandNext();
		}

	}

}
