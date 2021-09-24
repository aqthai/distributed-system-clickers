import java.rmi.*;

public interface Student extends Remote {
    String getName() throws RemoteException;
    User getAllState() throws RemoteException;
 }