package eg.edu.guc.santorini.AI;

import eg.edu.guc.santorini.exceptions.InvalidMoveException;
import eg.edu.guc.santorini.exceptions.InvalidPlacementException;

public interface NodeInterface {

	void playAI() throws InvalidMoveException, InvalidPlacementException;

}
