/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

@SuppressWarnings("serial")
public class EmptyPoolException extends RuntimeException{
  public EmptyPoolException(String s){
	super(s);
  }
}