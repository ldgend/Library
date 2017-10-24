package test;

import java.util.Date;
import junit.framework.TestCase;
import server.Book;
import server.Library;
import server.User;

public class userTest extends TestCase {
	Library l=new Library();
	User u=new User(l,"test","test");
	User u2=new User(l,"user","user");
	
	public void testLogin(){
		assertEquals(false,u.login("", ""));
		assertEquals(false,u.login("test", ""));
		assertEquals(false,u.login("", "test"));
		assertEquals(true,u.login("test", "test"));
	}
	
	public void testSetGet(){
		assertEquals(true,u.login("test", "test"));
		u.setPassword("user");
		assertEquals(false,u.login("test", "test"));
		assertEquals(true,u.login("test", "user"));
		u.setID("user");
		assertEquals(false,u.login("test", "test"));
		assertEquals(false,u.login("test", "user"));
		assertEquals(true,u.login("user", "user"));
		assertEquals(0,u.getFee());
		u.setFee(10);
		assertEquals(10,u.getFee());
	}
	
	public void testBorrow(){
		l.addBook("book1", "book1", 0);
		l.addBook("book2", "book2", 1);
		l.addBook("book3", "book3", 2);
		l.addBook("book4", "book4", 3);
		l.addBook("book5", "book5", 4);
		l.addBook("book6", "book6", 5);
		l.addBook("book7", "book7", 6);
		assertEquals(0, u2.getFee());
		u2.setFee(10);
		assertEquals(10, u2.getFee());
		
		assertEquals("Please pay the fee.",u2.borrow("book6"));
		assertEquals("No more books",u.borrow("book1"));
		assertEquals("Not exist",u.borrow("book9"));
		assertEquals("Borrow success!",u.borrow("book6"));
		assertEquals("You already borrowed!",u.borrow("book6"));
		assertEquals("Borrow success!",u.borrow("book5"));
		assertEquals("Borrow success!",u.borrow("book4"));
		assertEquals("Borrow success!",u.borrow("book3"));
		assertEquals("Borrow success!",u.borrow("book2"));
		assertEquals("You cannot borrow more than 5 books",u.borrow("book7"));
	}
	
	public void testRenew(){
		l.addBook("book1", "book1", 0);
		l.addBook("book2", "book2", 1);
		l.addBook("book3", "book3", 2);
		l.addBook("book4", "book4", 3);
		l.addBook("book5", "book5", 4);
		l.addBook("book6", "book6", 5);
		l.addBook("book7", "book7", 6);
		
		assertEquals("Borrow success!",u.borrow("book7"));
		assertEquals("You have never borrowed this book",u.renewBook("book100"));
		assertEquals("Renew success",u.renewBook("book7"));
		assertEquals("You cannot renew twice",u.renewBook("book7"));
		assertEquals("Borrow success!",u.borrow("book6"));
		u.setFee(10);
		assertEquals("Please pay the fee",u.renewBook("book6"));
	}
	
	public void testReturn(){
		l.addBook("book1", "book1", 0);
		l.addBook("book2", "book2", 1);
		l.addBook("book3", "book3", 2);
		l.addBook("book4", "book4", 3);
		l.addBook("book5", "book5", 4);
		l.addBook("book6", "book6", 5);
		l.addBook("book7", "book7", 6);
		Date date=new Date();
		long d=date.getTime();
		
		assertEquals("Borrow success!",u.borrow("book7"));
		assertEquals("Borrow success!",u.borrow("book6"));
		assertEquals("Borrow success!",u.borrow("book5"));
		assertEquals(0,u.getFee());
		assertEquals("Return success!",u.returnBook("book7",d));
		assertEquals(0,u.getFee());
		assertEquals("You have never borrow this book",u.returnBook("book100",d));
		d=d+6*1000*3600*24;
		assertEquals("Return success!",u.returnBook("book6",d));
		assertEquals(1,u.getFee());
		d=d+10*1000*3600*24;
		assertEquals("Return success!",u.returnBook("book5",d));
		assertEquals(11,u.getFee());
	}
}