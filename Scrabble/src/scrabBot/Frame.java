/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import java.util.ArrayList;

public class Frame {
	protected ArrayList<Character> frame;
	
	public Frame(){
		this.frame = new ArrayList<Character>();
	}
	
	public Frame(Frame frame){
		this.frame = frame.getLetters();
	}
	
	/* If the specified letter is present into the frame it is removed and the frameSize is decreased, otherwise it raises an exception */
	public boolean removeLetter(Character letter) {
		return frame.remove(letter);
	}

	public boolean containsLetter(Character letter) {
		return frame.contains(letter);		
	}
	
	public boolean removeLetters(String letters){
		for(Character ch : letters.toCharArray()){
			if(frame.remove(ch) == false)
				return false;
		}
		return true;
	}
	
	public boolean containsLetters(String letters){
		@SuppressWarnings("unchecked")
		ArrayList<Character> temp = (ArrayList<Character>)frame.clone();
		for(Character ch : frame){
			if(temp.remove(ch) == false)
				return false;
		}
		return true;
	}
	
	public int getFrameSize(){
		return frame.size();
	}
	
	public boolean isEmpty() {
		return (frame.size() == 0);
	}

	public ArrayList<Character> getLetters() {
		return new ArrayList<Character>(frame);
	}
	
	public void refillFrame( Pool pool) throws EmptyPoolException {
		for( int i = getFrameSize() ; i < 7 ; i++)
			frame.add(pool.drawRandomTile());
	}
	
	public void showFrame() {
		System.out.print("-");
		for( Character letter : frame){
			System.out.print(letter+"-");
		}
		System.out.println();
	}
	
	public String getString(){
		StringBuilder builder = new StringBuilder();
		for (Character value : getLetters()) {
		    builder.append(value);
		}
		return builder.toString();
	}
}

