import java.rmi.*;

public interface Instructor extends Remote {
    String makeMultipleChoice() throws RemoteException;
    String makeFreeResponse() throws RemoteException;
    User getState() throws RemoteException;
 }