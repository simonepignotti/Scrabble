package Assignment5;

public class GridRef {

	private int row, column;
	
	GridRef (int rowIndex, int columnIndex) {
		row = rowIndex;
		column = columnIndex;
		return;
	}
	
	public int getRow () {
		return(row);
	}
	
	public int getColumn () {
		return(column);
	}
}
