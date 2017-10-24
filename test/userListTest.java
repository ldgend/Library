package test;

import junit.framework.TestCase;
import server.Library;
import server.User;
import server.UserList;

public class userListTest extends TestCase{
	Library l=new Library();
	UserList u=new UserList(l);
	
	public void testLogin(){
		assertEquals(false,u.login("test", "test"));
		u.add(new User(l,"test","test"));
		assertEquals(true,u.login("test", "test"));
		assertEquals(false,u.login("test", ""));
		assertEquals(false,u.login("", "test"));
	}
	
	public void testAdd(){
		assertEquals(false,u.login("user", "user"));
		assertEquals(true,u.addUser("user", "user"));
		assertEquals(true,u.login("user", "user"));
		assertEquals(false,u.addUser("user", ""));
	}
	
	public void testDelete(){
		u.add(new User(l,"test","test"));
		u.add(new User(l,"user","user"));
		assertEquals(true,u.login("test", "test"));
		assertEquals(true,u.login("user", "user"));
		u.deleteUser("test");
		assertEquals(false,u.login("test", "test"));
		assertEquals(true,u.login("user", "user"));
	}
	
	public void testFindUser(){
		u.add(new User(l,"test","test"));
		u.add(new User(l,"user","user"));
		assertEquals(true,u.login("test", "test"));
		assertEquals(true,u.login("user", "user"));
		assertEquals("test",u.findUser("test").getID());
		assertEquals("user",u.findUser("user").getID());
	}
	
	public void testSetUser(){
		u.add(new User(l,"test","test"));
		assertEquals(true,u.login("test", "test"));
		assertEquals(0,u.get(0).getFee());
		u.setUser("test", "user", 10);
		assertEquals(10,u.get(0).getFee());
		assertEquals("test",u.get(0).getID());
		assertEquals("user",u.get(0).getPassword());
	}
}