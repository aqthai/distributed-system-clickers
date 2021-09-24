//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class InstructorServant extends UnicastRemoteObject implements User {
    String theName;
    User theUser;
     
    public InstructorServant(User u, String name)throws RemoteException{
    	theUser = u;
 		theName = name;
    }
    
	public int getName() throws RemoteException {
	    return theName;
	}
	
   public User getAllState() throws RemoteException{
        return theUser;
   }
    
}