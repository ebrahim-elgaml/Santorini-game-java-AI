package eg.edu.guc.santorini.gui;

public class Play {

	private ChoosePlayers choose;

	public ChoosePlayers getA() {
		return choose;
	}

	public void setA(ChoosePlayers a) {
		this.choose = a;
	}

	public Play() {

	}

	public void run() {
		
		Start a = new Start();
		do {
			if (a.getPlayWindow().isVisible()) {
				break;
			}
		} while (true);
		a.getPlayWindow().changeCounter();
		do {
			if (a.getPlayWindow().checkWin() != null) {
				break;
			}
		} while (true);
		a.getPlayWindow().setVisible(false);
		a.getPlayWindow().setGameOverWindow(
				new LastWindow(a.getPlayWindow().checkWin(), a.getPlayWindow()
						.getCounter().getText()));
		do {
			if (a.getPlayWindow().getGameOverWindow().isPlayagainBoolean()) {
				break;
			}

		} while (true);
		a.getPlayWindow().getGameOverWindow().playAgain();
	}
	
	
	public static void main(String[] args) {
		Play p = new Play();
		p.run();
	}

	
}
