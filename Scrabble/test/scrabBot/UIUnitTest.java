/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import org.junit.Test;

import action.Choice;

public class UIUnitTest {
	UI testUI;
	
	@Test
	public void getUserInput_QUIT(){
		byte[] data = "QUIT".getBytes();

        testUI = new UI(new ByteArrayInputStream(data));
        
        assertEquals(Choice.QUIT, testUI.getUserInput().getChoice());
	}
	
	@Test
	public void getUserInput_PASSTURN(){
		byte[] data = "PASS".getBytes();

        testUI = new UI(new ByteArrayInputStream(data));

        assertEquals(Choice.PASSTURN, testUI.getUserInput().getChoice());
	}
	
	@Test
	public void validateInput_PASSTURN_TRUE(){
        testUI = new UI(System.in);
        assertTrue( testUI.validateInput("PASS"));
	}
	
	@Test
	public void validateInput_PASSTURN_FALSE(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("PAS"));
	}
	
	@Test
	public void getUserInput_GETHELP(){
		byte[] data = "HELP".getBytes();

        testUI = new UI(new ByteArrayInputStream(data));

        assertEquals(Choice.GETHELP, testUI.getUserInput().getChoice());
	}
	
	@Test
	public void validateInput_GETHELP_TRUE(){
        testUI = new UI(System.in);
        assertTrue( testUI.validateInput("HELP"));
	}
	
	@Test
	public void validateInput_GETHELP_FALSE(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("HELPP"));
	}
	
	@Test
	public void getUserInput_PLACEWORD(){
		byte[] data = "A1 A HELLO".getBytes();

        testUI = new UI(new ByteArrayInputStream(data));

        assertEquals(Choice.PLAYWORD, testUI.getUserInput().getChoice());
	}
	
	@Test
	public void validateInput_PLACEWORD_TRUE(){
        testUI = new UI(System.in);
        assertTrue( testUI.validateInput("A1 A HELLO"));
	}
	
	@Test
	public void validateInput_PLACEWORD_FALSE_WRONGREF(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("Z13 A HELLO"));
	}
	
	@Test
	public void validateInput_PLACEWORD_FALSE_WRONGDIR(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("A1 P HELLO"));
	}
	
	@Test
	public void validateInput_PLACEWORD_FALSE_WRONGWORD(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("A1 A @@@@"));
	}
	
	@Test
	public void validateInput_PLACEWORD_TRUE_SPACE(){
        testUI = new UI(System.in);
        assertTrue( testUI.validateInput("A1 A HELLO&WORLD"));
	}
	
	@Test
	public void getUserInput_EXCHANGELETTERS(){
		byte[] data = "EXCHANGE ABC".getBytes();

		testUI = new UI(new ByteArrayInputStream(data));

        assertEquals(Choice.EXCHANGELETTERS, testUI.getUserInput().getChoice());
	}
	
	@Test
	public void validateInput_EXCHANGELETTERS_TRUE(){
        testUI = new UI(System.in);
        assertTrue( testUI.validateInput("EXCHANGE ABC"));
	}
	
	@Test
	public void validateInput_EXCHANGELETTERS_FALSE_WRONGCHOICE(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("EXCHANG ABC"));
	}
	
	@Test
	public void validateInput_EXCHANGELETTERS_FALSE_WRONGWORD(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("EXCHANGE @@@@"));
	}
	
	@Test
	public void validateInput_EXCHANGELETTERS_FALSE_WORDTOOLONG(){
        testUI = new UI(System.in);
        assertFalse( testUI.validateInput("EXCHANGE ABCDEFGHIJKLMNOPQRSTUVXYZ"));
	}
}
