package test;

import junit.framework.TestCase;
import server.Clerk;
import server.Library;

public class clerkTest extends TestCase {
	
		Library l=new Library();
		Clerk clerk=new Clerk(l,"Clerk","Clerk");
		String id="test";
		String ps="test";
		
	
	public void testSetGet() {
		assertEquals(true,clerk.login("Clerk", "Clerk"));
		assertEquals(false,clerk.login("Clerk", ""));
		assertEquals(false,clerk.login("", "Clerk"));
		assertEquals("Clerk",clerk.getPassword());
		clerk.setPassword(ps);
		assertEquals("test",clerk.getPassword());
		assertEquals(false,clerk.login("Clerk", "Clerk"));
		assertEquals(false,clerk.login("Clerk", ""));
		assertEquals(false,clerk.login("", "Clerk"));
		assertEquals(true,clerk.login("Clerk", "test"));
		assertEquals("Clerk",clerk.getClerkID());
		clerk.setClerkID(id);
		assertEquals("test",clerk.getClerkID());
		assertEquals(false,clerk.login("Clerk", "Clerk"));
		assertEquals(false,clerk.login("Clerk", ""));
		assertEquals(false,clerk.login("", "Clerk"));
		assertEquals(false,clerk.login("Clerk", "test"));
		assertEquals(true,clerk.login("test", "test"));
	}
	
}