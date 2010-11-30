import java.io.IOException;
import java.net.MalformedURLException;

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

	public void index(SolrInputDocument doc) throws SolrServerException,
			IOException {
		SolrInputField s = doc.getField("asin");
		solrServer.deleteById((String) s.getValue());
		System.out.print("Removing item with ASIN " + s.getValue());
		System.out.println(" (if it exists).");

		try {
			System.out.println("Indexing " + doc);
			solrServer.add(doc);

		} catch (IOException e) {
			solrServer.rollback();
		}

	}
	
	public void commit() {
		try {
			solrServer.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void optimize() {
		try {
			solrServer.optimize();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void removeAll() throws SolrServerException, IOException {
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
		System.exit(1);
	}

}
