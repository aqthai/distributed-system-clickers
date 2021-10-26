import java.rmi.*;

public interface Student extends Remote {
    String getAnswer() throws RemoteException;
    void sendAnswer(String answer) throws RemoteException;
    User getState() throws RemoteException;
    String getName() throws RemoteException;
    String getPass() throws RemoteException;
    String getQuestion() throws RemoteException;
    String getStatus() throws RemoteException;
    void setStatusOn() throws RemoteException;
    void setStatusOff() throws RemoteException;
    void setQuestion(String question) throws RemoteException;
 }