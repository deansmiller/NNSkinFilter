package realtime;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class Region extends Rectangle {
	
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String TOP = "top";
	public static final String BOTTOM = "bottom";
	
	private Map<String, Region> neighbourMap = new HashMap<String, Region>();
	private boolean joined = false;
	public int pointCount;
	public int neighbourCount = 0;
	public boolean hasNeighbour = false;
	public Region leftNeighbour = null, rightNeighbour = null, topNeighbour = null, bottomNeighbour = null;
	
	public Region(int pointCount){
		this.pointCount = pointCount;
	}

	public Region() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Region(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	public Region(Rectangle r) {
		super(r);
		// TODO Auto-generated constructor stub
	}
	
	public void incrementPointCount(){
		pointCount++;
	}
	
	public boolean isNeighbour(Region r){
		boolean neighbour = false;

		if(this.x - this.width == r.x && this.y == r.y){ //left neighbour
			neighbour = true;
//			leftNeighbour = r;
			neighbourMap.put("LEFT", r);
		}
		
		if(this.x + this.width == r.x && this.y == r.y){ //right neighbour
			neighbour = true;
//			rightNeighbour = r;
			neighbourMap.put("RIGHT", r);
		}
		
		if(this.y - this.width == r.y && this.x == r.x){ //top neighbour
			neighbour = true;
//			topNeighbour = r;
			neighbourMap.put("TOP", r);
		}
		
		if(this.y + this.width == r.y && this.x == r.x){ //bottom neighbour
			neighbour = true;
//			bottomNeighbour = r;
			neighbourMap.put("BOTTOM", r);
		}

		return neighbour;
	}
	
	public void joinNeighbours(String direction){
		Region r = joinNeighbour(direction);
		if(r != null && !joined) {
			add(r);
			joined = true;
		}
	}
	
	private Region joinNeighbour(String direction){
		//System.out.println("Chain: " + neighbourMap.get(direction));
		if(neighbourMap.get(direction) != null) {
			//System.out.println("in");
			Region r = neighbourMap.get(direction).joinNeighbour(direction);
			if(r != null) add(r);
			return this;
		} else return null; // neighbour in the direction supplied
	}
	
	
}
