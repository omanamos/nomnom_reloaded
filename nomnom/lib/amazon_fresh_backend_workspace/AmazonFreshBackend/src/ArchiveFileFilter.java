import java.io.File;
import java.io.FileFilter;

public class ArchiveFileFilter implements FileFilter {

	/**
	 * The extension of the ARC files. This should not be changed!
	 */
	private final String ARCHIVE_EXTENSION = ".arc.gz";
	
	@Override
	public boolean accept(File fileName) {
		return fileName.isFile() && fileName.getName().endsWith(ARCHIVE_EXTENSION);
	}

}
