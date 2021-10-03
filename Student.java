import java.rmi.*;

public interface Student extends Remote {
    String sendAnswer() throws RemoteException;
    String getState() throws RemoteException;
    void refresh() throws RemoteException;
 }