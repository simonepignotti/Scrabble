package scrabBot;

import java.util.ArrayList;
import java.util.HashMap;



public class MockPool extends Pool {
	MockPool(){
 		letterValues = new HashMap<Character,Integer>();
 		pool = new ArrayList<Character>();
		assignValuesToLetters();
	}
}
