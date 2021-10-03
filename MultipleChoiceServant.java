//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class MultipleChoiceServant extends UnicastRemoteObject implements MultipleChoice {
    Question theQ;
     
    public MultipleChoiceServant(Question q)throws RemoteException{
    	theQ = q;
    }
    
	public Question getQuestion() throws RemoteException {
	    return theQ;
	}
	
   public String getChoices() throws RemoteException{
        return theQ.answer;
   }
    
}