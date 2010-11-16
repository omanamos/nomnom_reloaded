import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;

/**
 * 
 * @author Aryan Naraghi (aryan@cs.washington.edu)
 */
public class ProductIndexer {

	private SolrServer solrServer;
	
	public ProductIndexer(String solrUrl) throws MalformedURLException {
		this.solrServer = new CommonsHttpSolrServer(solrUrl);
	}
	
	public void index(List<SolrInputDocument> docs) throws SolrServerException, IOException  {
		for (SolrInputDocument doc : docs) {
			SolrInputField s = doc.getField("asin");
			solrServer.deleteById((String) s.getValue());
			System.out.println("Indexing " + doc);
		}
		
		try {
			solrServer.add(docs);
			solrServer.commit();
		} catch (IOException e) {
			System.out.println("Problem!");
			solrServer.rollback();
		}
		
	}
	
}
