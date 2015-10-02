import junit.framework.TestCase;

public class ConnectorTest extends TestCase {
		
	public void testToConnector(){
	
	// All the below try methods should throw error. If it did, assertTrue(true) will return true. 
	// If it continues on and does not catch an exception, then assertFalse(true) will return false.
	// Hence, many "test" variables will be initialized but not used. They are used to run the toConnector method.
		
	// Tests for empty spaces only
		try {
			Connector test = Connector.toConnector("      "); 
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}

	// Tests empty string input.
		
		try {
			Connector test = Connector.toConnector("");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);	
		}
		
	// Test null input
		
		try {
			Connector test = Connector.toConnector(null);
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
		
	// Test string of non integers
		
		try {
			Connector test = Connector.toConnector("ab");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("hi");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
	// Tests of mix of integers and non integers
	
		try {
			Connector test = Connector.toConnector("1a");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("a1");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("2     d");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("23 b");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		
	// Tests invalid negative number strings
	
		try {
			Connector test = Connector.toConnector("-12");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("-34");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
	// Tests for out of range integers, where each integer is not within 1 and 6
	
		try {
			Connector test = Connector.toConnector("98");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("17");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
			
		try {
			Connector test = Connector.toConnector("81");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("29");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("01");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
			
	// Tests for duplicate integers 
	
		try {
			Connector test = Connector.toConnector("11");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("22");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("33");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("44");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("55");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("66");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
	
	// Tests for invalid characters/symbols
		
		try {
			Connector test = Connector.toConnector("%^&#*(@(@&%#&^");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
		try {
			Connector test = Connector.toConnector("#^");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("*1");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("1*");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("15 *");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("* 15");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
	// Tests for wrong number of integers
		
		try {
			Connector test = Connector.toConnector("623");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("1234");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("5");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
	
	// Tests for space in between the integers
		
		try {
			Connector test = Connector.toConnector("1       2");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
		
		try {
			Connector test = Connector.toConnector("3       5");
			assertFalse(true);
		}
		
		catch(IllegalFormatException e){
			assertTrue(true);
		}
	
	// From now on, these should compile and create connectors correctly.
	// The assertFalse statement will be switched and assertTrue will be replaced by assertEquals
	
	// Tests for a normal string input
		
		try {
			Connector test = Connector.toConnector("35");
			assertEquals(test.toString(), "35");
		}
		
		catch(IllegalFormatException e){
			assertFalse(true);
		}
		
	// Tests for spaces only before the integers 
		
		try {
			Connector test = Connector.toConnector("     35");
			assertEquals(test.toString(), "35");
		}
		
		catch(IllegalFormatException e){
			assertFalse(true);
		}

	// Tests for spaces after the integers
		
		try {
			Connector test = Connector.toConnector("23      ");
			assertEquals(test.toString(), "23");
		}
		
		catch(IllegalFormatException e){
			assertFalse(true);
		}
	
	// Tests for spaces before and after integers
		
		try {
			Connector test =  Connector.toConnector(" 45 ");
			assertEquals(test.toString(), "45");
		}
		
		catch(IllegalFormatException e){
			assertFalse(true);
		}

	// Test if reversed order associates with same connector.
		
		try {
			Connector test1 = Connector.toConnector("45");
			Connector test2 = Connector.toConnector("54");
			assertEquals(test1.toString(), "45");
			assertEquals(test2.toString(), "45");
		}
		
		catch(IllegalFormatException e){
			assertFalse(true);
		}
	}
}
