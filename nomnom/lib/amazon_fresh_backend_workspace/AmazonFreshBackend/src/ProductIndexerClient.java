import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

public class ProductIndexerClient {

	private static final int BATCH_SIZE = 10000;
	
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, SolrServerException {
		
		if (args.length != 2) {
			System.err.print("Usage: ProductIndexerClient ");
			System.err.println("<Solr URL> <serialized docs directory>");
			System.exit(1);
		}

		String solrUrl = args[0];
		String serializedDirectory = args[1];
		
		SolrServer solrServer = new CommonsHttpSolrServer(solrUrl);
		File serializedDocsDirectory = new File(serializedDirectory);
		
		String[] serializedDocs = serializedDocsDirectory.list();
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>(BATCH_SIZE);
		
		for (int i = 0; i < serializedDocs.length; i++) {
			FileInputStream fis = new FileInputStream(serializedDirectory + "/" + serializedDocs[i]);
			ObjectInputStream in = new ObjectInputStream(fis);
			SolrInputDocument doc = (SolrInputDocument) in.readObject();
			System.out.println(i + ": Unserialized " + doc);
			docs.add(doc);
			in.close();
			
			if ((i + 1) % BATCH_SIZE == 0) {
				System.out.print("Adding batch... ");
				solrServer.add(docs);
				solrServer.commit();
				docs = new ArrayList<SolrInputDocument>(BATCH_SIZE);
				System.out.println("Success!");
			}
		}
		
		solrServer.add(docs);
		solrServer.commit();
		solrServer.optimize();

	}
}
