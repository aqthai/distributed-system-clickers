import java.rmi.*;

public interface FreeResponse extends Remote {
    Question getQuestion() throws RemoteException;
    String getAnswer() throws RemoteException;
 }