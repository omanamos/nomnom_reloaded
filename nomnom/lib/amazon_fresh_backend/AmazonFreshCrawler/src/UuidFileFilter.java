import java.io.File;
import java.io.FileFilter;
import java.util.UUID;


public class UuidFileFilter implements FileFilter {

	@Override
	public boolean accept(File fileName) {
		try {
			UUID.fromString(fileName.getName());
		} catch (IllegalArgumentException e) {
			return false;
		}
		
		return fileName.isFile();
	}

}
