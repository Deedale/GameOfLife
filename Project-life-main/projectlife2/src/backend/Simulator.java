package backend;


import windowInterface.MyInterface;
import java.util.Random;
//TODO : add imports you will need here
/*
 *  Note : if you use an import in another class you will need to add
 *  the import lines on top of the file of the other class.
 */
// Examples of useful imports :
// import java.util.LinkedList;
// import java.util.ArrayList;
// import java.util.Random;

public class Simulator extends Thread {

	private MyInterface mjf;
	private boolean stopFlag;
	private boolean pauseFlag;
	private boolean borderFlag;
	private int loopDelay;
	private Grid grid = new Grid();
	//TODO : add declaration of additional attributes here

	public Simulator(MyInterface mjfParam) {
		mjf = mjfParam;
		stopFlag=false;
		pauseFlag=false;
		loopDelay = 150 ;
		//TODO : add other attribute initialization here

	}
	
	/**
	 * getter of the width of the simulated world
	 * @return the number of columns in the grid composing the simulated world
	 */
	public int getWidth() {
		return grid.getWidth();
	
	}

	/**
	 * getter of the height of the simulated world
	 * @return the number of rows in the grid composing the simulated world
	 */
	public int getHeight() {
		return grid.getHeight();
	}
	
	public void run() {
		//WARNING : Do not modify this.
		/*Exception : 
		 *if everything you have to do works and you have a backup... 
		 *have fun editing this if you want to enhance it!
		 *But be sure to take into account that this class inherits from Thread
		 *You might want to check documentation online about the Thread class
		 *But do not hesitate to email me any questions 
		*/
		int stepCount=0;
		while(!stopFlag) {
			stepCount++;
			makeStep();
			mjf.update(stepCount);
			try {
				Thread.sleep(loopDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while(pauseFlag && !stopFlag) {
				try {
					Thread.sleep(loopDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Individual step of the Simulation, modifying the world from
	 * its state at time t to its state at time t+1
	 */
	public void makeStep() {
		 Grid newgrid = new Grid();
		 for (int x=0;x<getWidth();x++) {
			 for (int y=0;y<getHeight();y++) {
				int count = 0;
				//iterate through each cell around the current (x,y) cell to find those "alive" aka =1
				for (int i=x-1; i<= x+1;i++) {
					 for (int j=y-1; j <= y+1 ;j++) {
						 //leave out the current cell
						 if (x != i && y != j) {
							 //leave out the values that are out of bound
							 if (i>0 && j>0 && i<getWidth() && j<getHeight()) {
								 //If alive count as neighbor
								 if (grid.getCell(i, j) == 1) {
									 count++;
								 }
							  }
						  }
					 }
				 }
				 System.out.println(count);
				 if (grid.getCell(x,y) == 1) {
				 	if(count<2||count>3) {
					 newgrid.setCell(x,y,0);}
					else {
					 newgrid.setCell(x,y,1);
					 } 
				 }
				 else if (count==3) {
					 newgrid.setCell(x,y,1);
				 }
				 else {
					 newgrid.setCell(x,y,0);
				 }
			 } 
		 }
		 grid = newgrid;
		 getFileRepresentation();
	}


	/**
	 * Stops simulation by raising the stop flag used in the run method
	 */
	public void stopSimu() {
		stopFlag=true; //when stop button pressed StopFlag is activated and therefore stops the game
	}

	/**
	 * Toggles Pause of simulation 
	 * by raising or lowering the pause flag used in the run method
	 */
	public void togglePause() {
		if (pauseFlag==true) {
			pauseFlag = false;
	}
		else {
			pauseFlag=true;
			}
	}
	/**
	 * Changes content value of the Cell at the coordinates specified in arguments
	 * 
	 * 
	 * @param x coordinate on the x-axis (horizontal)
	 * @param y coordinate on the y-axis (vertical)
	 */
	public void toggleCell(int x, int y) {
		//TODO : change the value of the cell at coordinates (x,y)
		/*
		 * Note : the value of the cell is NOT a boolean, it is an integer.
		 * O means dead, 1 means alive...
		 * But the GUI can also print properly more values than that.
		 * You might want to use this for the going further section.
		 */
		grid.toggleCell(x,y);
	}
	/**
	 * get the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @return the value of the cell
	 */
	public int getCell(int x, int y) {
		//TODO implement proper return
		return grid.getCell(x,y);
	}

	/**
	 * set the value of a cell at coordinates
	 * @param x coordinate
	 * @param y coordinate
	 * @param val the value to set inside the cell
	 */
	public void setCell(int x, int y, int val) {
		//TODO implement
		grid.setCell(x,y,val);
	}

	/**
	 * Each String in the returned array represents a [row/column]
	 * 
	 * @return an array of Strings representing the simulated world's state
	 */
	
	public String[] getFileRepresentation() {
	    String[] str = new String[grid.getHeight()];
	    
	    // Loop through all rows
	    for (int j = 0; j < grid.getHeight(); j++) {
	        StringBuilder row = new StringBuilder();
	        
	        // Loop through all elements of current row
	        for (int i = 0; i < grid.getWidth(); i++) {
	            row.append(grid.getCell(i, j)).append(" ");
	            System.out.print(grid.getCell(i, j) + " ");
	        }
	        
	        str[j] = row.toString();
	        System.out.println();
	        
	    }
	    System.out.println("\n");
	    return str;
	}

	/**
	 * Populates a row indicated by the given coordinate
	 * using its String representation
	 * 
	 * @param y the y coordinate of the row to populate
	 * @param fileLine the String line representing the row
	 */
	public void populateLine(int coord, String fileLine) {
		//TODO : implement and correct the comment
		// As you have to choose row OR column depending on your implementation
		    
		    // Split the fileLine string into individual cell values
		    String[] cells = fileLine.split(";");
		    
		    // Check if the provided coordinate is within bounds
		    if (coord >= 0 && coord < grid.getHeight()) {
		            // Update the cells in the row/column with the values from fileLine
		            for (int i = 0; i < cells.length; i++) {
		                int cellValue = Integer.parseInt(cells[i]);
		                grid.setCell(i, coord, cellValue);
		            }
		        } 
		    else {
		            System.out.println("Invalid fileLine length.");
		            }
		}
	
	/**
	 * populates world with randomly living cells
	 * 
	 * @param chanceOfLife the probability, expressed between 0 and 1, 
	 * that any given cell will be living
	 */
	public void generateRandom(float chanceOfLife) {
		Random randGenerator = new Random();
		 for (int x=0;x<getWidth(); x++) {
			 for (int y=0;y<getHeight(); y++) {
				float floatVal = randGenerator.nextFloat();  //generate a random float between 0 and 1 (uniform distribution)
				if (floatVal>chanceOfLife) {
					grid.setCell(x,y,0);
				}
				else {
					grid.setCell(x,y,1);
				}
			 }
		 }
	}
	
	/**
	 * Checks if the borders are looping
	 * 
	 * @return true if the borders are looping, false otherwise
	 */
	public boolean isLoopingBorder() {
		if (borderFlag==false){
			return true;
		}
		else {
		return false;
		}
	}
	
	/**
	 * Toggle the looping of borders, activating or deactivating it
	 * depending on the present state
	 */
	public void toggleLoopingBorder() {
		//TODO implement
		if (borderFlag==true) {
			borderFlag = false;
	}
		else {
			borderFlag=true;
			}
	}
	
	/**
	 * Setter for the delay between steps of the simulation
	 * @param delay in milliseconds
	 */
	public void setLoopDelay(int loopdelay) {
		loopDelay=loopdelay;
	}		
}
