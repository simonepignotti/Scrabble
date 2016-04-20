package action;

import java.util.Scanner;

public class ActionFactory {
	private static Scanner sc;

	public static Action buildAction(String choice) {
		sc = new Scanner(choice);
        String firstToken = sc.next();
        
        switch (firstToken) {
        case "QUIT":
            return new Quit();
        case "PASS":
            return new PassTurn();
        case "HELP":
            return new GetHelp();
        case "EXCHANGE":
        	String lettersToExchange = sc.next();
        	return new ExchangeLetters(lettersToExchange);
        default:
        	char column = firstToken.charAt(0);
        	int row = Integer.parseInt(firstToken.substring(1));
        	char dir = sc.next().charAt(0);
        	String wordToPlace = sc.next();
            return new PlayWord(column, row, dir, wordToPlace);
        }
    }
}
