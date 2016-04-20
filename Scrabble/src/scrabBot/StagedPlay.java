package scrabBot;

public class StagedPlay {
	public Board board;
	public int row, column;
	public String word;
	public Player player;
	public int points;
	
	public StagedPlay(Board board, int row, int column, String word, Player player, int points){
		this.board = board;
		this.row = row;
		this.column = column;
		this.word = word;
		this.player = player;
		this.points = points;
	}
}
