package chess.pieces.move;

import chess.Player;


public class QueenMoving extends ManyStepsMoving{
	private final static Shift[] SHIFTS=new Shift[]{new Shift(0,1), new Shift(1,1), new Shift(1,0), new Shift(1,-1), new Shift(0,-1), new Shift(-1,-1), new Shift(-1,0), new Shift(-1,1) };
	
	protected Shift[] getShifts(Player player) {
		return SHIFTS;
	}

}
