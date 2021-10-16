import java.rmi.*;

public interface Student extends Remote {
    void refresh() throws RemoteException;
    void sendAnswer() throws RemoteException;
    User getState() throws RemoteException;
    String getName() throws RemoteException;
    String getPass() throws RemoteException;
 }