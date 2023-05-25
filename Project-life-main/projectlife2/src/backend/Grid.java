package backend;


public class Grid {
	int tailleX = 10;
	int tailleY = 10;
	Cell[][] grid = new Cell[tailleX][tailleY];
	
	public Grid(){
		for (int x = 0; x < tailleX; x++) {
			for (int y = 0; y < tailleY; y++) {
				grid[x][y] = new Cell() ;
			
			}
		}
	} 
	public int getWidth() {
		return tailleX;
	}
	public int getHeight() {
		return tailleY;
	}
	
	public void toggleCell(int x,int y){
		grid[x][y].toggleCell();
	}
	
	public int getCell(int x, int y) {
		return grid[x][y].getCell();
	}
	
	public void setCell(int x, int y, int val) {
		grid[x][y].setCell(val);
	}

}