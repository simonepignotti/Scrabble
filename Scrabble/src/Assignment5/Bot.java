/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */

package Assignment5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Bot {

	private Word word = new Word();
	private String letters;
	private LinkedList<Word> legalWords;
	private String inputFileName = "sowpods.txt";
	private GADDAG gad;
	private Player bot;
	private Dictionary dictionary;
	private Board myBoard;
	private boolean noWordsOnBoard = true;
	
	private class GADDAG {
		
		private class dagNode {
			
			private class Element {
				public char letter;
				public boolean valid;
				
				public Element (char letter, boolean valid) {
					this.letter = letter;
					this.valid = valid;
				}
			}
			
			public Element el;
			public ArrayList<dagNode> children;
			
			public dagNode (char letter, boolean valid) {
				el = new Element(letter, valid);
				children = new ArrayList<dagNode>();
			}
			
			public void addChild (char letter, boolean valid) {
				children.add(new dagNode(letter, valid));
			}
			
			public int hasChild (char letter) {
				for (dagNode child : children) {
					if (child.el.letter == letter)
						return children.indexOf(child);
				}
				return -1;
			}
			
			public dagNode getChild(int i) {
				return children.get(i);
			}
			
			public dagNode getLastChild() {
				return children.get(children.size()-1);
			}
			
			public boolean isValid() {
				return el.valid;
			}
			
			public void print (int tabs) {
				System.out.print(el.letter);
				if (el.valid)
					System.out.println("V");
				else
					System.out.println("");
				for (dagNode child : children) {
					for (int i = 0; i < tabs; i++) {
						System.out.print('\t');
					}
					child.print(tabs+1);
				}
			}
			
		}
		
		public dagNode root;
		
		public GADDAG () throws FileNotFoundException {
			
			root = new dagNode('@',false);
			String word = "";
			File inputFile = new File(inputFileName);
			System.out.println("Initializing Bot...");
			Scanner in = new Scanner(inputFile);
			while (in.hasNextLine()) {
				word = in.nextLine();
				if (word.length() < 8)
					add(root, word);
			}
			in.close();
		}
		
		public void add(dagNode node, String word) {
			for (int i = 1; i <= word.length(); i++) {
				String newWord = new StringBuilder(word.substring(0, i)).reverse().toString() + "@" + word.substring(i);
				if (newWord.charAt(newWord.length()-1) == '@')
					newWord = newWord.substring(0, newWord.length()-1);
				recAdd(node, newWord);
			}
		}
		
		private void recAdd(dagNode node, String word) {
			if (word.length() != 0) {
				int index = node.hasChild(word.charAt(0));
				if (index != -1) {
					if(word.length() == 1)
						node.children.get(index).el.valid = true;
					else
						recAdd(node.children.get(index), word.substring(1));
				}
				else {
					node.addChild(word.charAt(0),(word.length() == 1));
					recAdd(node.getLastChild(), word.substring(1));
				}
			}
		}
		
		public void print () {
			root.print(1);
		}
		
		public boolean isFreePrevLoc(Board board, int dir, int row, int column) {
			// the second conditions are not evaluated if the first ones are true
			// -> no bound errors
			if (dir == Word.HORIZONTAL)
				return (column == 0 || board.getSqContents(row, column-1) == Board.EMPTY);
			else
				return (row == 0 || board.getSqContents(row-1, column) == Board.EMPTY);
		}
		
		public boolean isFreeNextLoc(Board board, int dir, int row, int column) {
			// the second conditions are not evaluated if the first ones are true
			// -> no bound errors
			if (dir == Word.HORIZONTAL)
				return (column == Board.SIZE-1 || board.getSqContents(row, column+1) == Board.EMPTY);
			else
				return (row == Board.SIZE-1 || board.getSqContents(row+1, column) == Board.EMPTY);
		}
		
		public void visit (ArrayList<Tile> tiles, Board board, int actualRow, int actualColumn, int dir, dagNode node, String word, boolean goingBackward, int initialRow, int initialColumn, boolean tileUsed) {
			if (actualRow >= 0 && actualColumn >= 0 && actualRow < Board.SIZE && actualColumn < Board.SIZE) {
				char nextLetter = board.getSqContents(actualRow, actualColumn);
				if (nextLetter != Board.EMPTY) {
					int i = node.hasChild(nextLetter);
					if (i != -1) {
						/*
						if (node.getChild(i).isValid() && goingBackward && isFreePrevLoc(board,dir,actualRow,actualColumn)) {
							String newWord = nextLetter + (new StringBuilder(word).reverse().toString());
							if (tileUsed)
								legalWords.add(new Word(actualRow,actualColumn,dir,newWord));
						}
						*/
						if (node.getChild(i).isValid() && !goingBackward && isFreeNextLoc(board,dir,actualRow,actualColumn)) {
							String newWord = "";
							int j = 0;
							while (word.charAt(j) != '@') {
								newWord += word.charAt(j);
								j++;
							}
							newWord = new StringBuilder(newWord).reverse().toString();
							newWord += word.substring(j+1);
							if (tileUsed) {
								if (dir == Word.HORIZONTAL) {
									Word tempWord = new Word(actualRow,initialColumn-j+1,dir,newWord);
									if(board.checkWord(tempWord, bot.getFrame()) == UI.WORD_OK && checkSides(tempWord, initialRow, initialColumn))
										legalWords.add(tempWord);
								}
								else {
									Word tempWord = new Word(initialRow-j+1,actualColumn,dir,newWord);
									if(board.checkWord(tempWord, bot.getFrame()) == UI.WORD_OK && checkSides(tempWord, initialRow, initialColumn))
										legalWords.add(tempWord);
								}
							}
						}
						int newRow = actualRow, newColumn = actualColumn;
						String newWord = word + nextLetter;
						if (dir == Word.HORIZONTAL) {
							if (goingBackward)
								newColumn--;
							else
								newColumn++;
						}
						else {
							if (goingBackward)
								newRow--;
							else
								newRow++;
						}
						visit(tiles,board,newRow,newColumn,dir,node.getChild(i),newWord,goingBackward,initialRow,initialColumn,tileUsed);
					}
				}
				else { // use player's tiles or change direction
					int h = node.hasChild('@');
					if (goingBackward && h != -1) {
						if (dir == Word.HORIZONTAL)
							visit(tiles,board,actualRow,initialColumn+1,dir,node.getChild(h),word+"@",false,initialRow,initialColumn,tileUsed);
						else
							visit(tiles,board,initialRow+1,actualColumn,dir,node.getChild(h),word+"@",false,initialRow,initialColumn,tileUsed);
					}
					ArrayList<Tile> tilesCopy = new ArrayList<Tile>(tiles);
					for (char c = 'A'; c <= 'Z'; c++) {
						boolean isIn = false;
						Tile t = null;
						for (Tile tile : tiles)
							if (tile.matches(c) || tile.isBlank()) {
								isIn = true;
								nextLetter = tile.isBlank() ? Character.toLowerCase(c) : c;
								t = tile;
								break;
							}
						if (isIn) {
							tileUsed = true;
							int i = node.hasChild(nextLetter);
							if (i != -1) {
								/*
								if (node.getChild(i).isValid() && goingBackward && isFreePrevLoc(board,dir,actualRow,actualColumn)) {
									String newWord = nextLetter + (new StringBuilder(word).reverse().toString());
									legalWords.add(new Word(actualRow,actualColumn,dir,newWord));
								}
								*/
								if (node.getChild(i).isValid() && !goingBackward && isFreeNextLoc(board,dir,actualRow,actualColumn)) {
									String newWord = "";
									int j = 0;
									while (word.charAt(j) != '@') {
										newWord += word.charAt(j);
										j++;
									}
									newWord += word.substring(j+1);
									if (dir == Word.HORIZONTAL) {
										Word tempWord = new Word(actualRow,initialColumn-j+1,dir,newWord);
										if(board.checkWord(tempWord, bot.getFrame()) == UI.WORD_OK && checkSides(tempWord, initialRow, initialColumn))
											legalWords.add(tempWord);
									}
									else {
										Word tempWord = new Word(initialRow-j+1,actualColumn,dir,newWord);
										if(board.checkWord(tempWord, bot.getFrame()) == UI.WORD_OK && checkSides(tempWord, initialRow, initialColumn))
											legalWords.add(tempWord);
									}
								}
								int newRow = actualRow, newColumn = actualColumn;
								String newWord = word + nextLetter;
								if (dir == Word.HORIZONTAL) {
									if (goingBackward)
										newColumn--;
									else
										newColumn++;
								}
								else {
									if (goingBackward)
										newRow--;
									else
										newRow++;
								}
								tilesCopy.remove(tiles.indexOf(t));
								visit(new ArrayList<Tile>(tilesCopy),board,newRow,newColumn,dir,node.getChild(i),newWord,goingBackward,initialRow,initialColumn,tileUsed);
								tilesCopy.add(tiles.indexOf(t), t);
							}
						}
					}
				}
			}
		}
	}
	
	public Bot () throws FileNotFoundException {
		gad = new GADDAG();
		legalWords = null;
		word.setWord(0, 0, Word.HORIZONTAL, "HELLO");
		letters = "XYZ";
	}
	
	public int getCommand (Player player, Board board, Dictionary dictionary) {
		// make a decision on the play here
		// use board.getSqContents to check what is on the board
		// use Board.SQ_VALUE to check the multipliers
		// use frame.getAllTiles to check what letters you have
		// return the corresponding commandCode from UI
		// if a play, put the start position and letters into word
		// if an exchange, put the characters into letters
		bot = player;
		this.dictionary = dictionary;
		myBoard = board;
		
		legalWords = new LinkedList<Word>();
		
		for(int i = 0; i<Board.SIZE; i++){
			for(int j = 0; j<Board.SIZE; j++){
				if(board.getSqContents(i, j) != Board.EMPTY){
					noWordsOnBoard = false;
					if( gad.isFreePrevLoc(board, Word.HORIZONTAL, i, j) 
						&& gad.isFreeNextLoc(board, Word.HORIZONTAL, i, j) )
						
						gad.visit(player.getFrame().getAllTiles(), board, i, j, Word.HORIZONTAL, gad.root, "", true, i, j, false);
						
					if( gad.isFreePrevLoc(board, Word.VERTICAL, i, j) 
						&& gad.isFreeNextLoc(board, Word.VERTICAL, i, j) )
						
						gad.visit(player.getFrame().getAllTiles(), board, i, j, Word.VERTICAL, gad.root, "", true, i, j, false);
				}
				if(noWordsOnBoard){
					gad.visit(player.getFrame().getAllTiles(), board, Board.CENTRE, Board.CENTRE, Word.HORIZONTAL, gad.root, "", true, Board.CENTRE, Board.CENTRE, false);
				}
			}
		}
		
		if(legalWords.size() == 0){
			ArrayList<Tile> rack = player.getFrame().getAllTiles();
			letters = "";
			
			int tilesToExchange = Math.round(((float)Math.random()*3)+2);	
			
			for(int i = 0; i<tilesToExchange; i++){
				letters += rack.get(i).getFace();
			}
			
			return UI.COMMAND_EXCHANGE;
		}
		else{
			getHighestValueWord(board, player, dictionary);
			return UI.COMMAND_PLAY;
		}
	}
	
	public Word getWord () {
		// should not change
		return(word);
	}
		
	public String getLetters () {
		// should not change
		return(letters);
	}
	
	public Word monteCarlo (LinkedList<Word> legalWords)
	{
		Word bestWord = new Word();
		Word currentWord = new Word();
		int wordValue, counter, xCord, yCord, currentWordDirection, multiplier, wordMult, letterValue;
		int bestWordValue = 0;
		String currentWordLetters;
		
		for(counter = 0; counter < legalWords.size(); counter++)
		{
		currentWord = legalWords.get(counter);
			
		wordValue = 0;	
		wordMult = 1;
			
		xCord = currentWord.getStartColumn();
		yCord = currentWord.getStartRow();
		currentWordDirection = currentWord.getDirection();
		currentWordLetters = currentWord.getLetters();
		
		for(int letterCounter = 0; letterCounter < currentWordLetters.length(); letterCounter++)
		{
			if(currentWordDirection == 1)
			{
				letterValue = Tile.getValue(currentWordLetters.charAt(letterCounter));
				multiplier = Board.SQ_VALUE[xCord][yCord];
					if(multiplier == 2)
					{
						letterValue = letterValue *2;
					}
					if(multiplier == 3)
					{
						letterValue = letterValue *3;
					}
					if(multiplier == 4)
					{
						wordMult = wordMult * 2;
					}
					if(multiplier == 5)
					{
						wordMult = wordMult * 3;
					}
					wordValue = wordValue + letterValue;
					yCord++;
			}
			if(currentWordDirection == 0)
			{
				letterValue = Tile.getValue(currentWordLetters.charAt(letterCounter));
				multiplier = Board.SQ_VALUE[xCord][yCord];
					if(multiplier == 2)
					{
						letterValue = letterValue *2;
					}
					if(multiplier == 3)
					{
						letterValue = letterValue *3;
					}
					if(multiplier == 4)
					{
						wordMult = wordMult * 2;
					}
					if(multiplier == 5)
					{
						wordMult = wordMult * 3;
					}
					wordValue = wordValue + letterValue;
					xCord++;
			}
		}
		wordValue = wordValue* wordMult;
		
		if(wordValue > bestWordValue)
		{
			wordValue = bestWordValue;	
			bestWord = currentWord;
		}
	}
	return bestWord;
	}
	
	public void getHighestValueWord ( Board board, Player player, Dictionary dictionary)
	{
		int bestValue = 0, currentValue;
		
		Collections.shuffle(legalWords);

		for(Word tempWord : legalWords){
			//currentValue = board.getTotalWordScore(tempWord);
			currentValue = tempWord.getLetters().length();
			if(currentValue > bestValue){
				bestValue = currentValue;
				word = tempWord;
			}
		}
	}
	
	boolean checkSides(Word word, int startRow, int startColumn){
		boolean check = true;
		int row = word.getStartRow();
		int column = word.getStartColumn();
		int dir = word.getDirection();
		int opDir = word.getOppositeDirection();
		ArrayList<String> test = new ArrayList<String>(1);
		test.add(word.getLetters());
		for(int i = 0; i < word.getLength() && check; i++){
			if (row != startRow || column != startColumn)
				check = check && gad.isFreeNextLoc(myBoard, opDir, row, column) && gad.isFreePrevLoc(myBoard, opDir, row, column);
			if(dir == Word.HORIZONTAL)
				column++;
			else
				row++;
		}
		if(dir == Word.HORIZONTAL)
			column--;
		else
			row--;
		check = check && dictionary.areWords(test);
		check = check && gad.isFreePrevLoc(myBoard, dir, word.getStartRow(), word.getStartColumn()) && gad.isFreeNextLoc(myBoard, dir, row, column);
		return check;
	}
	
}
