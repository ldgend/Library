package test;

import junit.framework.TestCase;
import server.Book;
import server.BookList;
import server.Library;

public class bookListTest extends TestCase{
	Library l=new Library();
	BookList b=new BookList(l);
	Book book=new Book("test","test",0);
	
	public void testAdd(){
		b.add(book);
		assertEquals(false,b.addNewBook("test", "test", 0));
		assertEquals(false,b.addNewBook("12345", "test", 0));
		assertEquals(false,b.addNewBook("123456", "test", 10));
		assertEquals(true,b.addNewBook("123456", "123", 10));
		assertEquals(false,b.addNewBook("1234567", "123", 10));
	}
	
	public void testDelete(){
		b.addNewBook("test", "test", 0);
		b.addNewBook("book", "book", 10);
		assertEquals("test",b.get(0).getTitle());
		b.deleteBook("test");
		assertEquals("book",b.get(0).getTitle());
	}
	
	public void testSetBook(){
		b.add(book);
		assertEquals("test",b.get(0).getTitle());
		assertEquals("test",b.get(0).getISBN());
		assertEquals(0,b.get(0).getCopy());
		b.setBook("booktest", "test", 1);
		assertEquals("booktest",b.get(0).getTitle());
		assertEquals("test",b.get(0).getISBN());
		assertEquals(1,b.get(0).getCopy());
	}
	
	public void testFindBook(){
		b.add(book);
		assertEquals(book,b.findBook("test"));
		assertEquals(book.getISBN(),b.findBook("test").getISBN());
		assertEquals(book.getTitle(),b.findBook("test").getTitle());
		assertEquals(book.getCopy(),b.findBook("test").getCopy());
	}
}