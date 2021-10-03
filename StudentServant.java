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
    
	public String getState() throws RemoteException {
        String status = "";
        if (theUser.status == true){
            status = "online";
        } else {
            status = "offline";
        }
	    return theUser.type + " " + theUser.username + " " + status;
	}
	
    public void refresh() throws RemoteException{
        return theUser;
    }
    
}