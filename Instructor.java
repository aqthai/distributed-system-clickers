/**
 * Instructor is the interface of the Instructor object.  The user given this
 * title can make free response or multiple choice questions.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */

import java.rmi.*;

public interface Instructor extends Remote {
    String makeMultipleChoice() throws RemoteException;
    String makeFreeResponse() throws RemoteException;
    User getState() throws RemoteException;
 }