public class Connector {
	
	// Implement an immutable connector that connects two points on the game board.
	// Invariant: 1 <= myPoint1 < myPoint2 <= 6.
	
	private int myPoint1, myPoint2;
	
	public Connector (int p1, int p2) {
		if (p1 < p2) {
			myPoint1 = p1;
			myPoint2 = p2;
		} else {
			myPoint1 = p2;
			myPoint2 = p1;
		}
	}
	
	public int endPt1 ( ) {
		return myPoint1;
	}
	
	public int endPt2 ( ) {
		return myPoint2;
	}
	
	public boolean equals (Object obj) {
		Connector e = (Connector) obj;
		return (e.myPoint1 == myPoint1 && e.myPoint2 == myPoint2);
	}
	
	public String toString ( ) {
		return "" + myPoint1 + myPoint2;
	}
	
	// Format of a connector is endPoint1 + endPoint2 (+ means string concatenation),
	// possibly surrounded by white space. Each endpoint is a digit between
	// 1 and 6, inclusive; moreover, the endpoints aren't identical.
	// If the contents of the given string is correctly formatted,
	// return the corresponding connector.  Otherwise, throw IllegalFormatException.
	public static Connector toConnector (String s) throws IllegalFormatException {
		
		if (s==null){ 
			throw new IllegalFormatException("No string to check"); // Will never happen with current implementation of driver. May happen elsewhere
		}
		
		s = s.trim();
		
		if (s.length() > 2){
			throw new IllegalFormatException("You cannot have a space between your two integers. Your input: " + s);
		}
		if (s.length() < 2){
			throw new IllegalFormatException("You did not enter in TWO integers. Your input: " + s);
		}
		
		char first = s.charAt(0);
		char second = s.charAt(1);
		
		if (!Character.isDigit(first) || !Character.isDigit(second)){
			throw new IllegalFormatException("Please input integers ONLY. Your input: " + s);
		}
		
		int p1 = first - '0'; //converts to integer
		int p2 = second - '0'; //converts to integer
		
		if(p1 == p2){
			throw new IllegalFormatException("Integers inputted cannot be the same");
		}
		if (p1 < 1 || p1 > 6){
		throw new IllegalFormatException("Not a valid point: " + p1);
		}
		if (p2 < 1 || p2 > 6){
		throw new IllegalFormatException("Not a valid point: " + p2);
		}
		return new Connector (p1, p2); // Doesn't matter if p1 or p2 is greater. Connector constructor checks automatically
	}
}
