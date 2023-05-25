package backend;

public class Cell {
	private int etat = 0;
	
	public int getCell() {
		return etat;
	}
	
	public void toggleCell(){
		if (etat == 0) {
			etat =1 ;
		}
		else {
			etat = 0;
		}
	
	}
	public void setCell(int val) {
		etat = val;
	}
}
	
