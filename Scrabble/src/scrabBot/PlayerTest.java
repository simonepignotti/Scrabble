/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import java.util.ArrayList;

public class PlayerTest {

	public static void main(String[] args) {
		Pool testPool = new Pool();
		Player testPlayer = new Player();
		Frame testFrame;
		ArrayList<Character> letters;
		int poolExpectedSize, poolActualSize, letterExpectedValue, letterActualValue, playerExpectedScore, playerActualScore ;
		String playerExpectedName, playerActualName;
		
		System.out.println("-----Pool-----\n");
		
		System.out.println("Testing initial pool size:");
		poolExpectedSize = 100;
		poolActualSize = testPool.getPoolSize();
		System.out.println("actual size = "+ poolActualSize);
		System.out.println("expected size = "+ poolExpectedSize);
		if(poolActualSize == poolExpectedSize)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		System.out.println("Testing draw method: ");
		System.out.println("Letter: "+ testPool.drawRandomTile());
		poolExpectedSize = 99;
		poolActualSize = testPool.getPoolSize();
		System.out.println("actual pool size = "+ poolActualSize);
		System.out.println("expected pool size = "+ poolExpectedSize);
		if(poolActualSize == poolExpectedSize)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		System.out.println("Testing reset method: ");
		testPool.resetPool();
		poolExpectedSize = 100;
		poolActualSize = testPool.getPoolSize();
		System.out.println("actual pool size = "+ poolActualSize);
		System.out.println("expected pool size = "+ poolExpectedSize);
		if(poolActualSize == poolExpectedSize)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		System.out.println("Testing isEmpty method: ");
		for(int i = 0; i < poolActualSize ; i++) testPool.drawRandomTile(); // draw all the tiles
		if(testPool.isEmpty())
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		System.out.println("Testing draw from empty pool:");
		System.out.println("Expecting EmptyPoolException");
		try{
			testPool.drawRandomTile();
			System.out.println("\t\t\t\tFAIL");
		}catch(EmptyPoolException e){
			System.out.println("\t\t\t\tOK");
		}
		
		
		System.out.println("Testing checkValue method: ");
		letterExpectedValue = 4;
		letterActualValue = testPool.checkValue('F');
		System.out.println("Expected letter value: "+letterExpectedValue);
		System.out.println("Actual letter value: "+letterActualValue);
		if(letterActualValue == letterExpectedValue)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing non-existing letter:");
		System.out.println("Expecting WrongLetterException");
		try{
			testPool.checkValue('#');
			System.out.println("\t\t\t\tFAIL");
		}catch(WrongLetterException e){
			System.out.println("\t\t\t\tOK");
		}
		
		System.out.println("Testing empty pool:");
		poolExpectedSize = 0;
		for(int i = testPool.getPoolSize(); i>0; i--) testPool.drawRandomTile();
		poolActualSize = testPool.getPoolSize();
		System.out.println("expected pool size: "+poolExpectedSize);
		System.out.println("actual pool size: "+poolActualSize);
		if(poolExpectedSize == poolActualSize)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");		
		
		System.out.println("-----Player-----\n");
		
		System.out.println("Testing get first Player name:");
		playerExpectedName = "Player1";
		playerActualName = testPlayer.getPlayerName();
		System.out.println("expected player's name: "+playerExpectedName);
		System.out.println("actual player's name: "+playerActualName);
		if(playerExpectedName.equals(playerActualName))
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing get second Player name:");
		Player testPlayer2 = new Player();
		playerExpectedName = "Player2";		
		playerActualName = testPlayer2.getPlayerName();
		System.out.println("expected player's name: "+playerExpectedName);
		System.out.println("actual player's name: "+playerActualName);
		if(playerExpectedName.equals(playerActualName))
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		
		System.out.println("Testing set Player name:");
		playerExpectedName = "Foo";
		testPlayer.setPlayerName(playerExpectedName);
		playerActualName = testPlayer.getPlayerName();
		System.out.println("expected player's name: "+playerExpectedName);
		System.out.println("actual player's name: "+playerActualName);
		if(playerExpectedName.equals(playerActualName))
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing increase score:");
		playerExpectedScore = 42;
		testPlayer.increasePlayerScoreBy(42);
		playerActualScore = testPlayer.getPlayerScore();
		System.out.println("expected player's score: "+playerExpectedScore);
		System.out.println("actual player's score: "+playerActualScore);
		if(playerExpectedScore == playerActualScore)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing reset Player Data:");
		playerExpectedScore = 0;
		testPlayer.increasePlayerScoreBy(42);
		testPlayer.resetPlayerData();
		playerActualScore = testPlayer.getPlayerScore();
		System.out.println("expected player's score: "+playerExpectedScore);
		System.out.println("actual player's score: "+playerActualScore);
		if(playerExpectedScore == playerActualScore)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		
		System.out.println("-----Frame-----\n");
		
		testFrame = testPlayer.getPlayerFrame();
		testPool.resetPool();
		
		System.out.println("Testing isEmpty method: ");
		
		if(testFrame.isEmpty())
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing refill method: ");
		testFrame.refillFrame(testPool);
		testFrame.showFrame();
		if(!testFrame.isEmpty())
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		//reinitialize pool and refill frame
		testPool.resetPool();
		testFrame.refillFrame(testPool);
		
		System.out.println("Testing get/remove letter:");
		letters = testFrame.getLetters();
		testFrame.showFrame();
		System.out.println("Removing: "+letters.get(0));
		testFrame.removeLetter(letters.get(0));
		if(testFrame.getFrameSize() == 6)
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing contains letter:");
		letters = testFrame.getLetters();
		testFrame.showFrame();
		System.out.println("Checking: "+letters.get(0));
		if(testFrame.containsLetter(letters.get(0)))
			System.out.println("\t\t\t\tOK");
		else
			System.out.println("\t\t\t\tFAIL");
		
		System.out.println("Testing remove wrong letter:");
		System.out.println("Expecting WrongLetterException");
		try{
			testFrame.removeLetter('#');
			System.out.println("\t\t\t\tFAIL");
		}catch(WrongLetterException e){
			System.out.println("\t\t\t\tOK");
		}
		
	}

}
