package test;

import realtime.NNSkinFilter;
import realtime.Region;
import realtime.SkinLocalizer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Set;

import neuralnet.Network;
import image.ImageUtils;

public class Test {

	public static void main(String[] args) {
		try {
			Network skinNN = Network.createNetworkFromFile("/home/deansmiller/Dropbox/Research/NNSkinFilter/skinNN.dat");
            BufferedImage image = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/Research/NNSkinFilter/group2.jpg");
            BufferedImage image0 = ImageUtils.getBufferedImage("/home/deansmiller/Dropbox/Research/NNSkinFilter/group2.jpg");
            NNSkinFilter skin = new NNSkinFilter(skinNN, 0.95, image.getWidth(), image.getHeight());
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
