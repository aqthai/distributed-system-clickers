import java.rmi.*;

public interface MultipleChoice extends Remote {
    Question getQuestion() throws RemoteException;
    String getChoices() throws RemoteException;
 }