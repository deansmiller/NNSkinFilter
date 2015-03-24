package realtime;

import image.ImageUtils;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import neuralnet.Network;

public class NNSkinFilter {
	
	private BufferedImage oImage, rImage;
	private int scaledWidth = 480, scaledHeight = 480;
	private Network nn;
	private File imgFile;
	private String targetDirectory;
	private final int RED = 0, GREEN = 1, BLUE = 2, RGB = 3;
	private double threshold = 0.65;
	private Color tmpColor;
	private double[] input = new double[RGB];
	
	public NNSkinFilter(Network nn, double threshold, String path, int scaleWidth, int scaleHeight) throws Exception{
		imgFile = new File(path);
		this.nn = nn;
		this.threshold = threshold;
		this.scaledWidth = scaleWidth;
		this.scaledHeight = scaleHeight;
		if(!imgFile.isDirectory()){
			Image image = ImageIO.read(imgFile);
			oImage = (BufferedImage) image;
		} else {
			handleMultipleFiles(imgFile);
		}
	}
	
	public NNSkinFilter(Network nn, double threshold, int scaleWidth, int scaleHeight) throws Exception {
		this.nn = nn;
		this.threshold = threshold;
		this.scaledWidth = scaleWidth;
		this.scaledHeight = scaleHeight;		
	}
	
	private void handleMultipleFiles(File file) throws Exception{
		List<File> files = Arrays.asList(file.listFiles());
		for(File f : files){
			System.out.println("Processing: " + f);
			targetDirectory = f.getParent() + "/_filtered_" + f.getName();
			Image image = ImageIO.read(f);
			oImage = (BufferedImage) image;
			filterSkinPixels();
		}
		System.out.println("Completed multiple file filter process");
	}
	
	public void setTargetDirectory(String directory){
		this.targetDirectory = directory;
	}
	
	
	public void filterSkinPixels() throws Exception{
		rImage = ImageUtils.scaleImage(oImage, scaledWidth, scaledHeight);
		Color tmpColor;
		double[] input = new double[RGB];
		BufferedImage fImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				tmpColor = new Color(rImage.getRGB(w, h));
				input[RED] = tmpColor.getRed();
				input[GREEN] = tmpColor.getGreen();
				input[BLUE] = tmpColor.getBlue();
				nn.applyInput(input);
				if(nn.getOutput()[0] > threshold){
					fImage.setRGB(w, h, Color.WHITE.getRGB());
				} else fImage.setRGB(w, h, Color.BLACK.getRGB());
			}
		}
		File outputFile = new File(targetDirectory);
	    ImageIO.write(fImage, "jpg", outputFile);
	    System.out.println("Filtered: " + outputFile);
	}
	
	public BufferedImage filterSkinPixels(BufferedImage image) throws Exception {
		BufferedImage output = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		image = ImageUtils.scaleImage(image, scaledWidth, scaledHeight);
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				tmpColor = new Color(image.getRGB(w, h));
				input[RED] = tmpColor.getRed();
				input[GREEN] = tmpColor.getGreen();
				input[BLUE] = tmpColor.getBlue();
				nn.applyInput(input);
				if(nn.getOutput()[0] > threshold){
					output.setRGB(w, h, Color.WHITE.getRGB());
				} else output.setRGB(w, h, Color.BLACK.getRGB());
			}
		}
		return output;
	}

}
