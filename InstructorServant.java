//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class InstructorServant extends UnicastRemoteObject implements Instructor {
    String theName;
    User theUser;
     
    public InstructorServant(User u, String name)throws RemoteException{
    	theUser = u;
 		theName = name;
    }
    
	public String getName() throws RemoteException {
	    return theName;
	}
	
   public User getState() throws RemoteException{
        return theUser;
   }
    
}