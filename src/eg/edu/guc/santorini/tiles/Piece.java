package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

public abstract class Piece implements PieceInterface {
	private Location pieceLocation;
	private boolean moved;
	public Piece() {
		pieceLocation = new Location(-1, -1);
		setMoved(false);
	}

	@Override
	public String toString() {
		return "Piece [pieceLocation=" + pieceLocation + ", moved=" + moved
				+ "]";
	}

	public Piece(Location pieceLocation, boolean moved) {
		super();
		this.pieceLocation = pieceLocation;
		this.moved = moved;
	}

	public abstract ArrayList<Location> possibleMoves();
	public abstract Piece copyPiece ();

	public ArrayList<Location> possiblePlacements() {
		ArrayList<Location> loc = new ArrayList<Location>();
		Location currentLocation = getPieceLocation();
		Location upLeft = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis() - 1);
		Location downLeft = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis() - 1);
		Location upRight = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis() + 1);
		Location downRight = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis() + 1);
		Location up = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis());
		Location down = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis());
		Location left = new Location(currentLocation.getYAxis(),
				currentLocation.getXAxis() - 1);
		Location right = new Location(currentLocation.getYAxis(),
				currentLocation.getXAxis() + 1);
		if (possible(upLeft)) {
			loc.add(upLeft);
		}
		if (possible(up)) {
			loc.add(up);
		}
		if (possible(upRight)) {
			loc.add(upRight);
		}
		if (possible(left)) {
			loc.add(left);
		}
		if (possible(right)) {
			loc.add(right);
		}
		if (possible(downLeft)) {
			loc.add(downLeft);
		}
		if (possible(down)) {
			loc.add(down);
		}
		if (possible(downRight)) {
			loc.add(downRight);
		}
		return loc;
	}

	public Location getPieceLocation() {
		return pieceLocation;
	}

	public void setPieceLocation(Location pieceLocation) {
		this.pieceLocation = pieceLocation;
	}

	protected boolean possible(Location loc) {
		int y = loc.getYAxis();
		int x = loc.getXAxis();
		return (x >= 0 & y >= 0 & x < 5 & y < 5);
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

}
