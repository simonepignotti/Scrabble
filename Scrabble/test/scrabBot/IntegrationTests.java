/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

public class IntegrationTests {

	public static void main(String[] args) {
		Board myBoard = new Board();
		Pool myPool = new Pool();
		Player P1 = new Player();
		Frame frame = P1.getPlayerFrame();
		
		System.out.println("\nStarting Automated Integration Tests: \n");
		
		System.out.println("Pool's size: "+ myPool.getPoolSize());
		frame.refillFrame(myPool);
		
		System.out.print("Player1's Frame: ");
		frame.showFrame();		
		System.out.println("Pool's new size: "+ myPool.getPoolSize());
		
		if( myBoard.checkPlacement(frame.getString(), Board.CENTER_ROW, Board.CENTER_ROW, Direction.HORIZONTAL, P1) == CheckResult.OK )
			     myBoard.placeWord(frame.getString(), Board.CENTER_ROW, Board.CENTER_ROW, Direction.HORIZONTAL);
		
		myBoard.displayBoard();
		
		for(Character letter : frame.getLetters())
			frame.removeLetter(letter);
		
		frame.refillFrame(myPool);
		
		System.out.println("Player1's new Frame: ");
		frame.showFrame();
		System.out.println();
		
		System.out.println("Automated Integration Test Completed");
	}

}
