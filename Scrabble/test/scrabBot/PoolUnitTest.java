/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PoolUnitTest {
	private Pool regularTestPool;
	private Pool emptyTestPool;
	final static int POOL_INITIAL_DIMENSION = 100;
	final static int LETTER_F_VALUE = 4;
	final static char NONEXISTING_LETTER = '#';
	
	@Before
	public void setUp(){
		regularTestPool = new Pool();
		emptyTestPool = new MockPool();
		
	}
	
	@Test
	public void testResetPool() {
		emptyTestPool.resetPool();
		assertEquals(emptyTestPool.getPoolSize(), POOL_INITIAL_DIMENSION);
	}

	@Test
	public void testGetPoolSize() {
		assertEquals(regularTestPool.getPoolSize(), POOL_INITIAL_DIMENSION);
	}
	

	@Test
	public void testIsEmpty_TRUE() {
		assertTrue(emptyTestPool.isEmpty());
	}
	
	@Test
	public void testIsEmpty_FALSE() {
		assertFalse(regularTestPool.isEmpty());
	}

	@Test
	public void testDrawRandomTile() {
		regularTestPool.drawRandomTile();
		assertEquals(regularTestPool.getPoolSize(), POOL_INITIAL_DIMENSION - 1);
	}
	
	@Test(expected=EmptyPoolException.class)
	public void testDrawRandomTile_raiseEmptyPoolEx(){
		emptyTestPool.drawRandomTile();
	}

	@Test
	public void testCheckValue() {
		assertEquals(regularTestPool.checkValue('F'),LETTER_F_VALUE);
	}
	
	@Test(expected=WrongLetterException.class)
	public void testCheckValue_raiseWrongLetterEx(){
		regularTestPool.checkValue(NONEXISTING_LETTER);
	}
}
