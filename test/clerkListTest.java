package test;

import junit.framework.TestCase;
import server.ClerkList;
import server.Library;

public class clerkListTest extends TestCase{
		Library l=new Library();
		ClerkList c=new ClerkList(l);
	
	public void testLogin(){
		assertEquals(true,c.login("Clerk", "123456"));
		assertEquals(false,c.login("Clerk", ""));
		assertEquals(false,c.login("", "123456"));
	}
	
	public void testAdd(){
		assertEquals(true,c.addNewClerk("admin", "admin"));
		assertEquals(true,c.login("admin", "admin"));
		assertEquals(false,c.addNewClerk("Clerk", "123456"));
		assertEquals(false,c.addNewClerk("Clerk", ""));
	}
	
	public void testDelete(){
		assertEquals(true,c.addNewClerk("admin", "admin"));
		assertEquals(true,c.login("admin", "admin"));
		c.deleteClerk("admin");
		assertEquals(false,c.login("admin","admin"));
		assertEquals(true,c.addNewClerk("admin", "admin"));
	}
	
	public void testFind(){
		assertEquals(null,c.findClerk("admin"));
		assertEquals("Clerk",c.findClerk("Clerk").getClerkID());
		c.addNewClerk("admin", "admin");
		assertEquals("admin", c.findClerk("admin").getClerkID());
		c.deleteClerk("admin");
		assertEquals(null,c.findClerk("admin"));
	}	
}
