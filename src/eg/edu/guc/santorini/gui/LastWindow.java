package eg.edu.guc.santorini.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import eg.edu.guc.santorini.players.Player;

public class LastWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = 4008317156046492145L;
	private JLabel winMsg;
	private JLabel dur;
	private JPanel buttonPanel;
	private JButton playAgain;
	private JButton exit;
	private boolean playagainBoolean;

	public LastWindow() {

	}

	public LastWindow(Player p, String duration) {
		JLabel gameOver = new JLabel("       	          Game Over");
		winMsg = new JLabel("            Congratulation " + p.getName()
				+ " wins ");
		dur = new JLabel("      The game takes " + duration + "");
		this.setLayout(new GridLayout(4, 1));
		this.getContentPane().add(gameOver);
		this.getContentPane().add(winMsg);
		this.getContentPane().add(dur);
		createPanel();
		this.add(buttonPanel);
		gameOver.setFont(new Font(gameOver.getFont().getName(), Font.PLAIN, 40));
		gameOver.setForeground(Color.BLUE);
		winMsg.setFont(new Font(gameOver.getFont().getName(), Font.PLAIN, 40));
		winMsg.setForeground(Color.BLUE);
		dur.setFont(new Font(gameOver.getFont().getName(), Font.PLAIN, 40));
		dur.setForeground(Color.BLUE);
		this.setSize(1000, 700);
		playagainBoolean = false;
		WindowDestroyer w = new WindowDestroyer();
		addWindowListener(w);
		this.setVisible(true);

	}

	public void createPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		playAgain = new JButton("Play Again");
		playAgain.setActionCommand("Play Again");
		playAgain.addActionListener(this);
		exit = new JButton("EXIT");
		exit.setActionCommand("Exit");
		exit.addActionListener(this);
		buttonPanel.setBackground(Color.RED);
		buttonPanel.add(playAgain);
		buttonPanel.add(exit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if (temp == null) {
			return;
		}
		if (e.getActionCommand().equals("Play Again")) {
			playagainBoolean = true;
		} else if (temp.getActionCommand().equals("Exit")) {
			System.exit(0);
		}

	}

	public void playAgain() {
		this.setVisible(false);
		Play a = new Play();
		a.run();
	}

	public boolean isPlayagainBoolean() {
		return playagainBoolean;
	}

	public void setPlayagainBoolean(boolean playagainBoolean) {
		this.playagainBoolean = playagainBoolean;
	}

	public JLabel getWinMsg() {
		return winMsg;
	}

	public void setWinMsg(JLabel winMsg) {
		this.winMsg = winMsg;
	}

	public JLabel getDur() {
		return dur;
	}

	public void setDur(JLabel dur) {
		this.dur = dur;
	}

	public JPanel getButtonPanel() {
		return buttonPanel;
	}

	public void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	public JButton getPlayAgain() {
		return playAgain;
	}

	public void setPlayAgain(JButton playAgain) {
		this.playAgain = playAgain;
	}

	public JButton getExit() {
		return exit;
	}

	public void setExit(JButton exit) {
		this.exit = exit;
	}

}
