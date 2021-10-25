import java.rmi.*;
import java.util.ArrayList;

public interface ClassList extends Remote {
    void newStudent(User u) throws RemoteException;
    Student getStudent(String name) throws RemoteException;
    Instructor getInstructor() throws RemoteException;
    void setInstructor(User u) throws RemoteException;
    ArrayList<StudentServant> allStudents() throws RemoteException;
}