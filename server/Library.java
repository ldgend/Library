package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Library implements Serializable{

	BookList books;                
	ClerkList clerks;        
	UserList users;          
	
	public Library(){
		books=new BookList(this);
		clerks=new ClerkList(this);
		users=new UserList(this);
	}
	
	public ClerkList getClerkList(){
		return clerks;
	}
	
	public UserList getUserList(){
		return users;
	}

	public boolean addBook(String title,String isbn,int copy){
		return books.addNewBook(title, isbn, copy);
	}
	
	public void deleteBook(String isbn){
		books.deleteBook(isbn);
	}	
	
	public Book findBook(String isbn){
		try{
		return books.findBook(isbn);
		}catch (Exception ex){
			return null;
		}
	}
	public void clear_attr(String isbn)
	{
		books.clear_attr(isbn);
	}
	
	public void setBook(String title,String isbn,int copy){
		books.setBook(title, isbn, copy);
	}	
	
	public ArrayList<Book> searchBook(String title,String isbn){
		return books.searchBook(title,isbn);
	}
	
	public void setBorrowDate(String isbn, long borrowDate){
		books.setBorrowDate(isbn, borrowDate);
	}
}
