import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;

public class ArchiveReaderTest {

	public static final String ARC_FILES = "/Users/aryan/Documents/university-of-washington/2010-autumn/cse-454/nomnom_reloaded/nomnom/lib/amazon_fresh_backend/AmazonFreshCrawler/arcs";

	public static void main(String[] args) throws IOException, SolrServerException {

		/*
		 * CrawledDataExpander e = new CrawledDataExpander(ARC_FILES,
		 * "/Users/aryan/Documents/university-of-washington/2010-autumn/cse-454/nomnom_reloaded/nomnom/lib/amazon_fresh_backend/AmazonFreshCrawler/data"
		 * ); while(e.hasNext()) { e.expandNext(); }
		 */

		ProductInfoExtractor p = new ProductInfoExtractor(
				"/Users/aryan/Documents/university-of-washington/2010-autumn/cse-454/nomnom_reloaded/nomnom/lib/amazon_fresh_backend/AmazonFreshCrawler/data");
		List<SolrInputDocument> docs = p.getSolrDocs();

		ProductIndexer i = new ProductIndexer("http://0.0.0.0:8983/solr/");
		i.index(docs);
		
		/*
		String test = "http://fresh.amazon.com/product?asin=B000P6G0YO&sim=Shoveler_mikes_picks&pf_rd_s=top-2&pf_rd_p=1277322322&pf_rd_t=101&pf_rd_i=21&pf_rd_r=12XVTE708ZP62TYPWP1X&pf_ref=mikes_picksItem_0_link";
		Pattern productUrl = Pattern.compile("product[?]asin=[A-Z0-9]{10}");
		Matcher matcher = productUrl.matcher(test);
		System.out.println(matcher.matches());

		
		 * ARCReader arc = ARCReaderFactory.get(new File(ARC_FILE));
		 * 
		 * Iterator<ArchiveRecord> i = arc.iterator();
		 * 
		 * OutputStream out = new FileOutputStream("test.txt");
		 * 
		 * 
		 * while (i.hasNext()) { String fileName = UUID.randomUUID().toString();
		 * 
		 * i.next().dump(out);
		 * 
		 * 
		 * } out.flush();
		 */

	}

}
