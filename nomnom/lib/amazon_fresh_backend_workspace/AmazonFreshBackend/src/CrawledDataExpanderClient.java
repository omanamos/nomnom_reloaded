import java.io.IOException;

public class CrawledDataExpanderClient {

	// public static final String SOLR_URL =
	// "http://ec2-50-16-26-144.compute-1.amazonaws.com:8984/solr/";

	public static void main(String[] args) throws IOException {

		if (args.length != 3) {
			System.err.print("Usage: CrawledDataExpanderClient ");
			System.err.print("<archive folder> <destination folder> ");
			System.err.println("<num archives to unarchive>");
			System.exit(1);
		}

		CrawledDataExpander expander = new CrawledDataExpander(args[0], args[1]);
		int numArchives = Integer.parseInt(args[2]);

		while (expander.hasNext() && numArchives-- != 0) {
			expander.expandNext();
		}

	}

}
