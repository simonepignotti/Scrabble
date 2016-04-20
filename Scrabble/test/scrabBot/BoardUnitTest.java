/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardUnitTest {
	Board regularBoard;
	Board myBoard;
	Player P1;
	
	@Before
	public void setUp(){
		regularBoard = new Board();
		myBoard = new MockBoard();
		P1 = new MockPlayer();
	}

	@Test
	public void testResetBoard() {
		regularBoard.resetBoard();
		for(int i = 1; i <= Board.MAX_ROW; i++)
			for(int j = 1; j<= Board.MAX_COLUMN; j++)
				assertEquals(regularBoard.getLetterAt(i, j), Board.FREE_LOCATION);
	}

	@Test
	public void testCheckPlacement_center() {
		assertEquals(CheckResult.OK ,regularBoard.checkPlacement("CAT", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_FIRST_NOT_CENTRED(){
		assertEquals(CheckResult.FIRST_NOT_CENTRED ,regularBoard.checkPlacement("CAT", Board.CENTER_ROW + 1, Board.CENTER_COLUMN + 1, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_crossing() {
		/* C A T
		 * a
		 * t
		 */
		assertEquals(CheckResult.OK ,regularBoard.checkPlacement("CAT", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_middle() {
		/* B O b c A T 
		 */
		assertEquals(CheckResult.OK ,myBoard.checkPlacement("BC", Board.CENTER_ROW, Board.CENTER_COLUMN - 5, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_corner() {
		/*  c A T
		 *  a
		 *  t
		 */
		assertEquals(CheckResult.OK ,myBoard.checkPlacement("CAT", Board.CENTER_ROW, Board.CENTER_COLUMN - 4, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_parallel() {
		/* C A T
		 * c a t
		 */
		assertEquals(CheckResult.OK ,myBoard.checkPlacement("CAT", Board.CENTER_ROW + 1, Board.CENTER_COLUMN, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_perpendicular() {
		/*   a
		 * C A T
		 */
		assertEquals(CheckResult.OK ,myBoard.checkPlacement("AA", Board.CENTER_ROW - 1, Board.CENTER_COLUMN + 1, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_LACK_NECESSARY_LETTERS(){
		assertEquals(CheckResult.LACK_NECESSARY_LETTERS ,myBoard.checkPlacement("CAZ", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_OUT_OF_BOUNDS_UP(){
		assertEquals(CheckResult.OUT_OF_BOUNDS ,myBoard.checkPlacement("CAT", 0 , Board.CENTER_COLUMN, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_OUT_OF_BOUNDS_DOWN(){
		assertEquals(CheckResult.OUT_OF_BOUNDS ,myBoard.checkPlacement("CAT", Board.MAX_ROW - 1 , Board.CENTER_COLUMN, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_OUT_OF_BOUNDS_LEFT(){
		assertEquals(CheckResult.OUT_OF_BOUNDS ,myBoard.checkPlacement("CAT", Board.CENTER_ROW , 0, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_OUT_OF_BOUNDS_RIGHT(){
		assertEquals(CheckResult.OUT_OF_BOUNDS ,myBoard.checkPlacement("CAT", Board.CENTER_ROW , Board.MAX_COLUMN - 1, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_CONFLICT(){
		assertEquals(CheckResult.CONFLICT ,myBoard.checkPlacement("DAE", Board.CENTER_ROW , Board.CENTER_COLUMN, Direction.VERTICAL, P1));
	}
	
	@Test
	public void testCheckPlacement_NO_LETTER_USED_emptyWord(){
		assertEquals(CheckResult.NO_LETTER_USED ,myBoard.checkPlacement("", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_NO_LETTER_USED_duplicateWord(){
		assertEquals(CheckResult.NO_LETTER_USED ,myBoard.checkPlacement("CAT", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.HORIZONTAL, P1));
	}
	
	@Test
	public void testCheckPlacement_NOT_CONNECTED(){
		assertEquals(CheckResult.NOT_CONNECTED ,myBoard.checkPlacement("CAT", Board.CENTER_ROW + 4, Board.CENTER_COLUMN, Direction.HORIZONTAL, P1));
	}

	@Test
	public void testPlaceWord() {
		regularBoard.placeWord("YO", Board.CENTER_ROW, Board.CENTER_COLUMN, Direction.HORIZONTAL);
		assertEquals(regularBoard.getLetterAt(Board.CENTER_ROW, Board.CENTER_COLUMN), 'Y');
		assertEquals(regularBoard.getLetterAt(Board.CENTER_ROW, Board.CENTER_COLUMN + 1), 'O');
	}
}
