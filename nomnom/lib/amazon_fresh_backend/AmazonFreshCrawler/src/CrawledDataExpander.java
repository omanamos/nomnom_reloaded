import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.archive.io.ArchiveRecord;
import org.archive.io.arc.ARCReader;
import org.archive.io.arc.ARCReaderFactory;

/**
 * CrawledDataExpander is a utility that can expand entries in ARC files into
 * smaller files where each file represents one entry. ARC files are the output
 * of Heritrix's crawling process.
 * 
 * You can learn more about ARC files at:
 * http://www.archive.org/web/researcher/ArcFileFormat.php
 * 
 * @author Aryan Naraghi (aryan@cs.washington.edu)
 */
public class CrawledDataExpander {

	private String pathToArchives;
	private String destinationFolder;

	private File[] archiveFiles;
	private int currentIndex;

	private final static String PRODUCT_URL_REGEX = ".*product[?]asin=[A-Z0-9]{10}.*";

	private final String MIME_TYPE = "text/html";

	/**
	 * Constructs a new CrawledDataExpander.
	 * 
	 * @param pathToArchives
	 *            The path to the folder containing the ARC files
	 * @param destinationFolder
	 *            The path to the folder where the entries from the ARC files
	 *            should be written
	 */
	public CrawledDataExpander(String pathToArchives, String destinationFolder) {
		this.pathToArchives = pathToArchives;
		this.destinationFolder = destinationFolder;

		File archiveFolder = new File(pathToArchives);
		if (!archiveFolder.isDirectory()) {
			throw new IllegalArgumentException("Given path is not a directory!");
		}

		// Eliminates any file that does not end with the correct extension.
		ArchiveFileFilter archiveFilter = new ArchiveFileFilter();
		this.archiveFiles = archiveFolder.listFiles(archiveFilter);

		this.currentIndex = 0;
	}

	/**
	 * @return True if there is an archive remaining that can be expanded
	 */
	public boolean hasNext() {
		return this.currentIndex < this.archiveFiles.length;

	}

	/**
	 * Expands the next ARC file in the set of ARC files. A file is created for
	 * each entry in the ARC file and is given a UUID name. It is the client's
	 * responsibility to ensure that hasNext() returns true.
	 * 
	 * @throws IllegalStateException
	 *             If called when there is no ARC file left to expand
	 * @throws IOException
	 */
	public void expandNext() throws IOException {
		if (!hasNext()) {
			throw new IllegalStateException(
					"There are no more archives to expand!");
		}

		expand(this.archiveFiles[this.currentIndex++]);
	}

	/**
	 * Expands the given ARC file by writing each entry that corresponds to a
	 * product to a separate file with a UUID name.
	 * 
	 * @param archive
	 *            The File object representing the ARC file
	 * @throws IOException
	 */
	private void expand(File archive) throws IOException {
		ARCReader arc = ARCReaderFactory.get(archive);

		Iterator<ArchiveRecord> i = arc.iterator();

		// Strips out the meta-data from the archive.
		if (i.hasNext()) {
			i.next();
		}

		// Iterates over the entries and for each entry, generates an
		// appropriate file.
		while (i.hasNext()) {
			String fileName = UUID.randomUUID().toString();
			OutputStream out = new FileOutputStream(destinationFolder + "/"
					+ fileName);

			ArchiveRecord record = i.next();
			String url = record.getHeader().getUrl();
			String mime = record.getHeader().getMimetype();
			Pattern productUrl = Pattern.compile(PRODUCT_URL_REGEX);
			Matcher urlMatcher = productUrl.matcher(url);

			// Sanity check to verify that each document is text/html and that
			// the URL matches the pattern for a product URL.
			if (mime.equals(MIME_TYPE) && urlMatcher.matches()) {
				record.dump(out);
			}
			out.flush();

		}
	}

	/**
	 * @return The path to the folder containing the archives
	 */
	public String getPathToArchives() {
		return pathToArchives;
	}

	/**
	 * @return The destination folder
	 */
	public String getDestinationFolder() {
		return destinationFolder;
	}

}
