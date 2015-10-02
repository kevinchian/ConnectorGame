import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
	
	// Check empty board.
	public void testEmptyBoard ( ) {
		Board b = new Board ( );
		assertTrue (b.isOK ( ));
		checkCollection (b, 0, 0); // applies more tests
		assertTrue (!b.formsTriangle (new Connector (1, 2), Color.RED));
	}
	
	
	// Checks that the color of connectors return properly
	public void testColorOf(){
		Board b = new Board();
		Connector cnctr1 = new Connector(3,4);
		Connector cnctr2 = new Connector(5,6);
		Connector cnctr3 = new Connector(2,3);
		b.add(cnctr1, Color.RED);
		b.add(cnctr2, Color.BLUE);
		assertEquals(b.colorOf(cnctr1), Color.RED);
		assertEquals(b.colorOf(cnctr2), Color.BLUE);
		assertEquals(b.colorOf(cnctr3), Color.WHITE);
		assertTrue(b.isOK());
	}
	
	// Tests if iterator functions all work properly
	
	public void testIterator(){
		Board b = new Board();
		Iterator<Connector> iter = b.connectors();
		for(int i = 0; i < 15; i++){
			assertTrue(iter.hasNext()); //Checks if there are actually 15 connectors
			Connector cnctr = iter.next();
			assertEquals(b.colorOf(cnctr), Color.WHITE);
		}
		assertFalse(iter.hasNext()); //should have no more connectors to iterate
		assertTrue(b.isOK());
		
		b.add(new Connector(3,4), Color.RED);
		Iterator<Connector> iter2 = b.connectors();
		for(int i = 0; i < 15; i++){
			assertTrue(iter2.hasNext()); 
			Connector cnctr = iter2.next();
			if(cnctr.endPt1() == 3 && cnctr.endPt2() == 4){
				assertEquals(b.colorOf(cnctr), Color.RED); //checks if the iterator correctly adjusts to new added connectors.
			}
			else{
				assertEquals(b.colorOf(cnctr), Color.WHITE);
			}
		}
		assertFalse(iter.hasNext()); //should have no more connectors to iterate
		assertTrue(b.isOK());
	}
	
	// Tests if color iterator functions all work properly
	
	public void testColorIterator(){
		Board b = new Board();
		b.add(new Connector(1,2), Color.RED);
		b.add(new Connector(3,4), Color.BLUE);
		b.add(new Connector(5,6), Color.RED);
		b.add(new Connector(3,6), Color.BLUE);
		assertTrue(b.isOK());
		Iterator<Connector> iterRed = b.connectors(Color.RED);
		Iterator<Connector> iterWhite = b.connectors(Color.WHITE);
		Iterator<Connector> iterBlue = b.connectors(Color.BLUE);

		while(iterRed.hasNext()){
			Connector cnctr = iterRed.next();
			assertEquals(b.colorOf(cnctr), Color.RED);
			System.out.println("Red: " + cnctr.toString());
		}
		System.out.println("Red connectors supposed to be: 12, 56");
		assertTrue(b.isOK());
		
		while(iterBlue.hasNext()){
			Connector cnctr = iterBlue.next();
			assertEquals(b.colorOf(cnctr), Color.BLUE);
			System.out.println("Blue: " + cnctr.toString());
		}
		System.out.println("Blue connectors supposed to be: 34 ,36");
		assertTrue(b.isOK());
		
		while(iterWhite.hasNext()){
			Connector cnctr = iterWhite.next();
			assertEquals(b.colorOf(cnctr), Color.WHITE);
			System.out.println("White: " + cnctr.toString());
		}
		System.out.println("White connectors NOT supposed to be: 12, 34, 36, 56");
		assertTrue(b.isOK());
	}
	
	// Tests that connectors are added correctly
	public void testAdd(){
		Board b = new Board();
		assertEquals(b.colorOf(new Connector(1,2)), Color.WHITE);
		b.add(new Connector(2,1), Color.RED);
		b.add(new Connector(1,4), Color.BLUE);
		assertTrue(b.isOK());
		
		//Added after iterator was implemented
		Iterator<Connector> iter = b.connectors();
		assertTrue(iter.hasNext());
		Connector cnctr = iter.next();
		assertEquals(b.colorOf(cnctr), Color.RED);
		cnctr = iter.next();
		assertEquals(b.colorOf(cnctr), Color.WHITE);
		cnctr = iter.next();
		assertEquals(b.colorOf(cnctr), Color.BLUE);
		assertTrue(b.isOK());
	}
	
	// Check if triangle is formed
	public void testFormsTriangle(){
		Board b = new Board();
		b.add(new Connector(1,2), Color.RED); //Add red then blue repeatedly to simulate real game. Makes sure isOK() won't return false.
		b.add(new Connector(3,2), Color.BLUE);
		b.add(new Connector(2,5), Color.RED);
		b.add(new Connector(4,6), Color.BLUE);
		assertFalse(b.formsTriangle(new Connector(5, 6), Color.RED)); //checks if method doesn't always return true
		assertFalse(b.formsTriangle(new Connector(1, 3), Color.RED)); //checks that method distinguishes between different colored connectors.
		assertTrue(b.formsTriangle(new Connector(1,5), Color.RED)); // checks if method works as intended;
		b.isOK();
	}
	
	// Check one-connector board.
	// Incorporates all above test methods
	public void test1Connector ( ) {
		Board b = new Board ( );
		b.add (new Connector (1, 2), Color.RED);
		assertTrue (b.isOK ( ));
		checkCollection (b, 1, 0);
		
		Iterator<Connector> iter = b.connectors (Color.RED);
		System.out.println(iter.hasNext());
		assertTrue (iter.hasNext ( ));
		Connector cnctr = iter.next ( );
		System.out.println(cnctr.toString());
		assertEquals (b.colorOf (cnctr), Color.RED);
		assertEquals (new Connector (1, 2), cnctr);
		System.out.println(iter.hasNext());
		assertTrue (!iter.hasNext ( ));
		
		assertTrue (!b.formsTriangle (new Connector(1,3), Color.RED));
		assertTrue (!b.formsTriangle (new Connector(5,6), Color.RED));
		assertTrue (!b.choice ( ).equals (new Connector (1, 2)));
		assertEquals (b.colorOf (b.choice ( )), Color.WHITE);
	}
	
	// (a useful helper method)
	// Make the following checks on a board that should be legal:
	//	Check connector counts (# reds + # blues + # uncolored should be 15.
	//	Check red vs. blue counts.
	//	Check for duplicate connectors.
	//	Check for a blue triangle, which shouldn't exist.
	private void checkCollection (Board b, int redCount, int blueCount) {
		// Fill this in if you'd like to use this method.
		// These checks have all been checked by the isOk method already.
	}
}
