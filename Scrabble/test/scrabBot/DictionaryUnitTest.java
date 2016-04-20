package scrabBot;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DictionaryUnitTest {
	public static Dictionary dict;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dict = new Dictionary();
		if(dict.loadDictionary() == false)
			throw new Exception("Can't load Dictionary");
	}

	@Test
	public void dictionaryCheck_TRUE() {
		assertTrue(dict.dictionaryCheck("cat"));
		assertTrue(dict.dictionaryCheck("CAT"));
	}
	
	@Test
	public void dictionaryCheck_FALSE(){
		assertFalse(dict.dictionaryCheck("£$*"));
		assertFalse(dict.dictionaryCheck("awkefbaefaowebfe"));
		assertFalse(dict.dictionaryCheck("123"));
	}
}
