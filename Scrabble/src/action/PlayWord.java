package action;

import scrabBot.Direction;

public class PlayWord extends Action {
	private int row, column;
	private Direction dir;
	private String word;
	
	PlayWord(char column, int row, char dir, String word){
		super(Choice.PLAYWORD);
		this.row = row;
		this.column = (int)(column - 'A') + 1; //board is indexed starting from 1
		this.dir = (dir == 'A') ? Direction.HORIZONTAL : Direction.VERTICAL;
		this.word = word;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	public Direction getDirection(){
		return dir;
	}
	
	public String getWord(){
		return word;
	}
}
