//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
    String theQuestion;
    String theAnswer;
    Boolean online;
    Scanner scanner = new Scanner(System.in);
     
    public StudentServant(User u)throws RemoteException{
    	theUser = u;
        theQuestion = "";
        theAnswer = "";
        online = false;
    }

    public void sendAnswer() throws RemoteException{
        System.out.println("Type the answer: ");
        this.theAnswer = scanner.nextLine();
    }
    
	public User getState() throws RemoteException {
        return theUser;
	}

	public String getName() throws RemoteException {
        return theUser.username;
	}

	public Boolean getStatus() throws RemoteException {
        return online;
	}


	public void setStatus() throws RemoteException {
        if (online == false) {
            online = true;
        } else {
            online = false;
        }
	}

	public String getPass() throws RemoteException {
        return theUser.password;
	}
	
    public void refresh() throws RemoteException{
        
    }
    
}