package eg.edu.guc.santorini.players;

import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;

public class Player {
	private String name;
	private Piece t1;
	private Piece t2;

	public Player() {
	}

	public Player(String name, int x) {
		this.name = name;
		if (x == 1) {
			this.t1 = new Cube();
			this.t2 = new Cube();
		} else {
			if (x == 2) {
				this.t1 = new Pyramid();
				this.t2 = new Pyramid();
			}
		}
	}
	public Player(String name, Piece t1, Piece t2) {
		super();
		this.name = name;
		this.t1 = t1;
		this.t2 = t2;
	}

	public Player copyPlayer() {
		
		Player r = new Player(name,t1.copyPiece(),t2.copyPiece());
		//System.out.println("mm"+r.getT1().getPieceLocation());
		/*int c = 0;
		if(t1 instanceof Cube ){
			c =1;
		}
		if(t1 instanceof Pyramid ){
			c =2;
		}
		Player r = new Player(name,c);*/
		return r;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Piece getT1() {
		return t1;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	public void setT1(Piece t1) {
		this.t1 = t1;
	}

	public Piece getT2() {
		return t2;
	}

	public void setT2(Piece t2) {
		this.t2 = t2;
	}

}
