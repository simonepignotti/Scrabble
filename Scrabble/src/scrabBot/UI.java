/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import java.io.InputStream;
import java.util.Scanner;

import action.Action;
import action.ActionFactory;

public class UI {
	
	private Scanner sc;
	private String nextAction;
	private InputStream source;

	public UI(InputStream source) {
		this.source = source;
	}
	
	public Action getUserInput(){
		sc = new Scanner(source);
		nextAction = sc.nextLine();
		while(!validateInput(nextAction)) {
			nextAction = sc.nextLine();
		}
		return ActionFactory.buildAction(nextAction);
	}
	
	public void printMessage(String msg, boolean newline){
		if(newline)
			System.out.println(msg);
		else
			System.out.print(msg);
	}
	
	public String getPlayerName(int currentPlayer){
		System.out.print("Player " + (currentPlayer+1) + " name: ");
		sc = new Scanner(source);
		String name = sc.nextLine();
		while (!name.matches("[a-zA-Z]{1,19}")) {
			
			System.out.println("Error: use a name shorter than 20 letters a-z");
			
			System.out.print("Player " + (currentPlayer+1) + " name: ");
			name = sc.nextLine();		}
		return name;
	}
	
	public void gameInfo(Scrabble scrabble){
		scrabble.board.displayBoard();
		System.out.println("Pool's size: "+scrabble.pool.getPoolSize());
		for (Player player : scrabble.turn)
			System.out.println(player.getPlayerName()+" : "+player.getPlayerScore());
		System.out.println();
	}
	
	public int checkChallenge(Player[] players){
		boolean decided = false;
		int challengerNumber = -1;
		sc = new Scanner(source);
		while (!decided) {
			System.out.println("Challenge? Y your_name / N");
			String answer = sc.nextLine();
			while (!answer.matches("Y [a-zA-Z]{1,19}|N")) {
				System.out.println("Error: incorrect answer format");
				System.out.println("Challenge? Y your_name / N");
				answer = sc.nextLine();
			}
			if (answer.matches("N")) {
				challengerNumber = -1;
				decided = true;
			}
			else {
				answer = answer.substring(2);
				int i;
				for (i = 0; i < players.length; i++)
					if (answer.matches(players[i].getPlayerName())) {
						challengerNumber = i;
						decided = true;
					}
				if (!decided)
					System.out.println("Error: no player named " + answer + " found");	
			}
		}
		return challengerNumber;
	}
	
	public int getNumberOfPlayers(){
		System.out.print("Number of players: ");
		sc = new Scanner(source);
		String numOfP = sc.nextLine();
		while (!numOfP.matches("[2-4]")) {
			System.out.println("Error: insert a number between 2 and 4");
			System.out.print("Number of players: ");
			numOfP = sc.next();
		}
		return Integer.parseInt(numOfP);
	}
	
	public void promptActivePlayer(Player activePlayer,Board board){
		System.out.println(activePlayer.getPlayerName() + "'s turn. (enter \"HELP\" for help)");
		System.out.print("Frame: ");
		activePlayer.getPlayerFrame().showFrame();
	}
	
	public boolean validateInput(String userInput) {
		return userInput.matches("QUIT|PASS|HELP|EXCHANGE [A-Z&]{1,7}|[A-O]([1-9]|1[0-5]) [AD] [a-zA-Z&]{1,15}");
	}
}
