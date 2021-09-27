//package examples.RMIShape;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
public class QuestionListServant extends UnicastRemoteObject implements QuestionList{
    private Vector<Question> theList;
    
    public QuestionListServant()throws RemoteException{
        theList = new Vector<Question>();
    }

  	public MultipleChoice newMCQuestion(Question q) throws RemoteException{
       	MultipleChoiceServant s = new MultipleChoiceServant( q);
        theList.addElement(s);                
        return s;
     }
  	
     public FreeResponse newFRQuestion(Question q) throws RemoteException{
       	FreeResponseServant s = new FreeResponseServant( q);
        theList.addElement(s);                
        return s;
     }

   	public  Vector allQuestions()throws RemoteException{
        return theList;
    }
}
