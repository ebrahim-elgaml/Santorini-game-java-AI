package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

public class Pyramid extends Piece {

	public Pyramid() {
		super();
	}
	
	public Pyramid(Location pieceLocation, boolean moved) {
		super(pieceLocation, moved);
		// TODO Auto-generated constructor stub
	}

	public ArrayList<Location> possibleMoves() {
		ArrayList<Location> loc = new ArrayList<Location>();
		Location currentLocation = super.getPieceLocation();
		Location upLeft = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis() - 1);
		Location downLeft = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis() - 1);
		Location upRight = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis() + 1);
		Location downRight = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis() + 1);
		if (super.possible(upLeft)) {
			loc.add(upLeft);
		}
		if (super.possible(downLeft)) {
			loc.add(downLeft);
		}
		if (super.possible(upRight)) {
			loc.add(upRight);
		}
		if (super.possible(downRight)) {
			loc.add(downRight);
		}
		return loc;
	}

	public Piece copyPiece() {
		Piece r = new Pyramid();
		r.setPieceLocation(getPieceLocation().copyLocation());
		r.setMoved(isMoved());
		return r;
	}

}
