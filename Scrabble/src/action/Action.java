package action;


public abstract class Action {
	private Choice playerChoice;
	
	public Action(Choice choice){
		playerChoice = choice;
	}
	
	public Choice getChoice(){
		return playerChoice;
	}
}
