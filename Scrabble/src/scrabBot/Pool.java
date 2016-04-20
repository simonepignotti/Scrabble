/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Pool implements Cloneable{
	protected HashMap<Character,Integer> letterValues;
	protected ArrayList<Character> pool;
	protected final char[] letters = {'&','E','A','I','O','N','R','T','L','S','U','D','G','B','C','M','P','F','H','V','W','Y','K','J','X','Q','Z'};
	protected final int[] values =   { 0 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 2 , 2 , 3 , 3 , 3 , 3 , 4 , 4 , 4 , 4 , 4 , 5 , 8 , 8 , 10, 10};
	private final int[] copies =   { 2 , 12, 9 , 9 , 8 , 6 , 6 , 6 , 4 , 4 , 4 , 4 , 3 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 1 , 1 , 1 , 1 , 1 };
	
	Pool(){
 		letterValues = new HashMap<Character,Integer>();
 		pool = new ArrayList<Character>();
		fillPool();
		shufflePool();
		assignValuesToLetters();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Character> getRemainingLetters(){
		ArrayList<Character> tempPool = (ArrayList<Character>) pool.clone();
		Collections.shuffle(tempPool);
		return tempPool;
			
	}
	
	public void shufflePool(){
		Collections.shuffle(pool);
	}
	
	private void insertLetterIntoPool( Character letter, int copies){
		for(int i = 0; i<copies; i++) pool.add(letter);
	}
	
	protected void assignValuesToLetters(){
		for(int i = 0; i < letters.length; i++)
			letterValues.put(letters[i], values[i]);
	}
	
	private void fillPool(){
		for(int i = 0; i < letters.length; i++)
			insertLetterIntoPool(letters[i], copies[i]);
	}
	
	public void resetPool(){
		pool = new ArrayList<Character>();
		fillPool();
		Collections.shuffle(pool);
	}
	
	public int getPoolSize() { return pool.size(); }
	
	public boolean isEmpty() { return (pool.size() == 0) ? true : false; }
	
	/* If the pool is not empty draws a random tile, otherwise throws an exception */
	public Character drawRandomTile() throws EmptyPoolException {		
		if(! this.isEmpty())
			return pool.remove(pool.size()-1); // returned tile is random because pool has been shuffled
		else
			throw new EmptyPoolException("Pool is empty. Can't draw a new tile.");
	}
	
	/* If the letter is valid returns its value, otherwise throws an exception */
	public int checkValue(Character letter) throws WrongLetterException {
		letter = Character.toUpperCase(letter);
		if(letterValues.containsKey(letter))
			return letterValues.get(letter);
		else
			throw new WrongLetterException("The letter -"+letter+"- does not appear in the game.");
	}
}
