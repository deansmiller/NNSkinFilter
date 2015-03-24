package image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;


public class ImageFolderProcessor {
	
	private List<File> files;
	
	public ImageFolderProcessor(String directory){
		files = Arrays.asList(new File(directory).listFiles());
	}
	
	
	public void process(ImageOperation operation) throws Exception  {
		for(File file : files){
			System.out.println("Processing: " + file.getPath());
			operation.execute(file);
		}
	}

}
