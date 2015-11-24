package eg.edu.guc.santorini.utilities;

import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;

public class Location {
	private int yAxis;
	private int xAxis;
	private int level;
	private boolean occupied;
	private Piece piece;
	private boolean killed;

	public Location() {
		setLevel(0);
		setOccupied(false);
		piece = null;
	}
	public Location copyLocation(){
		
		//r.setYAxis(yAxis);
		//r.setXAxis(xAxis);
		//r.setLevel(level);
		//r.setOccupied(occupied);
		Piece a;
		if(piece instanceof Cube){
			a = new Cube();
		}else if(piece instanceof Pyramid){
			a = new Pyramid();
			}else{
				a = null;
			}
		if(a==null){
			Location r = new Location(yAxis,xAxis,level,occupied,a,killed);
			return r;
		}
		a.setPieceLocation(new Location(yAxis,xAxis));
		a.getPieceLocation().setOccupied(occupied);
		a.getPieceLocation().setLevel(level);
		a.getPieceLocation().setKilled(killed);
		Location r = new Location(yAxis,xAxis,level,occupied,a,killed);
		//System.out.println(r.getPiece());
		//r.setPiece(a);
		//r.setKilled(killed);
		return r;
	}

	public Location(int yAxis, int xAxis, int level, boolean occupied,
			Piece piece, boolean killed) {
		super();
		this.yAxis = yAxis;
		this.xAxis = xAxis;
		this.level = level;
		this.occupied = occupied;
		this.piece = piece;
		this.killed = killed;
	}
	public Location(int y, int x) {
		setYAxis(y);
		setXAxis(x);
		setLevel(0);
		setOccupied(false);
		piece = null;
		killed = false;
	}

	public int getYAxis() {
		return yAxis;
	}

	public void setYAxis(int yAxis) {
		this.yAxis = yAxis;
	}

	public int getXAxis() {
		return xAxis;
	}

	public void setXAxis(int xAxis) {
		this.xAxis = xAxis;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public boolean isKilled() {
		return killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public String toString() {
		return "Location [y_axis=" + yAxis + ", x_axis=" + xAxis + "]";
	}

	public boolean equals(Object a) {
		Location l = (Location) a;
		return l.getXAxis() == xAxis && l.getYAxis() == yAxis;
	}

}
