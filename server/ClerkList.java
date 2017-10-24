package server;

import java.io.Serializable;
import java.util.ArrayList;

public class ClerkList extends ArrayList<Clerk>implements Serializable{

	Library library;
	
	public ClerkList(Library library){
		this.library=library;
		this.add(new Clerk(library,"Clerk","123456"));
	}
	
	public boolean login(String id,String ps){
		for (int i=0;i<this.size();i++)
			if (this.get(i).login(id,ps))
				return true;
		
		return false;
	}
	
	public boolean addNewClerk(String id,String ps){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getClerkID().equals(id))
				return false;
		this.add(new Clerk(library, id, ps));
		return true;
	}
	
	public void deleteClerk(String id){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getClerkID().equals(id))  remove(this.get(i));
	}	
	
	public Clerk findClerk(String id){
		for (int i=0;i<this.size();i++)
			if (this.get(i).getClerkID().equals(id)) return this.get(i);
		return null;
	}

	
	
}
