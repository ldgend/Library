package cucumber.steps;

import java.util.Date;
import junit.framework.TestCase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import server.*;

public class returnBook extends TestCase {
	Library library=new Library();
	User u1=new User(library,"u1","u1");
	User u2=new User(library,"u2","u2");
	int temp;
	int temp2;
	int fee1;
	int fee2;
	String message1;
	String message2;
	
	@Given("^a user \"([^\"]*)\" borrowed a book \"([^\"]*)\" (\\d+) days ago$")
	public void a_user_borrowed_a_book_days_ago(String userid, String isbn, int days) throws Throwable {
		u1.setID(userid);
		library.addBook("book1", isbn, 5);
		temp=days;
		u1.borrow(isbn);
	}

	@When("^\"([^\"]*)\" return the book of ISBN \"([^\"]*)\"$")
	public void return_the(String userid, String isbn) throws Throwable {
	    Date d=new Date();
	    long date1=d.getTime();
	    long date2=date1+(1000*3600*24*temp);
	    message1=u1.returnBook(isbn, date2);
	}

	@Then("^\"([^\"]*)\" with (\\d+) fee$")
	public void with_fee(String msg, int fee) throws Throwable {
	   assertEquals(fee,u1.getFee());
	   assertEquals(msg, message1);
	}
	
	@Given("^a user \"([^\"]*)\" borrowed book \"([^\"]*)\" (\\d+) days ago$")
	public void a_user_borrowed_book_days_ago(String userid, String isbn, int days) throws Throwable {
		u2.setID(userid);
		library.addBook("book2", isbn, 3);
		temp2=days;
		u2.borrow(isbn);
	}

	@When("^\"([^\"]*)\" return the book with ISBN \"([^\"]*)\"$")
	public void return_the_book_with_ISBN(String userid, String isbn) throws Throwable {
	    Date d=new Date();
	    long date2=d.getTime();
	    long date1=date2+(1000*3600*24*temp2);
	    message2=u2.returnBook(isbn, date1);
	}

	@Then("^\"([^\"]*)\" and the fee is (\\d+)$")
	public void and_the_fee_is(String msg, int fee) throws Throwable {
		assertEquals(fee, u2.getFee());
		assertEquals(msg, message2);
	}
}
