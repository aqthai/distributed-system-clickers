import java.rmi.*;

public interface Student extends Remote {
    void refresh() throws RemoteException;
    String sendAnswer() throws RemoteException;
    String getState() throws RemoteException;
 }