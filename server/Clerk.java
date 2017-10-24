package server;

import java.io.Serializable;

public class Clerk implements Serializable{

	String ClerkID;
	String password;
	Library library;
	
    public Clerk(Library library,String id,String ps){
		
		this.library=library;
		this.ClerkID=id;
	    this.password=ps;
	}
	
	public boolean login(String id,String ps){
		if (id.equals(this.ClerkID)&&ps.equals(this.password))
			return true;
		return false;
	}

	public void setClerkID(String s){
		ClerkID=s;
	}
	
	public String getClerkID(){
		return ClerkID;
	}
	
	public void setPassword(String p){
		password=p;
	}
	
	public String getPassword(){
		return password;
	}
}
