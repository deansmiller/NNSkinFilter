package realtime;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import image.ImageUtils;


//For localising skin for hand detection and classification for NN
public class SkinLocalizer {
	
	private ArrayList<Region> rectangles = new ArrayList<Region>();
	private ArrayList<Region> whiteRects = new ArrayList<Region>();
	private ArrayList<Region> blackRects = new ArrayList<Region>();
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Region> thresholdedRegions = new ArrayList<Region>();
	private Region window;
	private int scaledWidth, scaledHeight;
	private int WINDOW_SIZE = 5; // x by y size of window
	private int THRESHOLD = 20; //how many white pixels per window
	
	
	public SkinLocalizer(int scaledWidth, int scaledHeight){
		this.scaledWidth = scaledWidth;
		this.scaledHeight = scaledHeight;
	}
	
	public void setParameters(int windowSize, int threshold){
		WINDOW_SIZE = windowSize;
		THRESHOLD = threshold;
	}
	
	// Returns a list of regions that contain that only have neighbouring regions
	private ArrayList<Region> calculateRegionNeighbours(){
		ArrayList<Region> regions = new ArrayList<Region>();
		for(Region r1 : thresholdedRegions){
			for(Region r2 : thresholdedRegions){
				if(r1.isNeighbour(r2) && r1 != r2) {
					r1.neighbourCount++;
					r1.hasNeighbour = true;
				}
			}
		}
		
		for(Region r : thresholdedRegions){
			if(r.neighbourCount > 1){ //r.neighbourCount > 0 || r.hasNeighbour
				regions.add(r);
			}
		}
		
		return regions;
	}
	
	
	// create a bounded region of the grouped smaller regions
	private Set<Region> calculateEdges(){
		ArrayList<Region> regions = calculateRegionNeighbours();
		ArrayList<Region> edgeRegions = new ArrayList<Region>();
		
		for(Region region : regions){
			region.joinNeighbours("BOTTOM");
			region.joinNeighbours("TOP");
			region.joinNeighbours("LEFT");
			region.joinNeighbours("RIGHT");
			
			edgeRegions.add(region);
		}

		
		for(Region region1 : edgeRegions){
			for(Region region2 : edgeRegions){
				if(region1.intersects(region2) && region1 != region2) {
					region1.add(region2);
				} 
			}
		}
		
		Set<Region> set = new HashSet<Region>();
		for(Region region : edgeRegions) set.add(region);
		return set;
	}
	

	public Set<Region> localize(BufferedImage image) throws IOException {
		image = ImageUtils.scaleImage(image, scaledWidth, scaledHeight);
		
		// create windows
		for(int w = 0; w < scaledWidth; w += WINDOW_SIZE){
			for(int h = 0; h < scaledHeight; h += WINDOW_SIZE){
				window = new Region(w, h, WINDOW_SIZE, WINDOW_SIZE);
				rectangles.add(window);
			}
		}
		
		// remember all the pixels that are not black, call these white points
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				if(image.getRGB(w, h) != -16777216){ //-16777216 == Color.BLACK.getRGB()
					points.add(new Point(w, h));
				}
			}
		}
		
		// make a note of all the rectangles that contain white points
		for(Point p : points){
			for(Region r : rectangles){
				if(r.contains(p)){
					r.incrementPointCount();
					if(!whiteRects.contains(r)){
						whiteRects.add(r);
					}
				} 
			}
		}		

		// only need to remember the rectangles that have a certain amount of white points
		for(Region r : whiteRects){
			//System.out.println(r.pointCount);
			if(r.pointCount >= THRESHOLD){
				thresholdedRegions.add(r);
			}
		}


		Set<Region> regions = calculateEdges();

		points.clear();
		whiteRects.clear();
		blackRects.clear();
		rectangles.clear();
		thresholdedRegions.clear();
		return regions;		
	}

	
	
	public BufferedImage showoff(BufferedImage image) throws IOException{
		image = ImageUtils.scaleImage(image, scaledWidth, scaledHeight);

		// create windows
		for(int w = 0; w < scaledWidth; w += WINDOW_SIZE){
			for(int h = 0; h < scaledHeight; h += WINDOW_SIZE){
				window = new Region(w, h, WINDOW_SIZE, WINDOW_SIZE);
				rectangles.add(window);
			}
		}

		// remember all the pixels that are not black, call these white points
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				if(image.getRGB(w, h) != -16777216){ //-16777216 == Color.BLACK.getRGB()
					points.add(new Point(w, h));
				}
			}
		}

		// make a note of all the rectangles that contain white points
		for(Point p : points){
			for(Region r : rectangles){
				if(r.contains(p)){
					r.incrementPointCount();
					if(!whiteRects.contains(r)){
						whiteRects.add(r);
					}
				}
			}
		}

		// only need to remember the rectangles that have a certain amount of white points
		for(Region r : whiteRects){
			//System.out.println(r.pointCount);

			if(r.pointCount >= THRESHOLD){
				thresholdedRegions.add(r);
			}
		}

		BufferedImage transformed = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = transformed.createGraphics();
		for(int w = 0; w < scaledWidth; w++){
			for(int h = 0; h < scaledHeight; h++){
				transformed.setRGB(w, h, image.getRGB(w, h));
			}
		}

	    g2.setColor(Color.GREEN);
		Set<Region> regions = calculateEdges();
		Iterator<Region> iterator = regions.iterator();

		while(iterator.hasNext()) g2.draw(iterator.next());

		g2.dispose();
		points.clear();
		whiteRects.clear();
		blackRects.clear();
		rectangles.clear();
		thresholdedRegions.clear();
		return transformed;

	}
	

}
