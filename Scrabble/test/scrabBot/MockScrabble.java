/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

public class MockScrabble extends Scrabble {
	
	public MockScrabble(){
		turn = new Player[2];
		turn[0] = new Player();
		turn[0].setPlayerName("Lapo");
		turn[0].getPlayerFrame().refillFrame(pool);
		turn[1] = new Player();
		turn[1].setPlayerName("Simone");
		turn[1].getPlayerFrame().refillFrame(pool);
		board = new MockBoard();
		pool = new MockPool();
		activePlayer = turn[0];
		currentPlayerNumber = 0;
		ui = new UI(System.in);
		keepPlaying = true;
		dict = new Dictionary();
	}
}
