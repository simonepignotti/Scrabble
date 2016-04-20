/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ FrameUnitTest.class, 
				PlayerUnitTest.class, 
				PoolUnitTest.class , 
				BoardUnitTest.class, 
				UIUnitTest.class, 
				ScrabbleUnitTest.class, 
				DictionaryUnitTest.class})
public class AllTests {

}
