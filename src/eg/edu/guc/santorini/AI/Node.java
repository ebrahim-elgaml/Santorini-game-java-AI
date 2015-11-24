package eg.edu.guc.santorini.AI;

import java.util.ArrayList;
import eg.edu.guc.santorini.Board;
import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;
import eg.edu.guc.santorini.utilities.Location;

public class Node implements NodeInterface {
	Board head;
	int value;
	ArrayList<Node> children;
	int depth ;
	int levelDifficulty;
	public Node(Board b) {
		head = b;
		value = 1;
		depth = 0;
		children = new ArrayList<Node>();
		levelDifficulty = 1;
	}

	public Node() {
		value = 0;
	}

	/*public void formEquation1() { 
		if (head.isWinner(head.getPlayer2())) {
			value = 9999;
		}
		if (head.isWinner(head.getPlayer1())) {
			value = -9999;
		}
		for (int i = 0; i < children.size(); i++) {
			ArrayList<Location> possibleT1 = children.get(i).head.getPlayer1()
					.getT1().possiblePlacements();
			for (int j = 0; j < possibleT1.size(); j++) {
				if (possibleT1.get(j).getLevel() == 3
						&& children.get(i).head.canPlace(children.get(i).head
								.getPlayer1().getT1(), possibleT1.get(j))) {
					if (children.get(i).children.get(j).head.canMove(children
							.get(i).children.get(j).head.getPlayer2().getT1(),
							possibleT1.get(j))) {
						children.get(i).children.get(j).value /= 2;
					}

				}
			}
		}
	}
*/

	public void playAI() throws InvalidMoveException, InvalidPlacementException {//complete turn
		//System.out.println("called");
		
		Board temp = (Board) head.copyBoard();
		//System.out.println(head.getPlayer2().getT1().getPieceLocation());
		ArrayList<Location> possibleMoves = temp.getPlayer2().getT1()
				.possibleMoves();
		//System.out.println(temp.getPlayer2().getT1().getPieceLocation());
		//System.out.println(possibleMoves.size());
		//for(int i = 0;i<possibleMoves.size();i++){
			//System.out.println(possibleMoves.get(i).getYAxis()+" ,"+possibleMoves.get(i).getXAxis());
		//}
		
		/*String[][] s = temp.display();
		for(int i = 0 ;i<s.length;i++){
			for(int j = 0 ;j<s[i].length;j++){
				System.out.println(s[i][j]);
			}
		}*/
		
		for (int i = 0; i < possibleMoves.size(); i++) {
			if (temp.canMove(temp.getPlayer2().getT1(), possibleMoves.get(i))) {
				Board temp2 = temp.copyBoard();
				temp2.move2(temp2.getPlayer2().getT1(), possibleMoves.get(i));
				//System.out.println("3amlt move");
				ArrayList<Location> possiblePlacements = temp2.getPlayer2()
						.getT1().possiblePlacements();
				//System.out.println(possiblePlacements.size());
				for (int c = 0; c < possiblePlacements.size(); c++) {
					Board temp3 = temp2.copyBoard();
					if (temp3.canPlace(temp3.getPlayer2().getT1(),
							possiblePlacements.get(c))) {
						temp3.place2(temp3.getPlayer2().getT1(),
								possiblePlacements.get(c));
						Node n = new Node(temp3);
						n.depth = depth+1;
						
						this.children.add(n);
						//System.out.println("x");
					}
				}
			}

		}
		//System.out.println(children.size());
		this.setValuePlay();
		//System.out.println(children.size());
		for (int k = 0; k < children.size(); k++) {
			ArrayList<Location> possibleMoves2 = children.get(k).head
					.getPlayer1().getT1().possibleMoves();
			Board temp4 = children.get(k).head.copyBoard();
			for (int i = 0; i < possibleMoves2.size(); i++) {
				if (temp4.canMove(temp4.getPlayer1().getT1(),
						possibleMoves2.get(i))) {
					Board temp5 = temp4.copyBoard();
					temp5.move2(temp5.getPlayer1().getT1(),
							possibleMoves2.get(i));
					ArrayList<Location> possiblePlacements2 = temp5
							.getPlayer1().getT1().possiblePlacements();
					for (int c = 0; c < possiblePlacements2.size(); c++) {
						Board temp6 = temp5.copyBoard();
						if (temp6.canPlace(temp6.getPlayer1().getT1(),
								possiblePlacements2.get(c))) {
							temp6.place2(temp6.getPlayer1().getT1(),
									possiblePlacements2.get(c));
							Node n = new Node(temp6);
							n.depth = children.get(k).value+1 ;
							
							
							this.children.get(k).children.add(n);
							//System.out.println("x");
						}
					}
				}

			}
			//children.get(k).setValuePlay();
		}
		//System.out.println("2tl3");

	}

	
	public void setValuePlay() throws InvalidMoveException, InvalidPlacementException{
		if(children == null){
			return;
		}
		for(int i = 0 ;i<children.size();i++){
			Board currBoard = children.get(i).head;
			if(currBoard.getWinner()==currBoard.getPlayer1()){
				children.get(i).value+=-9999;
				//value += -9999;
				return;
			}
			if(currBoard.getWinner()==currBoard.getPlayer2()){
				children.get(i).value+=9999;
				//value += 9999;
				return;
			}
			ArrayList<Location> possibleMoves1 = currBoard.getPlayer1().getT1().possibleMoves();
			for(int j = 0 ;j<possibleMoves1.size();j++){
				Location a = possibleMoves1.get(i);
				if(currBoard.canMove(currBoard.getPlayer1().getT1(),a)){
					if(a.getLevel()-currBoard.getPlayer1().getT1().getPieceLocation().getLevel()==1){
					//	value += -100;
						children.get(i).value += -100;
						return;
					}else{
						Board copy = currBoard.copyBoard();
						copy.move2(copy.getPlayer1().getT1(),a);
						ArrayList<Location> possiblePlacements1 = copy.getPlayer1().getT1().possiblePlacements();
						for(int k = 0 ;k<possiblePlacements1.size();k++){
							Location b = possiblePlacements1.get(k);
							if(copy.canPlace(copy.getPlayer1().getT1(),b)){
								if(b.getLevel()==2){
						//			value += -1000;
									children.get(i).value += -1000;
									return;
								}
								if(b.getLevel()==copy.getPlayer1().getT1().getPieceLocation().getLevel()){
							//		value += -500;
									children.get(i).value += -500;
									return;
								}
								
							}
						}
						
					}
					
				}
			}
			ArrayList<Location> possibleMoves2 = currBoard.getPlayer2().getT1().possibleMoves();
			for(int j = 0 ;j<possibleMoves2.size();j++){
				Location a = possibleMoves2.get(i);
				if(currBoard.canMove(currBoard.getPlayer2().getT1(),a)){
					if(a.getLevel()==3){
						//value+=1000;
						children.get(i).value+=1000;
						return;
					}
					if(a.getLevel()-currBoard.getPlayer2().getT1().getPieceLocation().getLevel()==1){
						//value += 100;
						children.get(i).value += 100;
						return;
					}else{
						Board copy = currBoard.copyBoard();
						copy.move2(copy.getPlayer2().getT1(),a);
						ArrayList<Location> possiblePlacements2 = copy.getPlayer2().getT1().possiblePlacements();
						for(int k = 0 ;k<possiblePlacements2.size();k++){
							Location b = possiblePlacements2.get(k);
							if(copy.canPlace(copy.getPlayer2().getT1(),b)){
								if(b.getLevel()==2){
							//		value += 1000;
									children.get(i).value += 1000;
									return;
								}
								if(b.getLevel()==copy.getPlayer2().getT1().getPieceLocation().getLevel()){
								//	value += 500;
									children.get(i).value += 500;
									return;
								}
								
							}
						}
						
					}
					
				}
			}
		}
		/*if(head.getWinner()==head.getPlayer2()){
			value = 9999;
			return;
		}
		if(head.getWinner()==head.getPlayer1()){
			value = -9999;
			return;
		}
		ArrayList<Location> possibleMoves1 = head.getPlayer1().getT1().possibleMoves();
		ArrayList<Location> possiblePlacements1 = head.getPlayer1().getT1().possiblePlacements();
		
		for(int i = 0 ;i<possibleMoves1.size();i++){
			Location a = possibleMoves1.get(i);
			if(head.canMove(head.getPlayer1().getT1(),a)){
				if(a.getLevel()-head.getPlayer1().getT1().getPieceLocation().getLevel()==1){
					value = 10;
					return;
				}
			}
		}*/
	}
	public  int getArrayMax(){
		if(children==null){
			return -1;
		}
		if(children.size()==0){
			return -1;
		}
		int max = children.get(0).value;
		for(int i = 1 ;i<children.size();i++){
			if(children.get(i).value>max){
				max = children.get(i).value;
			}
		}
		return max;
	}
	public  int getArrayMin(){
		if(children==null){
			return -1;
		}
		int min = children.get(0).value;
		for(int i = 1 ;i<children.size();i++){
			if(children.get(i).value<min){
				min = children.get(i).value;
			}
		}
		return min;
	}
	public void setValue (){
		if (children == null){
			return ;	
		}
		if(depth%2==0){
			value = this.getArrayMax();
			return;
		}
		
		if(depth%2==1){
			value = this.getArrayMin();
			return;
		}
		
	}
	public void setValue27laL3ba(){
		if(children.size()==0){
			return;
		}
		if(children==null){
			return;
		}
		for(int i = 0 ;i<children.size();i++){
			children.get(i).setValue27laL3ba();
			children.get(i).setValue();
		}
	}

	public void constructTree(int x) throws InvalidMoveException, InvalidPlacementException{
		if(x == 0){
			return;
		}
		if(this.children.size()==0){
			this.playAI();
			for(int i = 0 ;i<children.size();i++){
				for(int j = 0 ;j<children.get(i).children.size();j++){
					if(x==1){
						return;
					}
					x--;
					children.get(i).children.get(j).constructTree(x);
				}
			}
			//System.out.println(children.size()+"asd");
			return;
		}
		//for(int i = 0 ;i<children.size();i++){
		//	children.get(i).constructTree();
		//}
	}
	/*public void Play() throws InvalidMoveException, InvalidPlacementException{
		for(int i= 0 ;i<1;i++){
			constructTree();
		}
	}*/
	public Board getEl3baEl7lwa() throws InvalidMoveException, InvalidPlacementException{
		
		constructTree(levelDifficulty);
		//System.out.println("el3ba");
		setValue27laL3ba();
		int max = children.get(0).value;
		Board result = children.get(0).head;
		for(int i = 1;i<children.size();i++){
			if(max<children.get(i).value){
				max =children.get(i).value;
				result = children.get(i).head;
			}
		}
		return result;
		
	}

}
