package action;


public class ExchangeLetters extends Action {
	private String lettersToChange;
	
	public ExchangeLetters(String letters) {
		super(Choice.EXCHANGELETTERS);
		lettersToChange = letters;		
	}
	
	public String getLettersToChange() {
		return lettersToChange;
	}
}
