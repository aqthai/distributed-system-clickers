import java.rmi.*;

public interface Instructor extends Remote {
    String getName() throws RemoteException;
    User getAllState() throws RemoteException;
 }