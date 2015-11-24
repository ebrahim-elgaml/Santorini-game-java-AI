package eg.edu.guc.santorini.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import eg.edu.guc.santorini.players.Player;

public class Start extends JFrame implements MouseListener, ActionListener {

	private static final long serialVersionUID = -8812918654098166450L;
	private ArrayList<JLabel> picked1;
	private ArrayList<JLabel> picked2;
	private JPanel[][] panelHolder;
	private JTextField player1Name = new JTextField("", 20);
	private JTextField player2Name = new JTextField("", 20);
	private JLabel cube1;
	private JLabel pyramid1;
	private JLabel cube3;
	private JLabel pyramid3;
	private JLabel errorMsg = new JLabel("");
	private Window2 playWindow;

	public Start() {
		setTitle("Start window");
		setSize(1000, 700);
		setLocation(200, 10);
		Container content = getContentPane();
		content.setBackground(Color.orange);
		content.setLayout(new GridLayout(4, 3));
		createkol7aga();
		WindowDestroyer w = new WindowDestroyer();
		addWindowListener(w);
		this.setVisible(true);
		picked1 = new ArrayList<JLabel>();
		picked2 = new ArrayList<JLabel>();
		playWindow = new Window2();
		playWindow.setVisible(false);
		playWindow.setPlayCounter(false);

	}

	public void createkol7aga() {
		panelHolder = new JPanel[4][3];
		for (int m = 0; m < 4; m++) {
			for (int n = 0; n < 3; n++) {
				panelHolder[m][n] = new JPanel();
				this.getContentPane().add(panelHolder[m][n]);
			}
		}
		for (int i = 0; i < 4; i++) {
			panelHolder[i][0].setBackground(Color.RED);
		}
		JLabel p1 = new JLabel("please pick piece type");
		p1.setFont(new Font(p1.getFont().getName(), Font.PLAIN, 32));
		panelHolder[0][1].add(p1);
		JLabel player1 = new JLabel("player1");
		panelHolder[1][0].add(player1);
		panelHolder[1][0].add(player1Name);
		player1.setFont(new Font(p1.getFont().getName(), Font.PLAIN, 64));
		ImageIcon icon1 = new ImageIcon(("0cyn.jpg"));
		cube1 = new JLabel(icon1);
		cube1.addMouseListener(this);
		panelHolder[2][0].add(cube1);
		ImageIcon icon2 = new ImageIcon(("0pyn.jpg"));
		pyramid1 = new JLabel(icon2);
		pyramid1.addMouseListener(this);
		panelHolder[3][0].add(pyramid1);
		for (int i = 0; i < 4; i++) {
			panelHolder[i][2].setBackground(Color.GREEN);
		}
		JLabel player2 = new JLabel("Player2");
		panelHolder[1][2].add(player2);
		panelHolder[1][2].add(player2Name);
		player2.setFont(new Font(p1.getFont().getName(), Font.PLAIN,
				64));
		cube3 = new JLabel(icon1);
		cube3.addMouseListener(this);
		panelHolder[2][2].add(cube3);
		pyramid3 = new JLabel(icon2);
		pyramid3.addMouseListener(this);
		panelHolder[3][2].add(pyramid3);
		JLabel split = new JLabel("VS");
		split.setFont(new Font(p1.getFont().getName(), Font.PLAIN, 64));
		panelHolder[3][1].setBackground(Color.YELLOW);
		panelHolder[1][1].add(split);
		JButton b = new JButton("Start");
		b.setActionCommand("Start");
		b.addActionListener(this);
		panelHolder[3][1].setBackground(Color.BLUE);
		panelHolder[3][1].add(b);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void createMyBorder(JLabel a) {
		a.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));

	}

	public void mousePressed(MouseEvent e) {
		JLabel temp = (JLabel) e.getSource();
		if (temp == null) {
			return;
		}
		if (temp == cube1) {
			createMyBorder(cube1);
			pyramid1.setBorder(null);
			if (picked1.size() == 1) {
				picked1.clear();
			}
			picked1.add(temp);
			return;
		}
		if (temp == pyramid1) {
			createMyBorder(pyramid1);
			cube1.setBorder(null);
			if (picked1.size() == 1) {
				picked1.clear();
			}
			picked1.add(temp);
			return;
		}
		if (temp == cube3) {
			createMyBorder(cube3);
			pyramid3.setBorder(null);
			if (picked2.size() == 1) {
				picked2.clear();
			}
			picked2.add(temp);
			return;
		}
		if (temp == pyramid3) {
			createMyBorder(pyramid3);
			cube3.setBorder(null);
			if (picked2.size() == 1) {
				picked2.clear();
			}
			picked2.add(temp);
			return;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if (temp == null) {
			return;
		}
		panelHolder[2][1].add(errorMsg);
		errorMsg.setFont(new Font(errorMsg.getFont().getName(), Font.PLAIN, 16));
		if (player1Name.getText().equals("")
				&& player2Name.getText().equals("")) {
			errorMsg.setText("Each enter a name in the required field ");
			return;
		} else if (player1Name.getText().equals("")) {
			errorMsg.setText("Player 1 enter your name , please. ");
			return;
		}
		if (player2Name.getText().equals("")) {
			errorMsg.setText("player 2 enter your name ,please  ");
			return;
		}
		if (picked1.size() != 1 || picked2.size() != 1) {
			if (picked1.size() == 0 && picked2.size() == 0) {
				errorMsg.setText("Each player should select a piece");
				return;
			}
			if (picked1.size() == 0) {
				errorMsg.setText("Player 1 should select a piece");
				return;
			}
			if (picked2.size() == 0) {
				errorMsg.setText("Player 2 should select a piece");
				return;
			}
		} else {
			initPlayWindow(picked1,picked2);
		}
	}
	public void initPlayWindow(ArrayList<JLabel> picked1,ArrayList<JLabel> picked2){
		this.setVisible(false);
		Player player1 = new Player();
		Player player2 = new Player();
		if (picked1.get(0) == cube1) {
			player1 = new Player(player1Name.getText(), 1);
		}
		if (picked1.get(0) == pyramid1) {
			player1 = new Player(player1Name.getText(), 2);
		}
		if (picked2.get(0) == cube3) {
			player2 = new Player(player2Name.getText(), 1);
		}
		if (picked2.get(0) == pyramid3) {
			player2 = new Player(player2Name.getText(), 2);
		}
		playWindow = new Window2(player1, player2);
	}

	public ArrayList<JLabel> getPicked1() {
		return picked1;
	}

	public void setPicked1(ArrayList<JLabel> picked1) {
		this.picked1 = picked1;
	}

	public ArrayList<JLabel> getPicked2() {
		return picked2;
	}

	public void setPicked2(ArrayList<JLabel> picked2) {
		this.picked2 = picked2;
	}

	public JPanel[][] getPanelHolder() {
		return panelHolder;
	}

	public void setPanelHolder(JPanel[][] panelHolder) {
		this.panelHolder = panelHolder;
	}

	public JTextField getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(JTextField player1Name) {
		this.player1Name = player1Name;
	}

	public JTextField getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(JTextField player2Name) {
		this.player2Name = player2Name;
	}

	public JLabel getCube1() {
		return cube1;
	}

	public void setCube1(JLabel cube1) {
		this.cube1 = cube1;
	}

	public JLabel getPyramid1() {
		return pyramid1;
	}

	public void setPyramid1(JLabel pyramid1) {
		this.pyramid1 = pyramid1;
	}

	public JLabel getCube3() {
		return cube3;
	}

	public void setCube3(JLabel cube3) {
		this.cube3 = cube3;
	}

	public JLabel getPyramid3() {
		return pyramid3;
	}

	public void setPyramid3(JLabel pyramid3) {
		this.pyramid3 = pyramid3;
	}

	public JLabel getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(JLabel errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Window2 getPlayWindow() {
		return playWindow;
	}

	public void setPlayWindow(Window2 playWindow) {
		this.playWindow = playWindow;
	}

}
