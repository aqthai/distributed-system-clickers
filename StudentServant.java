//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class StudentServant extends UnicastRemoteObject implements Student {
    String theName;
    User theUser;
     
    public StudentServant(User u, String name)throws RemoteException{
    	theUser = u;
 		theName = name;
    }
    
	public String getName() throws RemoteException {
	    return theName;
	}
	
    public User getUser() throws RemoteException{
        return theUser;
    }
    
}