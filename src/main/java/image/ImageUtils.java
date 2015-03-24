package image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Jama.Matrix;

public class ImageUtils {
	
	
	public static BufferedImage copyImage(BufferedImage image){
		BufferedImage copied = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for(int w = 0; w < image.getWidth(); w++){
			for(int h = 0; h < image.getHeight(); h++){
				copied.setRGB(w, h, image.getRGB(w, h));
			}
		}
		return copied;
	}
	
	public static Matrix toMatrix(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		int r, g, b;
		double[][] data = new double[width][height];
		for(int h = 0; h < width; h++){
			for(int w = 0; w < height; w++){
	            r = new Color(image.getRGB(h, w)).getRed();
	            g = new Color(image.getRGB(h, w)).getGreen();
	            b = new Color(image.getRGB(h, w)).getBlue();
				data[h][w] = ((r + g + b) / 3); 
			}
		}
		return new Matrix(data);
	}
	
	public static BufferedImage getBufferedImage(String path) throws IOException{
		Image image = ImageIO.read(new File(path));
		return (BufferedImage) image;	
	}
	
	public static BufferedImage getBufferedImage(File file) throws IOException{
		Image image = ImageIO.read(file);
		return (BufferedImage) image;	
	}
	
	public static void saveImage(BufferedImage image, String path) throws IOException{
		File output = new File(path);
	    ImageIO.write(image, "jpg", output);
	}
	
	
	public static void greyScaleImage(BufferedImage toGrey, String directory) throws IOException{
		int width = toGrey.getWidth(), height = toGrey.getHeight();
		BufferedImage tempImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	    Graphics2D g2 = tempImage.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(toGrey, 0, 0, width, height, null);
	    g2.dispose();
		File outputFile = new File(directory);
	    ImageIO.write(tempImage, "jpg", outputFile);
	}
	
	 public static BufferedImage greyScale(BufferedImage src) {
	    BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
	    return op.filter(src, null);
	 }
	 
	
	public static void scaleImage(BufferedImage toScale, String directory, int width, int height) throws IOException{
		BufferedImage tempImage  = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	    Graphics2D g2 = tempImage.createGraphics();
	    g2.drawImage(toScale, 0, 0, width, height, null);
	    g2.dispose();
		File outputFile = new File(directory);
	    ImageIO.write(tempImage, "jpg", outputFile);
	}
	
	
	
	public static BufferedImage scaleImage(BufferedImage toScale, int scaleWidth, int scaleHeight){
		BufferedImage tempImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = tempImage.createGraphics();
	    g2.drawImage(toScale, 0, 0, scaleWidth, scaleHeight, null);
	    g2.dispose();
	    return tempImage;
	}
	
	public static BufferedImage scaleGreyImage(BufferedImage toScale, int scaleWidth, int scaleHeight){
		BufferedImage tempImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_BYTE_GRAY);		
	    Graphics2D g2 = tempImage.createGraphics();
	    g2.drawImage(toScale, 0, 0, scaleWidth, scaleHeight, null);
	    g2.dispose();

	    return tempImage;
	}
	
	

}
