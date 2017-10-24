package server;

import java.io.Serializable;
import java.util.ArrayList;

public class BookList extends ArrayList<Book>implements Serializable{

	public BookList(Library library){
		
	}
	
	public boolean addNewBook(String title,String isbn,int copy){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getISBN().equals(isbn))
				return false;		
		
		this.add(new Book(title,isbn,copy));
		return true;
	}	
	
	public boolean deleteBook(String isbn){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getISBN().equals(isbn)){
				remove(this.get(i));
				return true;
			}
		return false;
	}		
	
	public void setBook(String title,String isbn,int copy){
		for (int i=0;i<this.size();i++){
			if(this.get(i).getISBN().equals(isbn)){
					this.get(i).setTitle(title);
					this.get(i).setCopy(copy);
			}
		}
	}
	
	
	public void clear_attr(String isbn)
	{
		for (int i=0;i<this.size();i++)
			if (this.get(i).getISBN().equals(isbn)) 
				{
				    this.get(i).setBorrowedDate(0);
				    this.get(i).setRenew(false, 0);
				};
	}
	
	public Book findBook(String isbn){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getISBN().equals(isbn)) return this.get(i);
		return null;
	}
	
	public void setBorrowDate(String isbn, long borrowDate){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getISBN().equals(isbn)) this.get(i).setBorrowedDate(borrowDate);
	}
	
	public ArrayList<Book> searchBook(String title,String isbn){
		ArrayList<Book> result=new ArrayList<Book>();
		
		for (int i=0;i<this.size();i++)
			result.add(this.get(i));
		
		if (!title.equals(""))
			for (int i=this.size()-1;i>=0;i--)
				if (!result.get(i).getTitle().equals(title)) result.remove(result.get(i));
		
		if (!isbn.equals(""))
			for (int i=result.size()-1;i>=0;i--)
				if (!result.get(i).getISBN().equals(isbn)) result.remove(result.get(i));
		
		return result;
	}
}
