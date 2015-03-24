package readers;

import image.ImageUtils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import javax.imageio.ImageIO;

import neuralnet.Data;

public class ImageReader implements Callable<Data> {
	
	protected BufferedImage oImage, rImage;
	protected int scaledWidth = 0, scaledHeight = 0;
	protected double[][] data;

	public ImageReader(String path) throws IOException {
		setPath(path);
		System.out.println("Warning: No scales set");
	}
	
	public ImageReader() {
		System.out.println("Warning: No scales set");
	}
	
	public void setPath(String path) throws IOException {
		Image image = ImageIO.read(new File(path));
		oImage = (BufferedImage) image;

	}
	
	public void setScale(int width, int height){
		scaledWidth = width;
		scaledHeight = height;
	}
	
	
	public Data read(){
		if(scaledWidth != 0 && scaledHeight != 0) rImage = ImageUtils.scaleImage(oImage, scaledWidth, scaledHeight);
		data = new double[scaledWidth][scaledHeight];
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				data[w][h] = rImage.getRGB(w, h);
			}
		}
		return new Data(data, scaledWidth, scaledHeight);
	}

	@Override
	public Data call() throws Exception {
		return read();
	}
	
//	public void print(){
//		for(int h = 0; h < scaledHeight; h++){
//			for(int w = 0; w < scaledWidth; w++){
//				System.out.print(data[w][h] + ",");
//			}
//			System.out.println();
//		}	
//	}
	
	
}
