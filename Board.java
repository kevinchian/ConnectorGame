import java.awt.Color;
import java.util.*;

public class Board {

	public static boolean iAmDebugging = false;
	private ArrayList<ArrayList<Connector>> connectors; //2D ArrayList of connectors. 1st number is p1, 2nd number is p2.
	private Color[][] connectorColors; //2D array of colors. 1st number is p1. 2nd number is p2.
	private static final int LARGEST_P1 = 5;
	private static final int LARGEST_P2 = 6;
	
	// Initialize an empty board with no colored edges.
	public Board ( ) {	
		connectors = new ArrayList<ArrayList<Connector>>(LARGEST_P1 + 1); //creates a 2D array of connectors
		for(int row = 0; row < LARGEST_P1 + 1; row++){ 
			connectors.add(new ArrayList<Connector>(LARGEST_P2 + 1)); //creates a new ArrayList of 7 in each cell, to set size of rows.
			for(int col = 0; col < LARGEST_P2 + 1; col++){
				if(col <= row || row == 0){
					connectors.get(row).add(null); //set the impossible connector values to null
				}
				else{
					connectors.get(row).add(new Connector(row, col)); //generate all possible connectors
				}
			}
		}
		connectorColors = new Color[LARGEST_P1 + 1][LARGEST_P2 + 1];
		for(int row = 1; row < connectorColors.length; row++){
			for(int col = LARGEST_P2; col > row; col--){
				connectorColors[row][col] = Color.WHITE;
			}
		}
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
		connectorColors[cnctr.endPt1()][cnctr.endPt2()] = c; // no need to "add" the connector, since all 
															//connectors representations have been initialized in the constructor.
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors (Color c) {
		return new ColorArrayListIterator(c);
	}
	
	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors ( ) {
		return new ArrayListIterator();
	}
	
	// Create a special iterator meant for the board's 2D ArrayList
	// Only iterates through legal connector pts
	// Uses protected variables because since it is a private class any outside user could not access variables either way.
	// Protected allows ColorArrayListIterator to use the variables as well, which is intended.
	private class ArrayListIterator implements Iterator<Connector>{
		protected int iterRowIndex; //represents p1 of connector (1,2,...5)
		protected int iterColIndex; //represents p2 of connector (2,3,...6)
		
		public ArrayListIterator(){
			iterRowIndex = 1;
			iterColIndex = 1; //start from before the first connector, so next() will work properly
		}
		
		public boolean hasNext(){
			if(iAmDebugging){
				System.out.println("connectors: " + connectors.size());
			}
			if(iterRowIndex >= LARGEST_P1 && iterColIndex >= LARGEST_P2){
				return false;
			}
			return true;
		}
		
		public Connector next(){
			
			increment();
			Connector rtn = connectors.get(iterRowIndex).get(iterColIndex);
			
			if(iAmDebugging){
				System.out.println("p1: " + rtn.endPt1() + "p2 : " + rtn.endPt2());
				System.out.println("Row: " +  iterRowIndex +" " + "Col " + iterColIndex);
			}
			
			return rtn;
		}
		
		public void increment(){
			if(iterColIndex < LARGEST_P2){
				iterColIndex++;
			}
			else{
				iterRowIndex++;
				iterColIndex = iterRowIndex + 1;
			}
		}
		
		public void remove(){ // not used but required method header
		}
	}
	
	// Extended ArrayListIterator to check for certain colors
	// Will iterate through all 15, but only return ones of the specified color.
	private class ColorArrayListIterator extends ArrayListIterator{
		
		private Color c;
		
		public ColorArrayListIterator(Color c){
			super();
			this.c = c;
		}
		
		public Connector next(){
			increment();
			if(connectorColors[iterRowIndex][iterColIndex] == c){
				return connectors.get(iterRowIndex).get(iterColIndex);
			}
			else{
				return this.next();
			}
		}
		
		public boolean hasNext(){	
			int lastConnectorP1 = 0;
			int lastConnectorP2 = 0;
			for(int i = 0; i <= LARGEST_P1; i++){
				for(int j = 0; j <= LARGEST_P2; j++){
					if(connectorColors[i][j] == c){
						lastConnectorP1 = i; // find the endpoints of the last connector of a certain color. 
						lastConnectorP2 = j;
					}
				}
			}
			if(iAmDebugging){
				System.out.println("Row: " + lastConnectorP1 + " Col: " + lastConnectorP2);
				System.out.println("iterRowIndex: " + iterRowIndex + " iterColIndex: " + iterColIndex);
			}
			if(lastConnectorP1 == 0){ //means that there were no connectors found that matched color
				return false;
			}
			if(iterRowIndex < lastConnectorP1){
				return true;
			}
			else{
				return iterColIndex < lastConnectorP2;
			}
		}
	}
		
	
	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		return connectorColors[e.endPt1()][e.endPt2()];
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	// Iterates through all possible pairs of endpoints that share a point with the connector
	public boolean formsTriangle(Connector cnctr, Color c) {
		for(int i = 1; i <= 6; i++){
			if(i != cnctr.endPt1() && i != cnctr.endPt2()){
				if(connectorColors[Math.min(cnctr.endPt1(), i)][Math.max(cnctr.endPt1(),i)] == c){
						if(connectorColors[Math.min(cnctr.endPt2(), i)][Math.max(cnctr.endPt2(), i)] == c){
							return true; // If both endpoint pairs match the color, then a triangle has been created.
						}
				}
			}
		}
		return false;
	}

	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	
	public Connector choice ( ) {
		Iterator<Connector> whiteConnectors = this.connectors(Color.WHITE);
		ArrayList<Connector> goodMoves = new ArrayList<Connector>();  // goodMoves are moves that will not lose the game	
		ArrayList<Connector> badMoves = new ArrayList<Connector>();  // badMoves are moves that will lose the game
		while (whiteConnectors.hasNext()){
			Connector cnctr = whiteConnectors.next();
			if (!this.formsTriangle(cnctr, Color.BLUE)){
				goodMoves.add(cnctr);
				}
			else {
				badMoves.add(cnctr);	
			}
		}
		
		Connector move = null;
		Connector lastResort = null;
		
		// Check the board for mixed triangles
		Iterator<Connector> mixedTriangleIter = goodMoves.iterator();
		while (mixedTriangleIter.hasNext()){
			move = mixedTriangleIter.next();
			if(this.formsMixedTriangle(move)){
				if (this.formsTriangle(move, Color.RED)){
					lastResort = move;
					if(mixedTriangleIter.hasNext()){
						move = mixedTriangleIter.next();
					}
					if(iAmDebugging){
						System.out.println("avoiding red triangle method is called.");
					}
				}
				else{
					return move;	
				}
			}
		}
		
		// If no mixed triangles, pick a line not touching opponent's
		Iterator<Connector> nonadjacentIter = goodMoves.iterator();
		while (nonadjacentIter.hasNext()){
			move = nonadjacentIter.next();
			if(!this.adjacent(move, Color.RED)){
				return move;
			}
		}
		
		// If all are adjacent lines, then pick the mixed triangle that also would have formed a red triangle.
		if(lastResort != null){
			return lastResort;
		}
		
		// If there were no good moves, return a losing move.
		if(goodMoves.size() == 0){
			if(iAmDebugging){
				System.out.println("computer's gonna lose for sure if this code is reached");
			}
			return badMoves.get(0);
		}
		
		// If nothing else is found, then just return a random not losing move
		return goodMoves.get(0); 
	}
	
	// Tests if any adjacent connectors are of a certain color.
	// Returns true if there are adjacent connectors of the color
	private boolean adjacent(Connector cnctr, Color c) {
		Iterator<Connector> coloredConnectors = this.connectors(c);
		while (coloredConnectors.hasNext ( )) {
			Connector check1 = coloredConnectors.next();
			if (cnctr.endPt1()==check1.endPt1() || cnctr.endPt1() == check1.endPt2() || cnctr.endPt2()==check1.endPt1() || cnctr.endPt2()==check1.endPt2())
			{
				return true;
			}
		}
		return false;
	}
	
	// Uses same concept as formsTriangle()
	// Tries to find possible mixed triangle moves. 
	private boolean formsMixedTriangle(Connector cnctr){
		for (int i=1; i<7; i++){
			if (connectorColors[Math.min(cnctr.endPt1(),i)][Math.max(cnctr.endPt1(),i)] == Color.BLUE){		
				if (connectorColors[Math.min(cnctr.endPt2(),i)][Math.max(cnctr.endPt2(),i)]== Color.RED){
					return true;
				}
			}
			if (connectorColors[Math.min(cnctr.endPt1(),i)][Math.max(cnctr.endPt1(),i)] == Color.RED){	
				if (connectorColors[Math.min(cnctr.endPt2(),i)][Math.max(cnctr.endPt2(),i)]== Color.BLUE){
					return true;
				}
			}
		}
		return false;
	}

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	// Each connector in the board is properly initialized so that 
	// 1 <= myPoint1 < myPoint2 <= 6.
	// Checks the following: 15 connectors total. 
	// Red and blue connectors have same count or red has one more depending on the turn
	// All connectors are correctly initialized and all colors correctly assigned
	// There is no losing or winning triangle formed on the board
	// Repeated connectors are impossible, since connectors are all assigned using endpoints to their respective unique cells.
	public boolean isOK() {
		int numberOfBlue = 0;
		int numberOfRed = 0;
		int numberOfWhite = 0;
		for(int i = 0; i < connectorColors.length; i++){
			for(int j = 0; j < connectorColors[i].length; j++){
				if(connectorColors[i][j] == Color.WHITE){
					numberOfWhite++;
				}
				if(connectorColors[i][j] == Color.BLUE){
					numberOfBlue++;
				}
				if(connectorColors[i][j] == Color.RED){
					numberOfRed++;
				}
			}
		}	
		if(iAmDebugging){
			System.out.println("Blue: " + numberOfBlue);
			System.out.println("Red: " + numberOfRed);
			System.out.println("White: " + numberOfWhite);
			System.out.println("Total connectors:" + (numberOfBlue + numberOfRed + numberOfWhite));
		}	
		if(numberOfBlue + numberOfRed + numberOfWhite != 15){
			return false;
		}
		if(iAmDebugging){
			System.out.println("About to test number of red and blue");
		}
		if(numberOfRed != numberOfBlue){
			if(numberOfRed != numberOfBlue + 1){
				return false;
			}
		}
		if(iAmDebugging){
			System.out.println("About to create iterator");
		}
		Iterator<Connector> iter = this.connectors();
		if(iAmDebugging){
			System.out.println("Iterator created");
		}
		while(iter.hasNext()){
			Connector cnctr = iter.next();
			if(cnctr == null){
				return false; // tests if iter returns a "null" (invalid) connector
			}
			Color temp = connectorColors[cnctr.endPt1()][cnctr.endPt2()];
			if(temp == null){
				return false; // tests if connector has invalid endpoints
			}
			if(temp != Color.WHITE){
				if(this.formsTriangle(cnctr, temp)){
					if(iAmDebugging){
						System.out.println("FormsTriangle Error");
					}
					return false;
				}
			}
		}
		return true;
	}
}
	
