/**
 * Student is the interface of Student objects.  These users need to get 
 * answers that are assigned to this object by the instructor, send answers,
 * log in and out.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */

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