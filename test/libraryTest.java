package test;

import junit.framework.TestCase;
import server.Clerk;
import server.Library;

public class libraryTest extends TestCase{
	Library l=new Library();
	
	public void testClerkList(){
		assertEquals("Clerk",l.getClerkList().get(0).getClerkID());
		l.getClerkList().add(new Clerk(l,"admin","admin"));
		assertEquals("admin",l.getClerkList().get(1).getClerkID());
		assertEquals(true,l.getClerkList().login("admin", "admin"));
		assertEquals(true,l.getClerkList().addNewClerk("admin1", "admin1"));
		assertEquals(false,l.getClerkList().addNewClerk("admin", ""));
		l.getClerkList().deleteClerk("admin");
		assertEquals(false,l.getClerkList().login("admin", "admin"));
		assertEquals("Clerk",l.getClerkList().findClerk("Clerk").getClerkID());
	}
	
	public void testUserList(){
		assertEquals(true,l.getUserList().addUser("user", "user"));
		assertEquals(true,l.getUserList().addUser("user1", "user1"));
		assertEquals(true,l.getUserList().login("user", "user"));
		assertEquals(true,l.getUserList().login("user1", "user1"));
		assertEquals(false,l.getUserList().addUser("user1", ""));
		l.getUserList().deleteUser("user");
		assertEquals(false,l.getUserList().login("user", "user"));
		assertEquals("user1",l.getUserList().findUser("user1").getID());
		assertEquals(0,l.getUserList().get(0).getFee());
		l.getUserList().setUser("user1", "test", 10);
		assertEquals(10,l.getUserList().get(0).getFee());
		assertEquals(false,l.getUserList().login("user1", "user1"));
		assertEquals(true,l.getUserList().login("user1", "test"));
	}

	public void testBook(){
		assertEquals(true,l.addBook("COMP", "TDD", 5));
		assertEquals("TDD",l.findBook("TDD").getISBN());
		assertEquals("COMP",l.findBook("TDD").getTitle());
		assertEquals(false,l.addBook("5104", "TDD", 5));
		l.deleteBook("TDD");
		assertEquals(null,l.findBook("TDD"));
		assertEquals(true,l.addBook("COMP", "TDD", 5));
		l.setBook("COMP5104", "TDD", 10);
		assertEquals("TDD",l.findBook("TDD").getISBN());
		assertEquals("COMP5104",l.findBook("TDD").getTitle());
	}
}