//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    Question theQuestion;
    Scanner scanner = new Scanner(System.in);
     
    public StudentServant(User u, Question q)throws RemoteException{
    	theUser = u;
        theQuestion = q;
    }

    public void sendAnswer() throws RemoteException{
        System.out.println("Type the answer: ");
        this.theQuestion.answer = scanner.nextLine();
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