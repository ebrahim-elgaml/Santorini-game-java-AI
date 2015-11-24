package eg.edu.guc.santorini;

import java.util.ArrayList;

import eg.edu.guc.santorini.AI.Node;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.players.PlayerAI;
import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;
import eg.edu.guc.santorini.utilities.Location;

public class Board implements BoardInterface {
	private Player player1;
	private Player player2;
	private Location[][] locations;
	private Turn turn;
	private boolean moved;

	public Board() {
		locations = new Location[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				locations[i][j] = new Location(i, j);
			}
		}
		turn = Turn.Player1;
	}
	public Turn getEnumTurn(){
		return turn;
	}
	public Board (Player p1, int i ) {
		this(p1,new PlayerAI(i));
	}
	public Board copyBoard(){
		Board r = new Board();
		r.setPlayer1(player1.copyPlayer());
		r.setPlayer2(player2.copyPlayer());
		r.setTurn(turn);
		r.setMoved(moved);
		for(int i = 0 ;i<5;i++){
			for(int j = 0 ;j<5;j++){
				r.getLocations()[i][j] = locations[i][j].copyLocation();
			}
		}
		return r;
	}

	public Board(Player p1, Player p2) {
		player1 = p1;
		player2 = p2;
		locations = new Location[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				locations[i][j] = new Location(i, j);
			}
		}
		p1.getT1().setPieceLocation(locations[0][0]);
		locations[0][0].setOccupied(true);
		locations[0][0].setPiece(p1.getT1());
		p1.getT2().setPieceLocation(locations[4][1]);
		locations[4][1].setOccupied(true);
		locations[4][1].setPiece(p1.getT2());
		p2.getT1().setPieceLocation(locations[0][3]);
		locations[0][3].setOccupied(true);
		locations[0][3].setPiece(p2.getT1());
		p2.getT2().setPieceLocation(locations[4][4]);
		locations[4][4].setOccupied(true);
		locations[4][4].setPiece(p2.getT2());
		turn = Turn.Player1;
		moved = false;
	}

	public ArrayList<Integer> validMoves(Piece p) {
		ArrayList<Location> possible = p.possibleMoves();
		int level = p.getPieceLocation().getLevel();
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < possible.size(); i++) {
			Location a = possible.get(i);
			int requiredLevel = locations[a.getYAxis()][a.getXAxis()]
					.getLevel();
			if ((!locations[a.getYAxis()][a.getXAxis()].isOccupied())
					&& locations[a.getYAxis()][a.getXAxis()].getPiece() == null
					&& (requiredLevel <= level || (requiredLevel - level) == 1)
					&& !locations[a.getYAxis()][a.getXAxis()].isKilled()) {
				result.add(a.getYAxis());
				result.add(a.getXAxis());

			}
		}
		return result;
	}

	public ArrayList<Integer> validPlacements(Piece p) {
		ArrayList<Location> possible = p.possiblePlacements();
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < possible.size(); i++) {
			Location a = possible.get(i);
			if ((!locations[a.getYAxis()][a.getXAxis()].isOccupied())
					&& locations[a.getYAxis()][a.getXAxis()].getPiece() == null
					&& !locations[a.getYAxis()][a.getXAxis()].isKilled()) {
				result.add(a.getYAxis());
				result.add(a.getXAxis());

			}
		}
		return result;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public void move(Piece piece, Location newLocation)
			throws InvalidMoveException, InvalidPlacementException {
		
		if (piece.isMoved()) {
			throw new InvalidMoveException("you should place not move");
		}
		if (isWinner(player1) || isWinner(player2)) {
			throw new InvalidMoveException("the game has already ended");
		}
		if (isGameOver()) {
			throw new InvalidMoveException(" Can not move ");
		}
		if (pieceBelongsTo(piece) != getTurn()) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		if (moved) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		if (!canMove(piece, newLocation)) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		piece.getPieceLocation().setOccupied(false);
		piece.getPieceLocation().setPiece(null);
		piece.setPieceLocation(this.locations[newLocation.getYAxis()][newLocation
				.getXAxis()]);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setOccupied(true);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setPiece(piece);
		setMoved(true);
		piece.setPieceLocation(newLocation);
		piece.setMoved(true);
	}

	@Override
	public void place(Piece piece, Location newLocation)
			throws InvalidPlacementException, InvalidMoveException {
		if (!piece.isMoved()) {
			throw new InvalidPlacementException("incorrect piece");
		}
		if (pieceBelongsTo(piece) != getTurn()) {
			throw new InvalidPlacementException("Can not move to that Location");
		}
		if (!moved) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!canPlace(piece, newLocation)) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.isKilled()) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setLevel(locations[newLocation.getYAxis()][newLocation
							.getXAxis()].getLevel() + 1);
		}
		if (locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.getLevel() > 3) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setKilled(true);
		}
		changeTurn();
		setMoved(false);
		piece.setMoved(false);
		if(getTurn()==player2&& player2 instanceof PlayerAI){
			moveAI();
			//System.out.println("asd");
			
		}
	}
	@Override
	public boolean isGameOver() {
		return (hasNoMoves(player1) && hasNoMoves(player2))
				|| isWinner(player1) || isWinner(player2);
	}
	public void maveWithoutTurn(Piece piece,Location newLocation) throws InvalidMoveException{
		if (isWinner(player1) || isWinner(player2)) {
			throw new InvalidMoveException("the game has already ended");
		}
		if (isGameOver()) {
			throw new InvalidMoveException(" Can not move ");
		}
		if (moved) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		if (!canMove(piece, newLocation)) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		piece.getPieceLocation().setOccupied(false);
		piece.getPieceLocation().setPiece(null);
		piece.setPieceLocation(this.locations[newLocation.getYAxis()][newLocation
				.getXAxis()]);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setOccupied(true);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setPiece(piece);
		piece.setPieceLocation(newLocation);
		
	}
	public void PlaceWithoutTurn(Piece piece ,Location newLocation) throws InvalidPlacementException{

		if (!moved) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!canPlace(piece, newLocation)) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.isKilled()) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setLevel(locations[newLocation.getYAxis()][newLocation
							.getXAxis()].getLevel() + 1);
		}
		if (locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.getLevel() > 3) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setKilled(true);
		}
		
	}
	public boolean isWinner(Player player) {
		Player tempPlayer;
		if (player == player1) {
			tempPlayer = player2;
		} else {
			tempPlayer = player1;
		}
		if (player.getT1().getPieceLocation().getLevel() == 3
				|| player.getT2().getPieceLocation().getLevel() == 3
				|| (hasNoMoves(tempPlayer) && getTurn() == tempPlayer)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean hasNoMoves(Player player) {
		ArrayList<Location> possible = player.getT1().possibleMoves();
		boolean flag1 = true;
		for (int i = 0; i < possible.size(); i++) {
			if (canMove(player.getT1(), possible.get(i))) {
				flag1 = false;
				break;
			}
		}
		boolean flag2 = true;
		ArrayList<Location> possible2 = player.getT2().possibleMoves();
		for (int i = 0; i < possible2.size(); i++) {
			if (canMove(player.getT2(), possible2.get(i))) {
				flag2 = false;
				break;
			}
		}
		return flag1 && flag2;
	}

	@Override
	public Player getWinner() {
		if (isWinner(player1)) {
			return player1;
		}
		if (isWinner(player2)) {
			return player2;
		}
		if (!isGameOver()) {
			if (hasNoMoves(player1)) {
				return player2;
			} else {
				return player1;
			}
		}
		return null;
	}

	public boolean canMove(Piece piece, Location location) {
		int requiredLevel = locations[location.getYAxis()][location.getXAxis()]
				.getLevel();
		if (locations[location.getYAxis()][location.getXAxis()].isKilled()) {
			return false;
		}
		ArrayList<Location> possible = piece.possibleMoves();
		for (int i = 0; i < possible.size(); i++) {
			Location a = possible.get(i);
			int level = piece.getPieceLocation().getLevel();
			if ((!locations[a.getYAxis()][a.getXAxis()].isOccupied())
					&& locations[a.getYAxis()][a.getXAxis()].getPiece() == null
					&& (requiredLevel <= level || (requiredLevel - level) == 1)) {
				if (location.getYAxis() == a.getYAxis()
						&& location.getXAxis() == a.getXAxis()) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	public boolean canPlace(Piece piece, Location location) {
		if (locations[location.getYAxis()][location.getXAxis()].isKilled()) {
			return false;
		}
		ArrayList<Location> possible = piece.possiblePlacements();
		for (int i = 0; i < possible.size(); i++) {
			Location a = possible.get(i);
			if ((!locations[a.getYAxis()][a.getXAxis()].isOccupied())
					&& locations[a.getYAxis()][a.getXAxis()].getPiece() == null) {
				if (location.getYAxis() == a.getYAxis()
						&& location.getXAxis() == a.getXAxis()) {
					return true;
				}

			}
		}
		return false;
	}

	@Override
	public Player getTurn() {
		if (turn == Turn.Player1) {
			return player1;
		}
		return player2;
	}

	public void setTurn(Turn t) {
		turn = t;
	}

	public void changeTurn() {
		if (turn == Turn.Player1) {
			setTurn(Turn.Player2);
			return;
		}
		setTurn(Turn.Player1);
	}

	@Override
	public String[][] display() {
		String[][] s = new String[5][5];
		for (int i = 0; i < locations.length; i++) {
			for (int j = 0; j < locations[i].length; j++) {
				s[i][j] = "" + locations[i][j].getLevel();
				if (locations[i][j].getPiece() != null) {
					if (locations[i][j].getPiece() instanceof Cube) {
						s[i][j] += "C";
						if (player1.getT1() instanceof Cube) {
							s[i][j] += "1";
						} else {
							s[i][j] += "2";
						}
					} else {
						s[i][j] += "" + "P";
						if (player1.getT1() instanceof Pyramid) {
							s[i][j] += "1";
						} else {
							s[i][j] += "2";
						}
					}
				}
			}
		}
		return s;
	}

	public Location[][] getLocations() {
		return locations;
	}

	public void setLocations(Location[][] locations) {
		this.locations = locations;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public Player pieceBelongsTo(Piece p) {
		if (player1.getT1() == p || player1.getT2() == p) {
			return player1;
		} else {
			if (player2.getT1() == p || player2.getT2() == p) {
				return player2;
			}
		}
		return null;
		/*
		 * if (p instanceof Cube) { if (player1.getT1() instanceof Cube) {
		 * return player1; } else { return player2; } } if (p instanceof
		 * Pyramid) { if (player1.getT1() instanceof Pyramid) { return player1;
		 * } else { return player2; } } return null;
		 */
	}
	public void moveAI() throws InvalidMoveException, InvalidPlacementException{
		Node a = new Node(this);
		Board newBoard = a.getEl3baEl7lwa();
		player1 = newBoard.getPlayer1();
		player2 = newBoard.getPlayer2();
		locations = newBoard.getLocations();
		turn = newBoard.getEnumTurn();
		moved = newBoard.isMoved();
	}
	public void move2(Piece piece, Location newLocation)
			throws InvalidMoveException, InvalidPlacementException {
		
		if (piece.isMoved()) {
			throw new InvalidMoveException("you should place not move");
		}
		if (isWinner(player1) || isWinner(player2)) {
			throw new InvalidMoveException("the game has already ended");
		}
		if (isGameOver()) {
			throw new InvalidMoveException(" Can not move ");
		}
		if (pieceBelongsTo(piece) != getTurn()) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		if (moved) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		if (!canMove(piece, newLocation)) {
			throw new InvalidMoveException("Can not move to that Location");
		}
		piece.getPieceLocation().setOccupied(false);
		piece.getPieceLocation().setPiece(null);
		piece.setPieceLocation(this.locations[newLocation.getYAxis()][newLocation
				.getXAxis()]);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setOccupied(true);
		this.locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.setPiece(piece);
		setMoved(true);
		piece.setPieceLocation(newLocation);
		piece.setMoved(true);
	}

	public void place2(Piece piece, Location newLocation)
			throws InvalidPlacementException, InvalidMoveException {
		if (!piece.isMoved()) {
			throw new InvalidPlacementException("incorrect piece");
		}
		if (pieceBelongsTo(piece) != getTurn()) {
			throw new InvalidPlacementException("Can not move to that Location");
		}
		if (!moved) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!canPlace(piece, newLocation)) {
			throw new InvalidPlacementException(" can not be placed");
		}
		if (!locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.isKilled()) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setLevel(locations[newLocation.getYAxis()][newLocation
							.getXAxis()].getLevel() + 1);
		}
		if (locations[newLocation.getYAxis()][newLocation.getXAxis()]
				.getLevel() > 3) {
			locations[newLocation.getYAxis()][newLocation.getXAxis()]
					.setKilled(true);
		}
		changeTurn();
		setMoved(false);
		piece.setMoved(false);
		/*if(getTurn()==player2&& player2 instanceof PlayerAI){
			moveAI();
			//System.out.println("asd");
			
		}*/
	}

}
