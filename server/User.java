package server;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;


public class User implements Serializable{

    String id;
    String password;
    Library library;
    int borrowCount;
    int fee;
    ArrayList<Book> borrowedBooks;    
	
    public User(Library library,String id,String password){
		
		this.library=library;	
		this.id=id;
	    this.password=password;
	    this.fee=0;
	    borrowedBooks=new ArrayList<Book>();
	}
    
	public boolean login(String id,String password){
		if (id.equals(this.id)&&password.equals(this.password))
			return true;
		return false;
	}
	
	public int getBorrowedCount()
	{
		return borrowedBooks.size();
	}
  	public void setID(String i){
  		id=i;
  	}
  	
  	public String getID(){
  		return id;
  	}
  	
  	public void setPassword(String p){
  		password=p;
  	}
  	
  	public String getPassword(){
  		return password;
  	}
  	
  	public void setFee(int f){
  		fee=f;
  	}
  	
  	public void returnFee(int f)
  	{
  		fee-=f;
  		if(fee<0)
  			fee=0;
  	}
  	public int getFee(){
  		return fee;
  	}
  	
  	
    public String borrow(String isbn){
    	long date;
    	Book book;
		Date borrow=new Date();
		date=borrow.getTime();
    	
		if (fee!=0) return "Please pay the fee.";
		
		if (borrowedBooks.size()==5) return "You cannot borrow more than 5 books";
		
		for (int i=0;i<borrowedBooks.size();i++)
			if (borrowedBooks.get(i).getISBN().equals(isbn))
				return "You already borrowed!";
    	
			book=library.findBook(isbn);

		try{
			
		if (book.getCopy()==0) return "No more books";
		
		}catch(Exception ex){
			return "Not exist";
		}
    	borrowedBooks.add(book);
		library.setBook(book.getTitle(),isbn, book.getCopy()-1);
    	library.setBorrowDate(isbn, date);
		return "Borrow success!";

    }
  	
   
    public String renewBook(String isbn){
    	long date;
    	boolean flag=false;
    	Date borrow=new Date();
		date=borrow.getTime();
    	if (fee>0) return "Please pay the fee";
    	
    	for (int i=0;i<borrowedBooks.size();i++)
    		if (borrowedBooks.get(i).getISBN().equals(isbn)){
    			flag=true;
    			if (borrowedBooks.get(i).getRenew()) return "You cannot renew twice";
    			borrowedBooks.get(i).setRenew(true,date);
    			break;
    		}
    	if (!flag) return "You have never borrowed this book";
    	
    	return "Renew success";
    }
    
  	public ArrayList<Book> getBorrowedBooks(){
  		return borrowedBooks;
  	}
    
  	
  	public String returnBook(String isbn, long d){
  		long date;    	
    	Date borrow=new Date();
		date=borrow.getTime();
  		for (int i=0;i<borrowedBooks.size();i++)
  			if (borrowedBooks.get(i).getISBN().equals(isbn)){
  				
  				fee+=borrowedBooks.get(i).calculateFee(d);
  				
  				library.setBook(borrowedBooks.get(i).getTitle(),isbn, borrowedBooks.get(i).getCopy()+1);
  				borrowedBooks.get(i).setBorrowedDate(0);
  				borrowedBooks.get(i).setRenew(false, 0);
  				borrowedBooks.remove(i);
  				return "Return success!";
  			}
  		
  		return "You have never borrow this book";
  	}
  	

}
