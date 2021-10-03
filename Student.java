import java.rmi.*;

public interface Student extends Remote {
    void refresh() throws RemoteException;
    void sendAnswer(String answer) throws RemoteException;
    String getState() throws RemoteException;
 }