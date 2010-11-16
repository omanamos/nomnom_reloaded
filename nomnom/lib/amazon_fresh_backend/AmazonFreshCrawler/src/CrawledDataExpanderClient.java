import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

public class CrawledDataExpanderClient {

	public static final String ARC_FILES = "/Users/aryan/Desktop/crawled_data";
	public static final String HTML_FILES = "/Users/aryan/Desktop/extracted_html";
	
	//public static final String SOLR_URL = "http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr/";

	public static void main(String[] args) throws IOException,
			SolrServerException {

		CrawledDataExpander e = new CrawledDataExpander(ARC_FILES, HTML_FILES);
		
		while (e.hasNext()) {
			e.expandNext();
		}

		/*ProductInfoExtractor p = new ProductInfoExtractor(HTML_FILES);
		List<SolrInputDocument> docs = p.getSolrDocs();

		ProductIndexer i = new ProductIndexer(SOLR_URL);
		i.index(docs);
*/
		

	}

}
