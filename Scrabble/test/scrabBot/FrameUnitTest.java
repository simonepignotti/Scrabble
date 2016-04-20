/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class FrameUnitTest {
	Frame myTestFrame;
	Frame emptyTestFrame;
	Pool regularTestPool;
	Pool emptyTestPool;
	final int REGULAR_FRAME_SIZE = 7;
	
	@Before
	public void setUp(){
		myTestFrame = new MockFrame(); // MockFrame contains {'A', 'B', 'C', 'D', 'E', 'F', 'T'};
		emptyTestFrame = new Frame();
		regularTestPool = new Pool();
		emptyTestPool = new MockPool();
	}
	
	@Test
	public void testRemoveLetter_TRUE() {
		assertTrue(myTestFrame.removeLetter('A'));
		assertEquals(myTestFrame.getFrameSize(), REGULAR_FRAME_SIZE - 1 );
	}
	
	@Test
	public void testRemoveLetter_FALSE() {
		assertFalse(myTestFrame.removeLetter('H'));
		assertEquals(myTestFrame.getFrameSize(), REGULAR_FRAME_SIZE);	
	}
	
	@Test
	public void testContainsLetter_TRUE() {
		assertTrue(myTestFrame.containsLetter('B'));
	}
	
	@Test
	public void testContainsLetter_FALSE() {
		assertFalse(myTestFrame.containsLetter('H'));
	}

	@Test
	public void testGetFrameSize() {
		assertEquals(myTestFrame.getFrameSize(), REGULAR_FRAME_SIZE);
	}

	@Test
	public void testIsEmptyFrame_TRUE() {
		assertTrue(emptyTestFrame.isEmpty());
	}
	
	@Test
	public void testIsEmptyFrame_FALSE() {
		assertFalse(myTestFrame.isEmpty());
	}

	@Test
	public void testGetLetters() {
		ArrayList<Character> retrievedLetters = myTestFrame.getLetters();
		Character[] expectedLetters = {'A', 'B', 'C', 'D', 'E', 'F', 'T'};
		assertEquals(retrievedLetters, Arrays.asList(expectedLetters));
	}

	@Test
	public void testRefillFrame() {
		emptyTestFrame.refillFrame(regularTestPool);
		assertEquals(emptyTestFrame.getFrameSize(), REGULAR_FRAME_SIZE);
	}
	
	@Test(expected=EmptyPoolException.class)
	public void testRefillFrame_raiseEmptyPoolException(){
		emptyTestFrame.refillFrame(emptyTestPool);
	}
}
