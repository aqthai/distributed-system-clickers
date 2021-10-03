//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    Scanner scanner = new Scanner(System.in);
     
    public StudentServant(User u)throws RemoteException{
    	theUser = u;
    }

    public String sendAnswer() throws RemoteException{
        System.out.println("Type the answer: ");
        String answer = scanner.nextLine();
        return answer;
    }
    
	public String getState() throws RemoteException {
        String status = "";
        if (theUser.online == true){
            status = "online";
        } else {
            status = "offline";
        }
	    return theUser.type + " " + theUser.username + " " + status;
	}
	
    public void refresh() throws RemoteException{
        
    }
    
}