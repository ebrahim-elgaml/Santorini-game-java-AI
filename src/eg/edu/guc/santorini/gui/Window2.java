package eg.edu.guc.santorini.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.players.Player;
import eg.edu.guc.santorini.players.PlayerAI;

public class Window2 extends JFrame implements MouseListener {
	private static final long serialVersionUID = 462895847487407762L;
	private JLabel[][] panelHolder2;
	private Adapter adapter;
	private JPanel south;
	private JPanel center;
	private JLabel turn;
	private JLabel action;
	private JLabel selected;
	private JLabel counter;
	private LastWindow gameOverWindow;
	private boolean playCounter;

	public Window2(Player player1, Player player2) {

		this.getContentPane().setLayout(new BorderLayout());
		createSouthPanel();
		this.getContentPane().add(south, BorderLayout.SOUTH);
		center = new JPanel();
		center.setLayout(new GridLayout(5, 5));
		this.setSize(1000, 700);
		this.setLocation(200, 00);
		panelHolder2 = new JLabel[5][5];
		adapter = new Adapter(player1, player2);
		for (int m = 0; m < 5; m++) {
			for (int n = 0; n < 5; n++) {

				panelHolder2[m][n] = new JLabel(adapter.getshaklelLabel(m, n));
				panelHolder2[m][n].addMouseListener(this);
				center.add(panelHolder2[m][n]);
				panelHolder2[m][n].setLayout(new BorderLayout());
			}
		}

		selected = new JLabel();
		this.getContentPane().add(center, BorderLayout.CENTER);
		this.setVisible(true);
		gameOverWindow = new LastWindow();
		gameOverWindow.setVisible(false);
		playCounter = true;
		counter.setFont(new Font(counter.getFont().getName(), Font.PLAIN, 36));
		counter.setForeground(Color.BLUE);
		action.setFont(new Font(action.getFont().getName(), Font.PLAIN, 36));
		action.setForeground(Color.BLUE);
		turn.setFont(new Font(turn.getFont().getName(), Font.PLAIN, 36));
		turn.setForeground(Color.BLUE);
		WindowDestroyer w = new WindowDestroyer();
		addWindowListener(w);
	}

	public Adapter getAdapter() {
		return this.adapter;
	}

	public void endGame() {
		if (adapter.isGameOver()) {

			this.setVisible(false);
			gameOverWindow = new LastWindow(adapter.getWinner(),
					counter.getText());
			gameOverWindow.setVisible(true);
		}
	}

	public boolean changeCounterBoolean() {
		if (adapter.isGameOver()) {
			playCounter = false;
		}
		return true;
	}

	public Window2() {
		playCounter = true;
	}

	public void createSouthPanel() {
		action = new JLabel("Move a piece");
		action.setBorder(BorderFactory.createLineBorder(Color.black));
		turn = new JLabel("Turn : Player 1");
		turn.setBorder(BorderFactory.createLineBorder(Color.black));
		counter = new JLabel("0");

		south = new JPanel();
		south.setLayout(new GridLayout(1, 3));
		south.add(action);
		south.add(turn);
		JPanel a = new JPanel();
		a.setLayout(new BorderLayout());
		a.add(counter, BorderLayout.CENTER);
		a.setBorder(BorderFactory.createLineBorder(Color.black));
		south.add(a);
		south.setBorder(BorderFactory.createLineBorder(Color.black));

	}

	public static String changeTo(int x) {
		if (x >= 3600) {
			return "" + (x / 3600) + ":" + ((x % 3600) / 60) + ":"
					+ ((x % 3600) / 60);
		}
		if (x >= 60) {
			return "" + (x / 60) + ":" + (x % 60);
		}
		if (x < 60) {
			return "" + x;
		}
		return null;

	}

	public void changeCounter() {
		int i = 0;
		while (playCounter) {
			changeCounterBoolean();
			if(!playCounter){
				return ;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ie) {
				ie.getMessage();
			}
			i++;
			counter.setText(changeTo(i));
		}
	}

	public void helper1() {
		for (int a = 0; a < 5; a++) {
			for (int b = 0; b < 5; b++) {
				if (panelHolder2[a][b] == selected) {
					String noSelected = getLast8(((ImageIcon) selected
							.getIcon()).getDescription());
					panelHolder2[a][b].setIcon((new ImageIcon(noSelected
							.charAt(0) + "nnn.jpg")));
				}
			}
		}
	}

	public int[] helper2(ImageIcon newMove, JLabel temp) {
		int[] r = new int[2];
		for (int a = 0; a < 5; a++) {
			for (int b = 0; b < 5; b++) {
				if (panelHolder2[a][b] == (JLabel) temp) {
					panelHolder2[a][b].setIcon((newMove));
					selected = new JLabel();
					action.setText("place a tile");
					r[0] = a;
					r[1] = b;
					return r;
				}
			}
		}
		return r;
	}

	public int[] helper3(JLabel temp) {
		int[] r = new int[2];
		for (int a = 0; a < 5; a++) {
			for (int b = 0; b < 5; b++) {
				if (panelHolder2[a][b] == (JLabel) temp) {
					r[0] = a;
					r[1] = b;
					return r;
				}
			}
		}
		return r;
	}

	public void movePieceOrPlaceTile(JLabel temp) throws InvalidMoveException,
			InvalidPlacementException {
		if (temp != null) {
			ImageIcon image = (ImageIcon) temp.getIcon();
			if (image == null) {
				return;
			}
			String name = getLast8(image.getDescription());
			String nISelected = getLast8(((ImageIcon) selected.getIcon())
					.getDescription());
			if (action.getText().substring(0, 4).equals("Move")) {
				adapter.callMove(getIndex(selected), getIndex(temp));
				helper1();
				ImageIcon newMove;
				if (adapter.getTurn() == 1) {
					newMove = new ImageIcon(name.charAt(0) + ""
							+ nISelected.charAt(1) + "yn.jpg");
				} else {
					newMove = new ImageIcon(name.charAt(0) + ""
							+ nISelected.charAt(1) + "gn.jpg");
				}
				int[] r = helper2(newMove, (JLabel) temp);
				whatToDoIfPressedOnPiece(panelHolder2[r[0]][r[1]]);
				return;
			} else {
				if(adapter.getBoard().getPlayer2() instanceof PlayerAI){
					adapter.callPlace(getIndex(selected), getIndex(temp));
					for(int c = 0 ;c<5;c++){
						for(int k = 0 ;k<5;k++){
							ImageIcon s = adapter.getshaklelLabel(adapter.getBoard().getLocations()[c][k].getYAxis(),adapter.getBoard().getLocations()[c][k].getXAxis());
							panelHolder2[c][k].setIcon(s);
						}
						 
					}
					action.setText("Move a piece");
					changeTurn(turn.getText());
					changeTurn(turn.getText());
				}else{
				adapter.callPlace(getIndex(selected), getIndex(temp));
				int[] a = helper3((JLabel) temp);
				String nameTemp = getLast8(((ImageIcon) temp.getIcon())
						.getDescription());
				ImageIcon newTile = new ImageIcon((Integer.parseInt(nameTemp
						.charAt(0) + ""))
						+ 1 + nameTemp.substring(1));
				panelHolder2[a[0]][a[1]].setIcon(newTile);
				selected = new JLabel();
				action.setText("Move a piece");
				changeTurn(turn.getText());
				}
			}
		}
		shilElSelected();
	}

	public void changeTurn(String turnT) {
		if (turnT.charAt(turnT.length() - 1) == '1') {
			turn.setText(turnT.substring(0, turnT.length() - 1) + "2");
		} else {
			turn.setText(turnT.substring(0, turnT.length() - 1) + "1");
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public String getLast8(String s) {
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			if (i >= s.length() - 8) {
				r = r + s.charAt(i);
			}
		}
		return r;
	}

	public void mousePressed(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		if (temp == null) {
			return;
		}
		String description = getLast8(((ImageIcon) temp.getIcon())
				.getDescription());
		if (description.charAt(1) == 'n' && description.charAt(2) == 'r') {
			getIndex(temp);
			try {
				movePieceOrPlaceTile(temp);
			} catch (InvalidMoveException e1) {
				e1.printStackTrace();
			} catch (InvalidPlacementException f) {
				f.printStackTrace();
			}
			return;
		}
		whatToDoIfPressedOnPiece(temp);
	}

	public void helper4(ArrayList<Integer> r, int k, String name) {
		if (name.charAt(name.length() - 5) == 's') {
			if (adapter.getTurn() == 1) {
				panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
						.charAt(0) + "" + name.charAt(1) + "gn.jpg"));
			} else {
				panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
						.charAt(0) + "" + name.charAt(1) + "yn.jpg"));
			}
		} else {
			panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
					.charAt(0) + "" + name.charAt(1) + "bs.jpg"));
		}
	}

	public void helper5(ArrayList<Integer> r, int k, String name) {
		if (name.charAt(name.length() - 5) != 's') {
			panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
					.charAt(0) + "" + name.charAt(1) + "rs.jpg"));
		} else {
			if (adapter.getTurn() == 1) {
				panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
						.charAt(0) + "" + name.charAt(1) + "yn.jpg"));
			} else {
				panelHolder2[r.get(k)][r.get(k + 1)].setIcon(new ImageIcon(name
						.charAt(0) + "" + name.charAt(1) + "gn.jpg"));
			}
		}
	}

	public void whatToDoIfPressedOnPiece(JLabel temp) {
		if (temp == null) {
			return;
		}
		String description = getLast8(((ImageIcon) temp.getIcon())
				.getDescription());
		shilElSelected();
		ImageIcon newSelectedImage = new ImageIcon(description.charAt(0) + ""
				+ description.charAt(1) + "rs.jpg");
		ImageIcon newSelectedImage2 = new ImageIcon(description.charAt(0) + ""
				+ description.charAt(1) + "bs.jpg");
		ArrayList<Integer> selLoc = getIndex(temp);
		if (selLoc.get(0) < 5 && selLoc.get(1) < 5) {
			ArrayList<Integer> r = adapter.whatToDoIfClickedOnce(selLoc.get(0),
					selLoc.get(1));
			boolean myTurn = false;
			if (r.size() != 0) {
				if ((r.get(r.size() - 1) + "").equals(turn.getText().charAt(
						turn.getText().length() - 1)
						+ "")) {
					myTurn = true;
				}
			}
			if (myTurn) {
				panelHolder2[selLoc.get(0)][selLoc.get(1)]
						.setIcon(newSelectedImage);
			} else {
				panelHolder2[selLoc.get(0)][selLoc.get(1)]
						.setIcon(newSelectedImage2);
			}
			for (int k = 0; k < r.size() - 2; k = k + 2) {
				JLabel jTemp = panelHolder2[r.get(k)][r.get(k + 1)];
				ImageIcon iTemp = (ImageIcon) jTemp.getIcon();
				String name = getLast8(iTemp.getDescription());
				if (!myTurn) {
					helper4(r, k, name);
				} else {
					helper5(r, k, name);
				}
			}
		}
		selected = temp;
	}

	public void shilElSelected() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				ImageIcon image = (ImageIcon) panelHolder2[i][j].getIcon();
				if (image.getDescription().charAt(3) == 's') {
					panelHolder2[i][j].setIcon(adapter.getshaklelLabel(i, j));
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		if (temp == null) {
			return;
		}
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public ArrayList<Integer> getIndex(JLabel x) {
		ArrayList<Integer> r = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (panelHolder2[i][j] == x) {
					r.add(i);
					r.add(j);
					// System.out.println(i +""+ j);
				}
			}
		}
		return r;
	}

	public JLabel getCounter() {
		return counter;
	}

	public void setCounter(JLabel counter) {
		this.counter = counter;
	}

	public Player checkWin() {
		return adapter.getWinner();
	}

	public LastWindow getGameOverWindow() {
		return gameOverWindow;
	}

	public void setGameOverWindow(LastWindow gameOverWindow) {
		this.gameOverWindow = gameOverWindow;
	}

	public boolean isPlayCounter() {
		return playCounter;
	}

	public void setPlayCounter(boolean playCounter) {
		this.playCounter = playCounter;
	}

}
