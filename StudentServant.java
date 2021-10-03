//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class StudentServant extends UnicastRemoteObject implements Student {
    User theUser;
     
    public StudentServant(User u, String name)throws RemoteException{
    	theUser = u;
    }

    public void sendAnswer(String answer) throws RemoteException{

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