package server;

import java.io.Serializable;

public class Book implements Serializable{

	String title;
	String isbn;
	int copy;
	long borrowedDate;
	long renewDate;
	boolean renew;                                     

	public Book(String title,String isbn,int copy){
		this.title=title;
		this.isbn=isbn;
		this.copy=copy;
		
		borrowedDate=0;
		renewDate=0;
		renew=false;
	}
	
	public void setTitle(String t){
		title=t;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setISBN(String s){
		isbn=s;
	}	
	
	public String getISBN(){
		return isbn;
	}
	
	public void setRenew(boolean r,long reDate){
		renew=r;
		renewDate=reDate;
	}
	public long getRenewDate()
	{
		return renewDate;
	}
	
	public boolean getRenew(){
		return renew;
	}
	
	public void setCopy(int c){
		copy=c;
	}
	
	public int getCopy(){
		return copy;
	}
	
	public void setBorrowedDate(long b){
		borrowedDate=b;
	}
	
	public long getBorrowedDate(){
		return borrowedDate;
	}
	
	public int calculateFee(long returnDate){
		int date_num=0;
		if(renewDate==0)
		{
			int i=(int) ((returnDate-borrowedDate)/(1000*3600*24));
			if (i>5) 
			{
				date_num+=i-5;
			}
		}
		else
		{
			int i=(int) ((renewDate-borrowedDate)/(1000*3600*24));
			int j=(int) ((returnDate-renewDate)/(1000*3600*24));
			if (i>5) 
			{
				date_num+=i-5;
			}
			if(j>5)
			{
				date_num+=j-5;
			}
		}
		return date_num;
	}
}
