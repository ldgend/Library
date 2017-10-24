package cucumber.steps;

import junit.framework.TestCase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import server.*;

public class renew extends TestCase{
	Library library=new Library();
	User u1=new User(library, "u1", "u1");
	User u2=new User(library, "u2", "u2");
	User u3=new User(library, "u3", "u3");
	private String message1;
	private String message2;
	private String message3;
	
	@Given("^a user \"([^\"]*)\" has already borrowed the book with ISBN \"([^\"]*)\"$")
	public void a_user_has_already_borrowed_the_book_with_ISBN(String userid, String isbn) throws Throwable {
	    u1.setID(userid);
	    library.addBook("book1", isbn, 5);
	    u1.borrow(isbn);
	}

	@When("^\"([^\"]*)\" want to renew \"([^\"]*)\"$")
	public void want_to_renew(String userid, String isbn) throws Throwable {
	    message1=u1.renewBook(isbn);
	}
	
	@Then("^\"([^\"]*)\" finish$")
	public void finish(String msg) throws Throwable {
	    assertEquals(msg,message1);
	}

	@Given("^a user \"([^\"]*)\" has already borrowed a book with ISBN \"([^\"]*)\"$")
	public void a_user_has_already_borrowed_a_book_with_ISBN(String userid, String isbn) throws Throwable {
	    u2.setID(isbn);
	}

	@When("^\"([^\"]*)\" has (\\d+)\\$ overdue fee, and he want to renew \"([^\"]*)\"$")
	public void has_$_overdue_fee_and_he_want_to_renew(String userid, int fee, String isbn) throws Throwable {
	    u2.setFee(fee);
	    message2=u2.renewBook(isbn);
	}

	@Then("^cannot renew the book, because \"([^\"]*)\"$")
	public void cannot_renew_the_book_because(String msg) throws Throwable {
	    assertEquals(msg, message2);
	}

	@Given("^a user \"([^\"]*)\" who has already borrowed and renewed \"([^\"]*)\"$")
	public void a_user_who_has_already_borrowed_and_renewed(String userid, String isbn) throws Throwable {
	    u3.setID(userid);
	    library.addBook("book3", isbn, 5);
	    u3.borrow(isbn);
	    u3.renewBook(isbn);
	}

	@When("^\"([^\"]*)\" want to renew \"([^\"]*)\" again$")
	public void want_to_renew_again(String userid, String isbn) throws Throwable {
		message3=u3.renewBook(isbn);
	}

	@Then("^the user cannot renew this book, because \"([^\"]*)\"$")
	public void the_user_cannot_renew_this_book_because(String msg) throws Throwable {
	    assertEquals(msg,message3);
	}
	
}