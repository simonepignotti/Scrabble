package scrabBot;

public class MockPlayer extends Player {
	public MockPlayer()
	{
		this.playerScore = 0;
		Player.numberOfPlayers = numberOfPlayers + 1; // Default player name will be PlayerN where N is the number of player created so far
		this.playerName = "Player".concat(Integer.toString(numberOfPlayers));
		this.frame = new MockFrame();
	}
}
