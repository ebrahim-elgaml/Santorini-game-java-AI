package eg.edu.guc.santorini.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoosePlayers extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1908359096879268362L;
	JButton singlePlayer;
	JButton doublePlayer;
	JPanel buttonPanel;
	JLabel icon;
	public ChoosePlayers(){
		setTitle("choose player window");
		setSize(400, 400);
		setLocation(200, 10);
		Container content = getContentPane();
		content.setBackground(Color.BLACK);
		content.setLayout(new BorderLayout());
		WindowDestroyer w = new WindowDestroyer();
		addWindowListener(w);
		createButtons();
		icon = new JLabel (new ImageIcon("xcxc.png"));
		this.getContentPane().add(icon,BorderLayout.CENTER);
		
		this.setVisible(true);
		
	}
	public void createButtons(){
		singlePlayer = new JButton("Single Player");
		singlePlayer.setActionCommand("singlePlayer");
		singlePlayer.addActionListener(this);

		doublePlayer = new JButton("Double Player");
		doublePlayer.setActionCommand("doublePlayer");
		doublePlayer.addActionListener(this);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,2));
		buttonPanel.add(singlePlayer);
		buttonPanel.add(doublePlayer);
		this.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton temp = (JButton) e.getSource();
		if (temp == null) {
			return;
		}
		if(temp.getActionCommand().equals("singlePlayer")){
			this.setVisible(false);
			WindowSinglePlayer a  = new WindowSinglePlayer();
			a.setVisible(true);
			return;
		}
		if(temp.getActionCommand().equals("doublePlayer")){
			this.setVisible(false);
			Start a  = new Start();
			a.setVisible(true);
		}
		
	}
	public static void main(String[]args){
		//ChoosePlayers a = new ChoosePlayers();
		
	}
	

}
