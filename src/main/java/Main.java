import image.ImageUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import neuralnet.Network;
import realtime.NNSkinFilter;
import realtime.NNSkinFilterVideo;
import realtime.Region;
import realtime.SkinLocalizer;

public class Main {

	public static void main(String[] args) {
		String runType = args[0];
		Properties properties = new Properties();
		InputStream inputStream;
		String networkPath = "";
		double threshold = 0;
		try {
			inputStream = new FileInputStream(args[1]);
			properties.load(inputStream);
			networkPath = (String) properties.get("networkPath");
			threshold = Double.parseDouble((String) properties.get("threshold"));
			
			if(runType.equals("video")){
				new NNSkinFilterVideo(networkPath, threshold);
			} else if(runType.equals("image")){
				
				String inputImagePath = args[2];	
				Network skinNN = Network.createNetworkFromFile(networkPath);
	            BufferedImage image = ImageUtils.getBufferedImage(inputImagePath);
	            BufferedImage image0 = ImageUtils.getBufferedImage(inputImagePath);
	            NNSkinFilter skin = new NNSkinFilter(skinNN, threshold, image.getWidth(), image.getHeight());
	            System.out.println("Filtering..");
				image = skin.filterSkinPixels(image);
	            System.out.println("Localising..");
	            SkinLocalizer boxer = new SkinLocalizer(image.getWidth(), image.getHeight());
	            boxer.setParameters(5, 10);
	            Set<Region> regions = boxer.localize(image);

	            System.out.println("Drawing regions..");
	            Iterator<Region> iter = regions.iterator();
	            Graphics graph = image0.getGraphics();
	            graph.setColor(Color.green);
	            while(iter.hasNext()){
	                Region region = (Region) iter.next();
	                graph.drawRect(region.x, region.y, region.width, region.height);
	            }
	            System.out.println("Drawing image");
				ImageUtils.saveImage(image0, "/home/deansmiller/Dropbox/Research/NNSkinFilter/test.jpg");
			    System.out.println("Completed..");

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		


	}

}
