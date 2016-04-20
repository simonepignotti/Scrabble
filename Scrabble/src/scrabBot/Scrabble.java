/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import java.util.ArrayList;
import java.util.Random;
import action.Action;
import action.ExchangeLetters;
import action.PlayWord;

public class Scrabble {
	
	protected Board board;
	protected Pool pool;
	protected Player activePlayer;
	protected int currentPlayerNumber;
	protected UI ui;
	protected boolean keepPlaying, proceed;
	protected Action playerChoice;
	protected Dictionary dict;
	protected Player[] turn;
	protected Board stagingBoard;
	protected int numOfPass;
	protected Random rand;
	private int moveValue;
	private String lettersUsed;
	private int challenger;

	
	private int NUM_PLAYERS;
	
	public Scrabble(){
		pool = new Pool();
		board = new Board();
		stagingBoard = new Board();
		ui = new UI(System.in);
		keepPlaying = true;
		dict = new Dictionary();
		rand = new Random();
		numOfPass = 0;
	}
	

	public void startGame(){
		if (!dict.loadDictionary()) {
			ui.printMessage("Fatal error: cannot load dictionary", true);
			return;
		}
		NUM_PLAYERS = ui.getNumberOfPlayers();
		
		turn = new Player[NUM_PLAYERS];
		for(int i = 0; i<NUM_PLAYERS; i++){
			turn[i] = new Player();
			turn[i].setPlayerName(ui.getPlayerName(i));
			turn[i].getPlayerFrame().refillFrame(pool);
		}
		
		currentPlayerNumber = rand.nextInt(NUM_PLAYERS);
		activePlayer = turn[currentPlayerNumber];
		ui.printMessage(activePlayer.getPlayerName()+"starts.", true);
		
		while(keepPlaying){
			proceed = false;
			while(!proceed){
				if(activePlayer.checkIfLostChallenge()){
					ui.printMessage("Skipped "+activePlayer.getPlayerName() +"'s turn.", true);
					activePlayer.resetLostChallenge();
					proceed = true;
				}
				else{
					ui.gameInfo(this);
					ui.promptActivePlayer(activePlayer,board);
					playerChoice = ui.getUserInput();
					switch(playerChoice.getChoice()){
						case PLAYWORD:	PlayWord wordToPlace = (PlayWord) playerChoice;
										CheckResult result = board.checkPlacement(wordToPlace.getWord(), 
												wordToPlace.getRow(), 
												wordToPlace.getColumn(), 
												wordToPlace.getDirection(),
												activePlayer);
										if((result == CheckResult.OK)){
											stagingBoard = new Board(board);
											
											moveValue = calculatePlacementPoints(	wordToPlace.getWord(), 
																					wordToPlace.getRow(),
																					wordToPlace.getColumn(), 
																					wordToPlace.getDirection());
											
											lettersUsed = stagingBoard.placeWord(wordToPlace.getWord(), 
																				wordToPlace.getRow(), 
																				wordToPlace.getColumn(), 
																				wordToPlace.getDirection());
											
											activePlayer.increasePlayerScoreBy(moveValue);
											
											// CHALLENGE **********************
											if((challenger = ui.checkChallenge(turn)) != -1){
												if (checkLegality(	wordToPlace.getWord(), 
																	wordToPlace.getRow(), 
																	wordToPlace.getColumn(), 
																	wordToPlace.getDirection())
													){ //the word is legal 
													ui.printMessage("The move is legal. "+turn[challenger].getPlayerName()+" loses his turn." , true);
													turn[challenger].setLostChallenge();
													board = stagingBoard; //the play is finalized
													activePlayer.getPlayerFrame().removeLetters(lettersUsed);
												}else { //the word is illegal
													ui.printMessage("The move is illegal.", true);
													activePlayer.increasePlayerScoreBy(-moveValue);
												}
											}
											else{
													board = stagingBoard; // no one challenged, the play is finalized
													activePlayer.getPlayerFrame().removeLetters(lettersUsed);
												}
											// END CHALLENGE ******************
											
											if(pool.getPoolSize() != 0){
													try{
														activePlayer.getPlayerFrame().refillFrame(pool);
													} catch (EmptyPoolException e){
														ui.printMessage("Letters in the pool finished.", true);
													}
											} else {
													if(activePlayer.getPlayerFrame().getFrameSize() == 0){
														keepPlaying = false;
														endGame();
													}
												}
											
											proceed = true;
										}
										else //second branch of if((result == CheckResult.OK))
											ui.printMessage("Invalid Placement. Error: "+result.name(), true);
										
										numOfPass = 0;
										break;
						case PASSTURN: 	proceed = true;
										if (++numOfPass >= 3){
											keepPlaying = false;
											endGame();
										}
										break;
						case GETHELP:	displayHelp();
										break;
						case EXCHANGELETTERS:	if(pool.getPoolSize() >= 7)
													proceed = exchangeLetters(((ExchangeLetters)playerChoice).getLettersToChange());
												else
													ui.printMessage("Not enough letters remaining", true);
												numOfPass = 0;
												break;
						case QUIT: 		ui.printMessage("GAME OVER", true);
										proceed = true;
										quitGame();
										break;
						default:	break;
					}
				}
			}
			passTurn();	
	}
}
	
	private void endGame(){
		ArrayList<Character> unusedLetters;
		for( int i = 0; i < NUM_PLAYERS; i++){
			if(i != currentPlayerNumber){
				unusedLetters = turn[i].getPlayerFrame().getLetters();
				for(Character letter : unusedLetters){
					activePlayer.increasePlayerScoreBy(pool.checkValue(letter));
					turn[i].increasePlayerScoreBy(-pool.checkValue(letter));
				}
			}
		}
		
		ui.gameInfo(this);
		
		int maxScore = turn[0].getPlayerScore();
		int winner = 0;
		int ties = 0;
		int tmp;
		for(int i = 1; i<NUM_PLAYERS; i++){
			tmp = turn[i].getPlayerScore();
			if(tmp > maxScore){
				maxScore=tmp;
				winner = i;
				ties = 0;
			}
			else
				if(tmp == maxScore){
					ties += 1;
				}
		}
		
		if(ties == NUM_PLAYERS -1)
			ui.printMessage("TIE", true);
		else
			ui.printMessage("The winner is "+turn[winner].getPlayerName(), true);
	}
	
	private void passTurn(){
		currentPlayerNumber = (currentPlayerNumber + 1)%NUM_PLAYERS;
		activePlayer = turn[currentPlayerNumber];
	}
	
	public boolean checkLegality(String word, int row, int column, Direction dir){
		int limit;
		boolean result;
		word = word.toUpperCase();
		ui.printMessage("Checking "+word, false);
		result = dict.dictionaryCheck(word); //Check word legality
		if(result == false)
			ui.printMessage(" -> Illegal.", true);
		else
			ui.printMessage(" -> Legal", true);
		if(result == false)
			return false;
		switch(dir){
		
		case VERTICAL: 	limit = row + word.length();
						for(int i = row; i<limit; i++ )
							if(	(column > 1 && stagingBoard.getLetterAt(i, column-1) != Board.FREE_LOCATION)
								|| (column < Board.MAX_COLUMN && stagingBoard.getLetterAt(i, column+1) != Board.FREE_LOCATION)	)
								//there are letters on either side of the word
								if(checkHorizontal(i, column) == false)
									return false;
						break;
						
		case HORIZONTAL: 	limit = column + word.length();
							for(int i = column; i<limit; i++ )
								if(	(row > 1 && stagingBoard.getLetterAt(row-1, i) != Board.FREE_LOCATION)
									|| (row < Board.MAX_ROW && stagingBoard.getLetterAt(row+1, i) != Board.FREE_LOCATION)	)
									//there are letters on either side of the word
									if(checkVertical(row, i) == false)
									return false;
							break;
		}
		return true;
	}
	
	public boolean checkHorizontal(int row, int column){
		int head = column;
		int tail = column;
		boolean result = false;
		String word = "";
		while(head > 1 && stagingBoard.getLetterAt(row, head-1) != Board.FREE_LOCATION) //Find start of word
			head--;
		while(tail < Board.MAX_COLUMN && stagingBoard.getLetterAt(row, tail+1) != Board.FREE_LOCATION) //Find end of word
			tail++;
		for( ; head<=tail; head++) //Build word
			word += stagingBoard.getLetterAt(row, head);
		word = word.toUpperCase();
		ui.printMessage("Checking "+word, false);
		result = dict.dictionaryCheck(word); //Check word legality
		if(result == false)
			ui.printMessage(" -> Illegal.", true);
		else
			ui.printMessage(" -> Legal", true);
		return result;
	}
	
	public boolean checkVertical(int row, int column){
		int head = row;
		int tail = row;
		boolean result = false;
		String word = "";
		while(head > 1 && stagingBoard.getLetterAt(head-1, column) != Board.FREE_LOCATION) //Find start of word
			head--;
		while(tail < Board.MAX_ROW && stagingBoard.getLetterAt(tail+1, column) != Board.FREE_LOCATION) //Find end of word
			tail++;
		for( ; head<=tail; head++)
			word += stagingBoard.getLetterAt(head, column); //Build word
		word = word.toUpperCase();
		ui.printMessage("Checking "+word, false);
		result = dict.dictionaryCheck(word); //Check word legality
		if(result == false)
			ui.printMessage(" -> Illegal.", true);
		else
			ui.printMessage(" -> Legal", true);
		return result;
	}
	
	public int calculatePlacementPoints(String wordPlayed, int row, int column, Direction dir){
		int total = 0, otherWordsTotal = 0, wordMult = 1, lettersUsed = 0;
		
		for(int i = 0; i < wordPlayed.length(); i++) {
			if (board.getLetterAt(row, column) == Board.FREE_LOCATION) {
				lettersUsed++;
				wordMult *= Board.wordMultiplier[row][column];
				total += Board.letterMultiplier[row][column] * pool.checkValue(wordPlayed.charAt(i));
				if (dir == Direction.VERTICAL && ((column > 1 && board.getLetterAt(row, column-1) != Board.FREE_LOCATION) || (column < 15 && board.getLetterAt(row, column+1) != Board.FREE_LOCATION))) {
					otherWordsTotal += calculateOtherWordsPoints(wordPlayed.charAt(i), row, column, Direction.HORIZONTAL);
				}
				else if (dir == Direction.HORIZONTAL && ((row > 1 && board.getLetterAt(row-1, column) != Board.FREE_LOCATION) || (row < 15 && board.getLetterAt(row+1, column) != Board.FREE_LOCATION))) {
					otherWordsTotal += calculateOtherWordsPoints(wordPlayed.charAt(i), row, column, Direction.VERTICAL);
				}
			}
			else {
				total += pool.checkValue(wordPlayed.charAt(i));
			}
			if(dir == Direction.VERTICAL)
				row++;
			else
				column++;
		}
		
		total *= wordMult;
		
		total += otherWordsTotal;
		
		if (lettersUsed == 7) {
			total += 50;
		}

		return total;
	}
	
	private void quitGame(){
		keepPlaying = false;
	}
	
	private void displayHelp(){
		System.out.println("Legal options:\nQUIT\nHELP\nPASS\nEXCHANGE <letters>\n<grid ref> <across/down> <word>  e.g. A1 A HELLO");
	}
	
	
	//use the frame of the active player
	private boolean exchangeLetters( String lettersToExchange ){
		Frame frame = activePlayer.getPlayerFrame();
		boolean result;
		result = frame.containsLetters(lettersToExchange);
		if(result){
			frame.removeLetters(lettersToExchange);
			frame.refillFrame(pool);
		}
		return result;
	}
	
	private int calculateOtherWordsPoints(char startingLetter, int row, int column, Direction dir){
		int total = 0, newRow = row, newColumn = column, diff = 0;
		char newChar;
		String newWord = "";
		boolean finished = false, startingLetterUsed = false;
		
		if (dir == Direction.HORIZONTAL) {
			int tempColumn;
			while (newColumn > 1 && board.getLetterAt(row, newColumn-1) != Board.FREE_LOCATION)
				newColumn--;
			diff = column - newColumn;
			tempColumn = newColumn;
			while (tempColumn < Board.MAX_COLUMN && !finished) {
				if ((newChar = board.getLetterAt(row, tempColumn)) == Board.FREE_LOCATION) {
					if (startingLetterUsed)
						finished = true;
					else {
						startingLetterUsed = true;
						newWord += startingLetter;
					}
				}
				else
					newWord += newChar;
				tempColumn++;
			}
		}
		else {
			int tempRow;
			while (newRow > 1 && board.getLetterAt(newRow-1, column) != Board.FREE_LOCATION)
				newRow--;
			diff = row - newRow;
			tempRow = newRow;
			while (tempRow < Board.MAX_ROW && !finished) {
				if ((newChar = board.getLetterAt(tempRow, column)) == Board.FREE_LOCATION) {
					if (startingLetterUsed)
						finished = true;
					else {
						startingLetterUsed = true;
						newWord += startingLetter;
					}
				}
				else
					newWord += newChar;
				tempRow++;
			}
		}
		
		for(int i = 0; i < newWord.length(); i++) {
			if (i==diff)
				total += pool.checkValue(newWord.charAt(i)) * Board.letterMultiplier[row][column];
			else
				total += pool.checkValue(newWord.charAt(i));
			if(dir == Direction.VERTICAL)
				newRow++;
			else
				newColumn++;
		}
		
		total *= Board.wordMultiplier[row][column];

		return total;
	}
}
