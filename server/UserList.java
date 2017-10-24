package server;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList extends ArrayList<User> implements Serializable{

    Library library;
	
	public UserList(Library library){
		this.library=library;
	}
	
	public boolean login(String id,String ps){
		for (int i=0;i<this.size();i++)
			if (this.get(i).login(id,ps))
				return true;
		
		return false;
	}
	
	public boolean addUser(String id,String ps){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getID().equals(id))
				return false;
		
		this.add(new User(library, id, ps));
		return true;
	}	
	
	public void deleteUser(String id){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getID().equals(id))  remove(this.get(i));
	}		
	
	public User findUser(String s){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getID().equals(s)) return this.get(i);
		return null;
	}
	
	public void setUser(String id, String ps,int fee){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getID().equals(id)) {
				this.get(i).setPassword(ps);
				this.get(i).setFee(fee);
			}
	}
}
