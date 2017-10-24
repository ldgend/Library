package cucumber.steps;

import junit.framework.TestCase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import server.*;

public class borrow extends TestCase{
	Library library=new Library();
	private String message;
	private String message1;
	private String message2;
	private String message3;
	private String message4;
	
	private String tempuserid;
	User u=new User(library,"id","test");
	User u2=new User(library,"u2","u2");
	User u3=new User(library, "u3", "u3");
	
	@Given("^a book with the title \"([^\"]*)\" the ISBN \"([^\"]*)\" with the copy (\\d+)$")
	public void a_book_with_the_title_the_ISBN_which_is_no_copy(final String title, final String isbn, final int copy) throws Throwable {
	    library.addBook(title, isbn, copy);
	}

	@When("^user \"([^\"]*)\" borrow the \"([^\"]*)\"$")
	public void user_borrow_the(final String userid, final String isbn) throws Throwable {
		User user=new User(library,userid,"test");
		message=user.borrow(isbn);
	}

	@Then("^the user cannot borrow, because \"([^\"]*)\"$")
	public void the_user_cannot_borrow_because(final String msg) throws Throwable {
		assertEquals(msg,message);
	}
	
	@Given("^a user \"([^\"]*)\" already borrow book with ISBN \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
	public void a_user_already_borrow_and(final String userid, final String book1, final String book2, final String book3, final String book4, final String book5) throws Throwable {
	   library.addBook("book1", book1, 5);
	   library.addBook("book2", book2, 5);
	   library.addBook("book3", book3, 5);
	   library.addBook("book5", book4, 5);
	   library.addBook("book5", book5, 5);
	   tempuserid=userid;
	}

	@When("^\"([^\"]*)\" borrow the book with ISBN \"([^\"]*)\"$")
	public void borrowBook(final String userid, final String book6) throws Throwable {
		User user=new User(library,tempuserid,"test");
		user.borrow("book5");
		user.borrow("book4");
		user.borrow("book3");
		user.borrow("book2");
		user.borrow("book1");
		library.addBook("book6", book6, 5);
		message1=user.borrow(book6);
	}

	@Then("^user cannot borrow, because \"([^\"]*)\"$")
	public void user_cannot_borrow_because(final String msg) throws Throwable {
		assertEquals(msg,message1);
	}
	
	@Given("^a user \"([^\"]*)\" which has (\\d+)\\$ overdue fee$")
	public void a_user_which_has_$_overdue_fee(String userid, int fee) throws Throwable {
		u.setID(userid);
		u.setFee(fee);
	}
	
	@When("^\"([^\"]*)\" borrow book with ISBN \"([^\"]*)\"$")
	public void borrow_book_with_ISBN(final String userid, final String isbn) throws Throwable {
		library.addBook("test", isbn, 5);
		message2=u.borrow(isbn);
		}
	
	@Then("^this user cannot borrow, because \"([^\"]*)\"$")
	public void this_user_cannot_borrow_because(final String msg) throws Throwable {
		assertEquals(msg,message2);
	}
	
	@Given("^a user \"([^\"]*)\" which has borrowed book with ISBN \"([^\"]*)\"$")
	public void a_user_which_has_borrowed_book_with_ISBN(String userid, String isbn) throws Throwable {
	    u2.setID(userid);
	    library.addBook("common", isbn, 5);
	    u2.borrow(isbn);
	}

	@When("^\"([^\"]*)\" borrow \"([^\"]*)\" before he return it$")
	public void borrow_before_he_return_it(String userid, String isbn) throws Throwable {
	    message3=u2.borrow(isbn);
	}

	@Then("^cannot borrow, because \"([^\"]*)\"$")
	public void cannot_borrow_because(String msg) throws Throwable {
	    assertEquals(msg,message3);
	}

	@Given("^a normal user \"([^\"]*)\"$")
	public void a_normal_user(String userid) throws Throwable {
		u3.setID(userid);
	}
	
	@When("^\"([^\"]*)\" borrow a book with ISBN \"([^\"]*)\"$")
	public void borrow_a_book_with_ISBN(String userid, String isbn) throws Throwable {
	    library.addBook("borrow me", isbn, 5);
	    message4=u3.borrow(isbn);
	}
	
	@Then("^\"([^\"]*)\" done$")
	public void done(String msg) throws Throwable {
	    assertEquals(msg,message4);
	}

}
