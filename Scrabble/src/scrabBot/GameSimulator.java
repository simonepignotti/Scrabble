/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

public class GameSimulator {
	public static void main(String[] args) {
		Scrabble game = new Scrabble();
		
		RunUnitTests.main(args);
		System.out.println();
		game.startGame();
	}
}