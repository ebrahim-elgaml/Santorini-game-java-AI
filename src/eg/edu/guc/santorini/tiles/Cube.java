package eg.edu.guc.santorini.tiles;

import java.util.ArrayList;

import eg.edu.guc.santorini.utilities.Location;

public class Cube extends Piece {

	public Cube() {
		super();
	}

	
	public Cube(Location pieceLocation, boolean moved) {
		super(pieceLocation, moved);
		// TODO Auto-generated constructor stub
	}


	public ArrayList<Location> possibleMoves() {
		ArrayList<Location> loc = new ArrayList<Location>();
		Location currentLocation = super.getPieceLocation();
		Location up = new Location(currentLocation.getYAxis() - 1,
				currentLocation.getXAxis());
		Location down = new Location(currentLocation.getYAxis() + 1,
				currentLocation.getXAxis());
		Location left = new Location(currentLocation.getYAxis(),
				currentLocation.getXAxis() - 1);
		Location right = new Location(currentLocation.getYAxis(),
				currentLocation.getXAxis() + 1);
		if (super.possible(up)) {
			loc.add(up);
		}
		if (super.possible(down)) {
			loc.add(down);
		}
		if (super.possible(left)) {
			loc.add(left);
		}
		if (super.possible(right)) {
			loc.add(right);
		}
		return loc;
	}



	public Piece copyPiece() {
		Piece r = new Cube();
		r.setPieceLocation(getPieceLocation().copyLocation());
		r.setMoved(isMoved());
		return r;
	}

}
