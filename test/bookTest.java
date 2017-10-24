package test;

import java.util.Date;

import junit.framework.TestCase;
import server.Book;

public class bookTest extends TestCase {
	Book test=new Book("book1","book2",5);
	Date d=new Date();
	long t=d.getTime();
	
	public void testCreate(){
		assertEquals("book1",test.getTitle());
		test.setTitle("book1title");
		assertEquals("book1title",test.getTitle());
		assertEquals("book2",test.getISBN());
		test.setISBN("book1test");
		assertEquals("book1test",test.getISBN());
		assertEquals(5,test.getCopy());
		test.setCopy(10);
		assertEquals(10,test.getCopy());
		assertEquals(false,test.getRenew());
		test.setRenew(true,t);
		assertEquals(true,test.getRenew());
		assertEquals(0,test.getBorrowedDate());
		test.setBorrowedDate(t);
		assertEquals(t,test.getBorrowedDate());
	}
	
	public void testCalculateFee(){
		Date returnDate=new Date();
		long r=returnDate.getTime();
		r=r+5*1000*3600*24;
		
		test.setBorrowedDate(t);
		assertEquals(0,test.calculateFee(r));
		r=r+1*1000*3600*24;
		assertEquals(1,test.calculateFee(r));
		r=r+11*1000*3600*24;
		assertEquals(12,test.calculateFee(r));
	}
	
}