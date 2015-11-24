package eg.edu.guc.santorini.gui;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.utilities.Location;

public class Adapter {
	private Board board;

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Adapter(Player player1, Player player2) {

		board = new Board(player1, player2);

	}

	public ArrayList<Integer> whatToDoIfClickedOnce(int y, int x) {
		ArrayList<Integer> r;
		int pl = 0;
		Location temp = board.getLocations()[y][x];
		if (temp.getPiece() == null) {
			// ImageIcon i = new
			// ImageIcon(getClass().getResource(temp.getLevel()+'n' + 'n'));
			r = new ArrayList<Integer>();
			return r;
			// r.add(x);
		} else {
			Piece currPiece = temp.getPiece();
			Player currPlayer = board.getTurn();
			Player playerOfPiece;
			if (board.getPlayer1().getT1() == currPiece
					|| board.getPlayer1().getT2() == currPiece) {
				playerOfPiece = board.getPlayer1();
				pl = 1;
			} else {
				playerOfPiece = board.getPlayer2();
				pl = 2;
			}
			if (playerOfPiece != currPlayer) {
				r = board.validMoves(currPiece);
			} else {
				if (board.isMoved()) {
					r = board.validPlacements(currPiece);
				} else {
					r = board.validMoves(currPiece);
					// System.out.println(currPiece.getPieceLocation().getLevel());
				}
			}
		}
		r.add(pl);
		return r;
	}

	public void callMove(ArrayList<Integer> locLast, ArrayList<Integer> locNext)
			throws InvalidMoveException, InvalidPlacementException {
		Location locPiece = board.getLocations()[locLast.get(0)][locLast.get(1)];
		Location newLocation = board.getLocations()[locNext.get(0)][locNext
				.get(1)];
		board.move(locPiece.getPiece(), newLocation);

	}

	public void callPlace(ArrayList<Integer> locLast, ArrayList<Integer> locNext)
			throws InvalidPlacementException, InvalidMoveException {
		Location locPiece = board.getLocations()[locLast.get(0)][locLast.get(1)];
		Location newLocation = board.getLocations()[locNext.get(0)][locNext
				.get(1)];
		board.place(locPiece.getPiece(), newLocation);
		System.out.println(board.getLocations()[0][3].getPiece());
		System.out.println(board.getPlayer2().getT1().getPieceLocation());
		System.out.println(board.getTurn());
		//System.out.println(board.getPlayer2().getT1().getPieceLocation().getYAxis()+","+board.getPlayer2().getT1().getPieceLocation().getXAxis());
	}

	public ImageIcon getshaklelLabel(int y, int x) {
		JLabel r;
		r = new JLabel();
		r.setText("");
		System.out.print(r.getText());
		ImageIcon i;
		Location temp = board.getLocations()[y][x];
		if (temp.getPiece() != null) {
			if (temp.getPiece() instanceof Cube) {
				if (temp.getPiece() == board.getPlayer1().getT1()
						|| temp.getPiece() == board.getPlayer1().getT2()) {
					i = new ImageIcon(temp.getLevel() + "cyn.jpg");
					// System.out.println(i.getDescription());
					r = new JLabel(i);
				} else {
					i = new ImageIcon(temp.getLevel() + "cgn.jpg");
					// System.out.println(i.getDescription());
					r = new JLabel(i);
				}
			} else {
				if (temp.getPiece() == board.getPlayer1().getT1()
						|| temp.getPiece() == board.getPlayer1().getT2()) {

					i = new ImageIcon(temp.getLevel() + "pyn.jpg");
					r = new JLabel(i);
				} else {

					i = new ImageIcon(temp.getLevel() + "pgn.jpg");
					r = new JLabel(i);
				}
			}
		} else {
			if (temp.getLevel() > 0) {
				i = new ImageIcon(temp.getLevel() + "nnn.jpg");
				r = new JLabel(i);
			} else {
				i = new ImageIcon("0nnn.jpg");
				r = new JLabel(i);
			}
		}
		return i;
	}

	public Player getWinner() {
		return board.getWinner();
	}

	public boolean isGameOver() {
		return board.isGameOver();
	}

	public int getTurn() {
		if (board.getTurn() == board.getPlayer1()) {
			return 1;
		} else {
			return 2;
		}
	}

}
