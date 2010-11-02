import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ProductInfoExtractor {

	private File[] productFiles;
	
	public ProductInfoExtractor(String pathToData) {
		File dataDir = new File(pathToData);
		if (!dataDir.isDirectory()) {
			throw new IllegalArgumentException("The provided path does not correspond to a folder!");
		}
		
		UuidFileFilter uuidFilter = new UuidFileFilter();
		this.productFiles = dataDir.listFiles(uuidFilter);
	}
	
	public void processData() {
		for (File i : this.productFiles) {
			extractAttributes(i);
			System.out.println(i.toString());
			
		}
	}

	
	private void extractAttributes(File dataFile) {
		
		try {
			Scanner s = new Scanner(dataFile);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);				
			}
			
			
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
}
