/** 
 * ClassList is the remote interface of ClassListServant.  It adds students
 * gets students, gets the instructor, sets the instructor, and the classlist.
 * Authors: Alvin Thai, Daniel Holguin, Jarom Montgomerry, Nicholas Knaggs
 * Professor Silva ACO 432
 */

import java.rmi.*;
import java.util.ArrayList;

public interface ClassList extends Remote {
    void newStudent(User u) throws RemoteException;
    Student getStudent(String name) throws RemoteException;
    Instructor getInstructor() throws RemoteException;
    void setInstructor(User u) throws RemoteException;
    ArrayList<StudentServant> allStudents() throws RemoteException;
}