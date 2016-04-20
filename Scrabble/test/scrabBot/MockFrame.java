package scrabBot;

import java.util.ArrayList;
import java.util.Arrays;

public class MockFrame extends Frame {
	public MockFrame(){
		Character[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'T'};
		this.frame = new ArrayList<Character>(Arrays.asList(letters));
	}
}
