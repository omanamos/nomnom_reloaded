import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;


public class ProductInfoExtractorClient {

	public static void main(String[] args) throws IOException, SolrServerException {
		
		ProductInfoExtractor extractor = new ProductInfoExtractor(args[0], args[1]);
		extractor.getSolrDocs();
		
	}
	
}
