//package examples.RMIShape;
import java.rmi.*;
import java.util.Vector;

public interface QuestionList extends Remote {
  	MultipleChoice newMCQuestion(Question q) throws RemoteException;  	    
  	FreeResponse newFRQuestion(Question q) throws RemoteException;  	    
    Vector allQuestions()throws RemoteException;
}
