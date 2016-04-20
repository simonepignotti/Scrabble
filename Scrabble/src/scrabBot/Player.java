/* Team: Random2
 * Members: Lapo Frati 14202439, Simone Pignotti 14202498, Brennan O'Brien 14209388
 */
package scrabBot;

public class Player {
	protected int playerScore;
	protected String playerName;
	protected Frame frame;
	protected static int numberOfPlayers = 0;
	protected boolean lostChallenge = false;
	
	public Player()
	{
		this.playerScore = 0;
		Player.numberOfPlayers = numberOfPlayers + 1; // Default player name will be PlayerN where N is the number of player created so far
		this.playerName = "Player".concat(Integer.toString(numberOfPlayers));
		this.frame = new Frame();
	}
	
	public void setLostChallenge(){
		lostChallenge = true;
	}
	
	public void resetLostChallenge(){
		lostChallenge = false;
	}
	
	public boolean checkIfLostChallenge(){
		return lostChallenge;
	}
	
	public Player(String name){
		this.playerScore = 0;
		Player.numberOfPlayers = numberOfPlayers + 1; // Default player name will be PlayerN where N is the number of player created so far
		this.playerName = name;
		this.frame = new Frame();
	}
	
	public void resetPlayerData() {
		this.playerScore = 0;
		this.frame = new Frame();
	}
	
	public void setPlayerName(String playerName){
		if(playerName != null)
			this.playerName = playerName;
	}
	
	public String getPlayerName() {
			return this.playerName;
	}
	
	/* If the value is positive it increments the score by that amount, otherwise it throws an exception */
	public void increasePlayerScoreBy(int value)
	{
			this.playerScore = playerScore + value;
	}
	
	public int getPlayerScore() {
			return this.playerScore;
	}
	
	public Frame getPlayerFrame(){
		return frame;
	}
}

