package server;

import java.awt.BorderLayout;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.LoaderHandler;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LibServer extends JFrame {

	public static final String SPLIT_STRING="@/@/";
	public static final String CLOSE_INFO="x~@";
	public static final String END_INFO="@$@";
	public static final String LISTBOOK="0",SEARCHBOOK="1", LISTADMIN="2",ADDADMIN="3",DELETEADMIN="4",ALTERADMIN="5";
	public static final String LISTUSER="6",ADDUSER="7",DELETEUSER="8",ALTERUSER="9";
	public static final String ADDBOOK="10",DELETEBOOK="11",ALTERBOOK="12";
	public static final String BORROW="13",BORROWED="14",RENEW="15",RETURN="16",NEWINFO="17",GETFEE="19",RETURNFEE="20";
	
	JPanel panel;
	JTextArea info;
	JButton startButton,quitButton;
	boolean started;
	JPanel buttonPanel;
	JScrollPane jsp;
	JScrollBar jsb;
	
	Library library;
	Thread forClient;
	
	ServerSocket serverSocket;
	

    public LibServer(){
        int x,y;
		
		x=Toolkit.getDefaultToolkit().getScreenSize().width;
		y=Toolkit.getDefaultToolkit().getScreenSize().height;
    	this.setBounds((x-300)/2, (y-200)/2, 300, 500);
    	panel=new JPanel();
    	panel.setLayout(new BorderLayout());
    	this.setContentPane(panel);
    	info=new JTextArea();
    	info.setEditable(false);
    	
    	
    	jsp=new JScrollPane(info);
    	jsb=jsp.getVerticalScrollBar();
    	panel.add(jsp);
    	startButton=new JButton("Start");
    	startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!started){
					if (!start()) return;
					info.append("Server is waiting...\n");
					jsb.setValue(jsb.getMaximum());
					startButton.setText("Stop");
					started=true;
					waitClient();
				}
				else{
					stop();
					info.append("Sever is closed...\n");
					jsb.setValue(jsb.getMaximum());
					startButton.setText("Start");
					started=false;
				}
			}
		});
    	quitButton=new JButton("Exit");
    	quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				System.exit(0);
			}
		});
    	buttonPanel=new JPanel();
    	buttonPanel.add(startButton);
    	buttonPanel.add(quitButton);
    	panel.add(buttonPanel,"South");
    	this.setVisible(true);
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setResizable(false);
    	this.setTitle("Library System Server");
    	
    	load();
    }
	
	public static void main(String[] args) {
		SplashScreen splashScreen=SplashScreen.getSplashScreen();
		if (splashScreen!=null){
			try{
				Thread.sleep(3000);
				splashScreen.close();
			}catch (Exception e) {
			}
		}
		
		LibServer libServer=new LibServer();
	}
	
	public void load(){
		ObjectInputStream ois;
		
		try{
			ois=new ObjectInputStream(new FileInputStream("data"));
			library=(Library)ois.readObject();
		}catch (Exception e) {
			library=new Library();
		}
	}
	
	public static String readTxtFile(String filePath){
       
                String res="";
                File file=new File(filePath);
                InputStreamReader read=null;
                if(file.isFile() && file.exists()){
                   
					try {
						read = new InputStreamReader(
						new FileInputStream(file));
					} catch (FileNotFoundException e) {
						
						e.printStackTrace();
					}
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    try {
						while((lineTxt = bufferedReader.readLine()) != null){
							res=res+lineTxt;
						}
					} catch (IOException e) {

						e.printStackTrace();
					}
                    try {
						read.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
                   
                    }
                return res;
	}


	public void save(){
		ObjectOutputStream oos;
		File file;
		
		try{
			file=new File("data");
			if (!file.exists()) file.createNewFile();
			oos=new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(library);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}	
		
		
	    
	    String aa = info.getText();
	    FileWriter fw = null;
	    BufferedWriter br = null;
	    try {
	        fw = new FileWriter("log");	        
	        br = new BufferedWriter(fw);
	        br.write(aa);	        
	        br.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    finally{
	        try {
	            br.close();
	            fw.close();
	        } catch (IOException e) {
	            br = null;
	            fw = null;
	        }
	    }
	    
	}
	
	public boolean start(){
		try{
			serverSocket=new ServerSocket(5555);
			return true;
		}catch(Exception ex){
			info.append("Sever error\n");
			jsb.setValue(jsb.getMaximum());
			return false;
		}	
	}
	
	public void stop(){
		try{
			serverSocket.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	
	class Handler implements Runnable{
	    private Socket socket;
	    public Handler(Socket socket){
	        this.socket=socket;
	    }
	    
	    public void run(){
	        try{
	        	String account,password;
				String[] strings=null;
				boolean flag;
				User nowBorrower;
				Clerk nowAdmin;
				PrintWriter writer;
				BufferedReader reader;
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer=new PrintWriter(socket.getOutputStream());
				info.append("A client is connecting\n");
				jsb.setValue(jsb.getMaximum());
				int id;
				strings=reader.readLine().split(SPLIT_STRING);
				
				account=strings[0];
				password=strings[1];
				if (strings[2].equals("1")){
					flag=library.getClerkList().login(account, password);	
				    id=1;
				}
				else{
	                flag=library.getUserList().login(account, password);
	                id=2;
				}
				
				if (!flag){
					writer.println("0");
					writer.flush();
					return;
				}
				else{
					writer.println("1");
					writer.flush();
				}		
				
			
				if (id==1){
					nowAdmin=library.getClerkList().findClerk(account);
					nowBorrower=null;
				}
				else{
					nowAdmin=null;
					nowBorrower=library.getUserList().findUser(account);
				}
				
				info.append("Client operation\n");
				jsb.setValue(jsb.getMaximum());
						String message;
						
						try{
							while((message=reader.readLine())!=null){
								
								if (message.equals(CLOSE_INFO)) throw new Exception();
																	
								String[] temp=message.split(SPLIT_STRING,10);

								if (temp[0].equals(LISTBOOK)){
									
									String string;
									ArrayList<Book> books=library.searchBook("", "");									
									
									for (int i=0;i<books.size();i++){
										string="";
										string=string+books.get(i).getISBN()+SPLIT_STRING;
										string=string+books.get(i).getTitle()+SPLIT_STRING;
										string=string+books.get(i).getCopy();
										
										writer.println(string);
										writer.flush();
									}
									writer.println(END_INFO);
									writer.flush();
									continue;
								}
								

								if (temp[0].equals(LISTADMIN)){
									ArrayList<Clerk> admins=library.getClerkList();
									String string;
									
									for (int i=0;i<admins.size();i++){
										string="";
										string=string+admins.get(i).getClerkID()+SPLIT_STRING;												
										string=string+admins.get(i).getPassword();
										
										writer.println(string);
										writer.flush();
									}
									writer.println(END_INFO);
									writer.flush();
									continue;
								}
								

								if (temp[0].equals(ADDADMIN)){
									boolean tt;
									tt=library.getClerkList().addNewClerk(temp[1],temp[2]);
									if (flag){
										writer.println("1");
										writer.flush();
									}
									else{
										writer.println("0");
										writer.flush();
									}
									continue;
								}
								

								if (temp[0].equals(DELETEADMIN)){
									Clerk admin;
									admin=library.getClerkList().findClerk(temp[1]);
									if (admin==null){
										writer.println("0");
										writer.flush();
									}
									else{
										library.getClerkList().deleteClerk(temp[1]);
										writer.println("1");
										writer.flush();
									}
									continue;
								}
								

								if (temp[0].equals(ALTERADMIN)){
									Clerk admin;
									
									admin=library.getClerkList().findClerk(temp[1]);											
									if (admin==null){
										writer.println("0");
										writer.flush();
									}
									else{
										
										if (temp[2].length()>0) admin.setPassword(temp[2]);
										
										writer.println("1");
										writer.flush();
									}
									continue;
								}
								

                                if (temp[0].equals(LISTUSER)){
                                	ArrayList<User> borrowers=library.getUserList();
									String string;
									
									for (int i=0;i<borrowers.size();i++){
										string="";
										string=string+borrowers.get(i).getID()+SPLIT_STRING;
										string=string+borrowers.get(i).getPassword()+SPLIT_STRING;											
										string=string+borrowers.get(i).getBorrowedCount()+SPLIT_STRING;
										string=string+borrowers.get(i).getFee()+SPLIT_STRING;
										writer.println(string);
										writer.flush();
									}
									writer.println(END_INFO);
									writer.flush();
									continue;
								}
                                

								if (temp[0].equals(ADDUSER)){
									boolean tt;
								
									
									tt=library.getUserList().addUser(temp[1],temp[2]);
									if (flag){
										writer.println("1");
										writer.flush();
									}
									else{
										writer.println("0");
										writer.flush();
									}
									continue;
								}
								

								if (temp[0].equals(DELETEUSER)){
									User borrower;
									borrower=library.getUserList().findUser(temp[1]);
									if (borrower==null){
										writer.println("0");
										writer.flush();
									}
									else{
										library.getUserList().deleteUser(temp[1]);
										writer.println("1");
										writer.flush();
									}
									continue;
								}
								

								if (temp[0].equals(ALTERUSER)){
									User borrower;
									
									borrower=library.getUserList().findUser(temp[1]);
									if (borrower==null){
										writer.println("0");
										writer.flush();
									}
									else{												
										if (temp[2].length()>0) borrower.setPassword(temp[2]);
										
										
										writer.println("1");
										writer.flush();
									}
									continue;
								}


                                if (temp[0].equals(SEARCHBOOK)){
									
									String string;
									ArrayList<Book> books=library.searchBook(temp[2],temp[1]);									
									
									for (int i=0;i<books.size();i++){
										string="";
										string=string+books.get(i).getISBN()+SPLIT_STRING;
										string=string+books.get(i).getTitle()+SPLIT_STRING;
										string=string+books.get(i).getCopy();
										writer.println(string);
										writer.flush();
									}
									writer.println(END_INFO);
									writer.flush();
									continue;
								}
                                
 
                                if (temp[0].equals(ADDBOOK)){
                                	boolean tt;               	
                                	
                                	tt=library.addBook(temp[1], temp[2], Integer.parseInt(temp[3]));
                                	if (tt){
										writer.println("1");
										writer.flush();
									}
									else{
										writer.println("0");
										writer.flush();
									}
									continue;
                                }
                                

                                if (temp[0].equals(DELETEBOOK)){
                                	Book book=null;
                                	ArrayList<Book> books;
                                	books=library.searchBook("",temp[1]);
                                	if (books.size()>0) book=books.get(0);
                                	
                                	if (book==null){
                                		writer.println("0");
										writer.flush();
                                	}
                                	else{
                                		library.deleteBook(book.getISBN());
                                		writer.println("1");
										writer.flush();
                                	}    
                                	continue;
                                }		
                                

                                if (temp[0].equals(ALTERBOOK)){
                                	Book book=null;
                                	ArrayList<Book> books;
                                	books=library.searchBook("",temp[1]);
                                	if (books.size()>0) book=books.get(0);
                                	
                                	if (book==null){
                                		writer.println("0");
										writer.flush();
                                	}
                                	else{
                                		if (temp[2].length()>0) book.setTitle(temp[2]);
                                		if (temp[3].length()>0) book.setCopy(Integer.parseInt(temp[3]));
                                	
                                		writer.println("1");
										writer.flush();
                                	}  
                                	continue;
                                }
                                

                                if (temp[0].equals(BORROW)){
                                	
                                	String str=nowBorrower.borrow( temp[1]);
                                	writer.println(str);
                            		writer.flush();
                            		continue; 
                                }
                                
                               
								if (temp[0].equals(RENEW)){
									String info=null;       									
									info=nowBorrower.renewBook(temp[1]);
									writer.println(info);
									writer.flush();
									continue;
								}
								
								if (temp[0].equals(RETURN)){
									String info=null;    
									long date;    	
							    	Date borrow=new Date();
									date=borrow.getTime();
									info=nowBorrower.returnBook(temp[1],date);
									
									writer.println(info);
									writer.flush();
									continue;
								}
								
								if (temp[0].equals(BORROWED)){
                                    ArrayList<Book> books;       
                                    String string;
                                    
                                	books=nowBorrower.getBorrowedBooks();
                                	for (int i=0;i<books.size();i++){
                                		long date;    	
    							    	Date borrow=new Date();
    									date=borrow.getTime();
										string="";
										string=string+books.get(i).getISBN()+SPLIT_STRING;
										string=string+books.get(i).getTitle()+SPLIT_STRING;
										string=string+books.get(i).getBorrowedDate()+SPLIT_STRING;
										string=string+books.get(i).getRenewDate()+SPLIT_STRING;
																					
										writer.println(string);
										writer.flush();
									}
									writer.println(END_INFO);
									writer.flush();
									continue;
								}
								
								if (temp[0].equals(GETFEE)){
                                    
									User borrower;
									int fee=0;
									borrower=library.getUserList().findUser(temp[1]);
									if (borrower!=null){
										  fee= borrower.getFee();
									}                                  																				
									writer.println(String.valueOf(fee));
									writer.flush();								
									continue;
								}
								
								if (temp[0].equals(RETURNFEE)){                                   
	                                   
									User borrower;
									
									borrower=library.getUserList().findUser(temp[1]);
									if (borrower==null){
										writer.println("0");
										writer.flush();
									}
									else{												
										if (temp[2].length()>0) borrower.returnFee(Integer.parseInt(temp[2]));							
										
										writer.println("1");
										writer.flush();
									}
								
                                   						
									continue;
								}
							}
						}catch(Exception ex){
							ex.printStackTrace();
							info.append("Client disconnect\n");
							jsb.setValue(jsb.getMaximum());
							
							save();
						}
				
	        }catch(Exception e){e.printStackTrace();}finally{
	            try{
	                System.out.println("Close connection:"+socket.getInetAddress()+":"+socket.getPort());
	                if(socket!=null)socket.close();
	            }catch(IOException e){
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public void waitClient(){
		Thread thread;
		
		thread=new Thread(){
			public void run(){			
			
				while(true){
					try{
						if (!started) break;

						Socket temp=serverSocket.accept();					
						
					  Thread workThread=new Thread(new Handler(temp));    
			           workThread.start();                                   
						
					}
					catch(java.net.SocketException ex){
					}
					catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
	}

	
}
