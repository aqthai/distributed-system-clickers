//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    String theQuestion;
    String theAnswer;
    Scanner scanner = new Scanner(System.in);
     
    public StudentServant(User u)throws RemoteException{
    	theUser = u;
        theQuestion = "";
        theAnswer = "";
    }

    public void sendAnswer() throws RemoteException{
        System.out.println("Type the answer: ");
        this.theAnswer = scanner.nextLine();
    }
    
	public String getState() throws RemoteException {
        String status = "";
        if (this.theUser.online == true){
            status = "online";
        } else {
            status = "offline";
        }
	    return theUser.type + " " + theUser.username + " " + status;
	}
	
    public void refresh() throws RemoteException{
        
    }
    
}