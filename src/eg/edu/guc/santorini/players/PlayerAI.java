package eg.edu.guc.santorini.players;

import eg.edu.guc.santorini.tiles.Cube;
import eg.edu.guc.santorini.tiles.Piece;
import eg.edu.guc.santorini.tiles.Pyramid;

public class PlayerAI extends Player {
	public PlayerAI(){
		
	}
	public PlayerAI(int i){
		super("Rashida",i);
	}
	public PlayerAI copyPlayer() {
		
		PlayerAI r = new PlayerAI(super.getName(),super.getT1().copyPiece(),super.getT2().copyPiece());
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
	public PlayerAI(String name, int x) {
		super(name, x);
		// TODO Auto-generated constructor stub
	}
	public PlayerAI(String name, Piece t1, Piece t2) {
		super(name, t1, t2);
		// TODO Auto-generated constructor stub
	}


}
